package org.team1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard> {
    private final String fileName;

    public FileGameOfLifeBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public GameOfLifeBoard read() {
        try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream(fileName))) {
            return (GameOfLifeBoard) oos.readObject();
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public void write(GameOfLifeBoard object) {
        // try-with-resources, dzięki temu nie trzeba pisać oos.close()
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

}
