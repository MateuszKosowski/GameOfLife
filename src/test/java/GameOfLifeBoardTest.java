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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.team1.*;
import java.util.List;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeBoardTest {
    private GameOfLifeBoard gameOfLifeBoard;
    private GameOfLifeBoard gameOfLifeBoardSmall;
    private final GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();

    @BeforeEach
    void createGameOfLifeBoard() throws Exception {
         gameOfLifeBoard = new GameOfLifeBoard(8, 8, gameOfLifeSimulator);
         gameOfLifeBoardSmall = new GameOfLifeBoard(3, 3, gameOfLifeSimulator);
    }

    // Martwa komórka mająca dokładnie trzech żywych sąsiadów staje się żywa w następnym kroku
    // + kiedy komórki są na brzegach.
    @Test
    void shouldBecomeAliveWithExactlyThreeAliveNeighbors() {
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

    // żywa komórka z dwoma lub trzema sąsiadami pozostaje żywa
    // + żywa komórka z jednym sąsiadem umiera "z samotności"
    @Test
    void shouldStayAliveWithTwoNeighborsAndDieWithOne() {
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.set(3, 3, true);
        gameOfLifeBoard.set(3, 4, true);
        gameOfLifeBoard.set(3, 5, true);
        gameOfLifeBoard.doSimulationStep();
        assertTrue(gameOfLifeBoard.get(3, 4));
    }

    @Test
    void shouldStayAliveWithThreeNeighbors() {
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.set(3, 3, true);
        gameOfLifeBoard.set(3, 4, true);
        gameOfLifeBoard.set(3, 5, true);
        gameOfLifeBoard.set(4, 4, true);
        gameOfLifeBoard.doSimulationStep();
        assertTrue(gameOfLifeBoard.get(3, 4));
    }

    // żywa komórka z więcej niż trzema sąsiadami umiera "z zatłoczenia"
    @Test
    void shouldDieWithMoreThanThreeNeighbors() {
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.set(3, 3, true);
        gameOfLifeBoard.set(3, 4, true);
        gameOfLifeBoard.set(3, 5, true);
        gameOfLifeBoard.set(4, 4, true);
        gameOfLifeBoard.set(2, 4, true);
        gameOfLifeBoard.doSimulationStep();
        assertFalse(gameOfLifeBoard.get(3, 4));
    }

    // dwa kolejne wykonania konstruktora klasy org.nikodem.GameOfLifeBoard
    // generują inny początkowy układ komórek żywych i martwych
    @Test
    void shouldFillBoardsWithDifferentStartValues() throws Exception {
        GameOfLifeBoard gameOfLifeBoard1 = new GameOfLifeBoard(8, 8, gameOfLifeSimulator);
        assertFalse(Arrays.deepEquals(gameOfLifeBoard.getBoard(), gameOfLifeBoard1.getBoard()));
    }

    @Test
    void shouldThrowExceptionWhenWidthOrHeightIsNegativeNumber() {
        Assertions.assertThrows(Exception.class, () -> new GameOfLifeBoard(-1, 1, gameOfLifeSimulator));

        Assertions.assertThrows(Exception.class, () -> new GameOfLifeBoard(1, -1, gameOfLifeSimulator));
    }

    @Test
    void getRowTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(0, 1, true);
        gameOfLifeBoardSmall.set(0, 2, true);
        gameOfLifeBoardSmall.set(2, 0, true);
        gameOfLifeBoardSmall.set(2, 1, true);
        gameOfLifeBoardSmall.set(2, 2, true);

        GameOfLifeCell[] expectedCells = {new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(true)};
        List<GameOfLifeCell> expectedList = Arrays.asList(expectedCells);
        List<GameOfLifeCell> actualCells = gameOfLifeBoardSmall.getRow(0).getLine();

        boolean areEqual = true;
        for (int i = 0; i < expectedCells.length; i++) {
            if (expectedList.get(i).getValue() != actualCells.get(i).getValue()) {
                areEqual = false;
                break;
            }
        }
        assertTrue(areEqual);

    }

    @Test
    void getColumnTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(1, 0, true);
        gameOfLifeBoardSmall.set(2, 0, true);
        gameOfLifeBoardSmall.set(0, 2, true);
        gameOfLifeBoardSmall.set(1, 2, true);
        gameOfLifeBoardSmall.set(2, 2, true);

        GameOfLifeCell[] expectedCells = {new GameOfLifeCell(true), new GameOfLifeCell(true), new GameOfLifeCell(true)};
        List<GameOfLifeCell> expectedList = Arrays.asList(expectedCells);
        List<GameOfLifeCell> actualCells = gameOfLifeBoardSmall.getColumn(2).getLine();

        boolean areEqual = true;
        for (int i = 0; i < expectedCells.length; i++) {
            if (expectedList.get(i).getValue() != actualCells.get(i).getValue()) {
                areEqual = false;
                break;
            }
        }
        assertTrue(areEqual);
    }

    @Test
    void shouldUpdateRowAliveAndDeadCells() {
        gameOfLifeBoardSmall.fillFalse();
        int aliveCells = gameOfLifeBoardSmall.getRow(0).countAliveCells();
        int deadCells = gameOfLifeBoardSmall.getRow(0).countDeadCells();
        assertEquals(0, aliveCells);
        assertEquals(3, deadCells);
        gameOfLifeBoardSmall.set(0, 1, true);
        aliveCells = gameOfLifeBoardSmall.getRow(0).countAliveCells();
        deadCells = gameOfLifeBoardSmall.getRow(0).countDeadCells();
        assertEquals(1, aliveCells);
        assertEquals(2, deadCells);
    }

    @Test
    void shouldUpdateColumnAliveAndDeadCells() {
        gameOfLifeBoardSmall.fillFalse();
        int aliveCells = gameOfLifeBoardSmall.getColumn(1).countAliveCells();
        int deadCells = gameOfLifeBoardSmall.getColumn(1).countDeadCells();
        assertEquals(0, aliveCells);
        assertEquals(3, deadCells);
        gameOfLifeBoardSmall.set(1, 0, true);
        gameOfLifeBoardSmall.set(1, 1, true);
        aliveCells = gameOfLifeBoardSmall.getRow(1).countAliveCells();
        deadCells = gameOfLifeBoardSmall.getRow(1).countDeadCells();
        assertEquals(2, aliveCells);
        assertEquals(1, deadCells);
    }

    @Test
    void shouldNotUpdateRow_WrongPropertyName() {
        gameOfLifeBoardSmall.fillFalse();
        PropertyChangeEvent event = new PropertyChangeEvent(this, "noCell", false, true);

        GameOfLifeRow row = gameOfLifeBoardSmall.getRow(0);

        row.propertyChange(event);

        assertEquals(0, row.countAliveCells());
        assertEquals(3, row.countDeadCells());
    }

    @Test
    void countDeadCellsTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(2, 0, true);
        int expectedDeadCells = 2;
        int actualDeadCells = gameOfLifeBoardSmall.getRow(0).countDeadCells();

        assertEquals(expectedDeadCells, actualDeadCells);
    }

    @Test
    void countAliveCellsTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(1, 0, true);
        int expectedAliveCells = 2;
        int actualAliveCells = gameOfLifeBoardSmall.getColumn(0).countAliveCells();

        assertEquals(expectedAliveCells, actualAliveCells);
    }

    @Test
    void toStringTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(1, 0, true);
        gameOfLifeBoardSmall.set(2, 0, true);
        GameOfLifeCell[][] board = gameOfLifeBoardSmall.getBoard();
        StringBuilder sb = new StringBuilder();
        sb.append("GameOfLifeBoard{board=[");
        for (int i = 0; i < board.length; i++) {
            sb.append("[");
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j].toString());
                if (j < board[i].length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (i < board.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("], gameOfLifeSimulator=").append(gameOfLifeSimulator).append("}");
        String actualString = gameOfLifeBoardSmall.toString();

        assertEquals(sb.toString(), actualString);
    }

    @Test
    void equalsTest() throws Exception {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(1, 0, true);
        gameOfLifeBoardSmall.set(2, 0, true);

        GameOfLifeBoard gameOfLifeBoardSmall2 = new GameOfLifeBoard(3, 3, gameOfLifeSimulator);
        gameOfLifeBoardSmall2.fillFalse();
        gameOfLifeBoardSmall2.set(0, 0, true);
        gameOfLifeBoardSmall2.set(1, 0, true);

        assertEquals(gameOfLifeBoardSmall, gameOfLifeBoardSmall);

        assertNotEquals(gameOfLifeBoardSmall, gameOfLifeBoardSmall2);

        gameOfLifeBoardSmall2.set(2, 0, true);

        assertEquals(gameOfLifeBoardSmall, gameOfLifeBoardSmall2);

        assertNotEquals(gameOfLifeBoardSmall, new Object());

        assertNotEquals(gameOfLifeBoardSmall, null);
    }

    @Test
    void hashCodeTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(1, 0, true);
        gameOfLifeBoardSmall.set(2, 0, true);

        int expectedHashCode = gameOfLifeBoardSmall.hashCode();

        assertEquals(expectedHashCode, gameOfLifeBoardSmall.hashCode());

        gameOfLifeBoardSmall.set(2, 0, false);
        assertNotEquals(expectedHashCode, gameOfLifeBoardSmall.hashCode());
    }
}
