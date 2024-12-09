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

import java.io.*;

public class FileGameOfLifeBoardDao implements Dao<GameOfLifeBoard> {
    private final FileInputStream fis;
    private final ObjectInputStream ois;
    private final FileOutputStream fos;
    private final ObjectOutputStream oos;

    public FileGameOfLifeBoardDao(String fileName) throws IOException {
        this.fis = new FileInputStream(fileName);
        this.ois = new ObjectInputStream(this.fis);
        this.fos = new FileOutputStream(fileName);
        this.oos = new ObjectOutputStream(this.fos);
    }

    @Override
    public GameOfLifeBoard read() {
        try {
            return (GameOfLifeBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(GameOfLifeBoard object) {
        // try-with-resources, dzięki temu nie trzeba pisać oos.close()
        try {
            oos.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            fis.close();
            ois.close();
            fos.close();
            oos.close();
        } catch (IOException error) {
            throw new RuntimeException(error);
        }
    }
}
