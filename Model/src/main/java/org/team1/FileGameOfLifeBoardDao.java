package org.team1;

/*-
 * #%L
 * GameOfLife
 * %%
 * Copyright (C) 2024 Zespol1
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable {
    private final String fileName;

    public FileGameOfLifeBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public GameOfLifeBoard read() {
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(fileName))) {
            return (GameOfLifeBoard) oos.readObject();
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public void write(GameOfLifeBoard object) {
        // try-with-resources, dzięki temu nie trzeba pisać oos.close()
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    @Override
    public void close() {
        // nie trzeba nic robić, ale musi być, bo implementujemy AutoCloseable
    }
}
