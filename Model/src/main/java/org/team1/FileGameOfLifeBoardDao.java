package org.team1;

import java.io.*;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final String fileName;

    public FileGameOfLifeBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public GameOfLifeBoard read() {
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameOfLifeBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    @Override
    public void write(GameOfLifeBoard object) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + fileName, e);
        }
    }

    @Override
    public void close() {
        // Resources are closed automatically by try-with-resources
    }
}