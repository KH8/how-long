package com.h8.howlong.repositories;

import java.io.*;
import java.util.Optional;

public abstract class AbstractFileBasedRepository<T> {

    private final static String DB_FILE_NAME = System.getProperty("user.home") + "/_db";

    AbstractFileBasedRepository() {
        initialize();
    }

    private void initialize() {
        try {
            File yourFile = new File(DB_FILE_NAME);
            if (yourFile.createNewFile()) {
                T t = initializeContent();
                writeContent(t);
                System.out.println("Initialized file: " + DB_FILE_NAME);
            }
        } catch (IOException e) {
            System.out.println("ERROR: Could not create file: " + DB_FILE_NAME);
            e.printStackTrace();
        }
    }

    public void writeContent(T t) {
        try (
                FileOutputStream fos = new FileOutputStream(DB_FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(t);
        } catch (Exception e) {
            System.out.println("ERROR: Could not write to file: " + DB_FILE_NAME);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<T> readContent() {
        try (
                FileInputStream fis = new FileInputStream(DB_FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return Optional.of((T) ois.readObject());
        } catch (Exception e) {
            System.out.println("ERROR: Could not read from file: " + DB_FILE_NAME);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    protected abstract T initializeContent();

}
