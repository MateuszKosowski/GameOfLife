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
import org.team1.GameOfLifeBoard;
import org.team1.GameOfLifeSimulator;
import org.team1.PlainGameOfLifeSimulator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlainGameOfLifeSimulatorTest {

    @Test
    void doStepTest() throws Exception {
        GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard(8, 8, gameOfLifeSimulator);
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.set(0, 0, true);
        gameOfLifeBoard.set(0, 1, true);
        gameOfLifeBoard.set(0, 2, true);
        gameOfLifeBoard.set(1, 0, true);
        gameOfLifeBoard.set(2, 0, true);
        gameOfLifeBoard.doSimulationStep();
        assertTrue(gameOfLifeBoard.get(7, 1));
        assertTrue(gameOfLifeBoard.get(1, 7));
    }
}
