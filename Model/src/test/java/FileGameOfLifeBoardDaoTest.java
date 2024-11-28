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

import org.junit.jupiter.api.Test;
import org.team1.*;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
// TODO: wszędzie try with resources
public class FileGameOfLifeBoardDaoTest {

    @Test
    void writeTest() throws Exception {
        GameOfLifeBoard board = new GameOfLifeBoard(3, 3, new PlainGameOfLifeSimulator());
        GameOfLifeBoardDaoFactory factory = new GameOfLifeBoardDaoFactory();
        // TODO: to ma dzialac, nie wiem jak to zrobic
        Dao<GameOfLifeBoard> dao = factory.createFileGameOfLifeBoardDao("board.txt");  //new FileGameOfLifeBoardDao();
        dao.write(board);
        dao.close();
    }

    @Test
    void writeFailTest() throws Exception {
        // Utwórz tymczasowy plik
        Path filePath = Files.createTempFile("testFile", ".txt");

        // Usuń plik po zakończeniu testu
        filePath.toFile().deleteOnExit();

        // Spróbuj zablokować plik
        try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "rw");
             FileChannel channel = raf.getChannel()) {
            // Blokada pliku
            FileLock lock = channel.lock();

            // Spróbuj zapisać do zablokowanego pliku
            FileGameOfLifeBoardDao writer = new FileGameOfLifeBoardDao(filePath.toString());
            GameOfLifeBoard board = new GameOfLifeBoard(3, 3, new PlainGameOfLifeSimulator());

            // Tutaj trzeba użyć lambdy, ponieważ metoda write przyjmuje argument typu GameOfLifeBoard
            assertThrows(RuntimeException.class, () -> writer.write(board));

            // Zwolnij blokadę (po wykonaniu testu)
            lock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void readTest() {
        try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao("board.txt")) {
            GameOfLifeBoard board = dao.read();
            // TODO:
            System.out.println(board.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void readFailTest() throws RuntimeException {
        try (FileGameOfLifeBoardDao reader = new FileGameOfLifeBoardDao("invalidFile.txt")) {
            // assertThrows oczekuje przekazania referencji do metody, która jest executable
            // dlatego podajemy referencję do metody (::read), a nie wywołanie metody
            assertThrows(RuntimeException.class, reader::read);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
