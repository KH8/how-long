package com.h8.howlong.repositories;

import com.h8.howlong.utils.*;

import java.io.*;
import java.util.*;

public abstract class AbstractFileBasedRepository<T> {

    private final String dbFileName;

    AbstractFileBasedRepository(String dbFileName) {
        this.dbFileName = dbFileName;
        initialize(dbFileName);
    }

    private void initialize(String dbFileName) {
        try {
            var yourFile = new File(dbFileName);
            if (yourFile.createNewFile()) {
                T t = initializeContent();
                writeContent(t);
                Logger.log("Initialized file: ", dbFileName);
            }
        } catch (IOException e) {
            Logger.log("ERROR: Could not create file: ", dbFileName);
            e.printStackTrace();
        }
    }

    public void writeContent(T t) {
        try (
                var fos = new FileOutputStream(dbFileName);
                var oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(t);
        } catch (Exception e) {
            Logger.log("ERROR: Could not write to file: ", dbFileName);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<T> readContent() {
        try (
                var fis = new FileInputStream(dbFileName);
                var ois = new ObjectInputStream(fis)
        ) {
            return Optional.of((T) ois.readObject());
        } catch (Exception e) {
            Logger.log("ERROR: Could not read from file: ", dbFileName);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    protected abstract T initializeContent();

}
