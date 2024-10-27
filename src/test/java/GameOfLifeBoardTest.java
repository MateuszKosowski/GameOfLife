import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.team1.GameOfLifeBoard;
import org.team1.GameOfLifeSimulator;
import org.team1.PlainGameOfLifeSimulator;
import org.team1.GameOfLifeCell;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        GameOfLifeCell[] actualCells = gameOfLifeBoardSmall.getRow(0).getLine();

        boolean areEqual = true;
        for (int i = 0; i < expectedCells.length; i++) {
            if (expectedCells[i].getValue() != actualCells[i].getValue()) {
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
        GameOfLifeCell[] actualCells = gameOfLifeBoardSmall.getColumn(2).getLine();

        boolean areEqual = true;
        for (int i = 0; i < expectedCells.length; i++) {
            if (expectedCells[i].getValue() != actualCells[i].getValue()) {
                areEqual = false;
                break;
            }
        }
        assertTrue(areEqual);
    }

    @Test
    void countDeadCellsTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(2, 0, true);
        int expectedDeadCells = 2;
        int actualDeadCells = gameOfLifeBoardSmall.getRow(0).countDeadCells();

        assertTrue(expectedDeadCells == actualDeadCells);
    }

    @Test
    void countAliveCellsTest() {
        gameOfLifeBoardSmall.fillFalse();
        gameOfLifeBoardSmall.set(0, 0, true);
        gameOfLifeBoardSmall.set(1, 0, true);
        int expectedAliveCells = 2;
        int actualAliveCells = gameOfLifeBoardSmall.getColumn(0).countAliveCells();

        assertTrue(expectedAliveCells == actualAliveCells);
    }
}
