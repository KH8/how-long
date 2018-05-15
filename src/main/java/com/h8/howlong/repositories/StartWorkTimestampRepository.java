package com.h8.howlong.repositories;

import com.h8.howlong.domain.StartWorkTimestamp;

import java.io.*;
import java.util.Optional;

public class StartWorkTimestampRepository {

    private final static String DB_FILE_NAME = "./_db";

    public StartWorkTimestampRepository() {
        try {
            File yourFile = new File(DB_FILE_NAME);
            if (yourFile.createNewFile()) {
                System.out.println("Created file: " + DB_FILE_NAME);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: Could not create file: " + DB_FILE_NAME);
        }
    }

    public Optional<StartWorkTimestamp> save(StartWorkTimestamp timestamp) {
        try (
                FileOutputStream fos = new FileOutputStream(DB_FILE_NAME);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(timestamp);
            return Optional.of(timestamp);
        } catch (Exception e) {
            System.out.println("ERROR: Could not write to file: " + DB_FILE_NAME);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<StartWorkTimestamp> findLatest() {
        try (
                FileInputStream fis = new FileInputStream(DB_FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return Optional.of((StartWorkTimestamp) ois.readObject());
        } catch (Exception e) {
            System.out.println("ERROR: Could not read from file: " + DB_FILE_NAME);
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
