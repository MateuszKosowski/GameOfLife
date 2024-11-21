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
import org.team1.FileGameOfLifeBoardDao;
import org.team1.GameOfLifeBoard;
import org.team1.PlainGameOfLifeSimulator;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileGameOfLifeBoardDaoTest {

    @Test
    void writeTest() throws Exception {
        GameOfLifeBoard board = new GameOfLifeBoard(3, 3, new PlainGameOfLifeSimulator());
        FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao("board.txt");
        dao.write(board);
    }

    @Test
    void writeFailTest() throws RuntimeException {
        assertThrows(RuntimeException, new FileGameOfLifeBoardDao(""));
    }

    @Test
    void readTest() {
        FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao("board.txt");
        GameOfLifeBoard board = dao.read();
        System.out.println(board.toString());
    }
}
