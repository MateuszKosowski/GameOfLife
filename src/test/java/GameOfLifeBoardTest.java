import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nikodem.GameOfLifeBoard;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameOfLifeBoardTest {
    private GameOfLifeBoard gameOfLifeBoard;

    @BeforeEach
    void createGameOfLifeBoard() throws Exception {
         gameOfLifeBoard = new GameOfLifeBoard(8, 8);
    }

    // Martwa komórka mająca dokładnie trzech żywych sąsiadów staje się żywa w następnym kroku
    // + kiedy komórki są na brzegach.
    @Test
    void shouldBecomeAliveWithExactlyThreeAliveNeighbors() {
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.setCoordinate(0, 0, true);
        gameOfLifeBoard.setCoordinate(0, 1, true);
        gameOfLifeBoard.setCoordinate(0, 2, true);
        gameOfLifeBoard.setCoordinate(1, 0, true);
        gameOfLifeBoard.setCoordinate(2, 0, true);
        gameOfLifeBoard.doStep();
        assertTrue(gameOfLifeBoard.getCoordinate(7, 1));
        assertTrue(gameOfLifeBoard.getCoordinate(1, 7));
    }

    // żywa komórka z dwoma lub trzema sąsiadami pozostaje żywa
    // + żywa komórka z jednym sąsiadem umiera "z samotności"
    @Test
    void shouldStayAliveWithTwoNeighborsAndDieWithOne() {
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.setCoordinate(3, 3, true);
        gameOfLifeBoard.setCoordinate(3, 4, true);
        gameOfLifeBoard.setCoordinate(3, 5, true);
        gameOfLifeBoard.doStep();
        assertTrue(gameOfLifeBoard.getCoordinate(3, 4));
    }

    @Test
    void shouldStayAliveWithThreeNeighbors() {
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.setCoordinate(3, 3, true);
        gameOfLifeBoard.setCoordinate(3, 4, true);
        gameOfLifeBoard.setCoordinate(3, 5, true);
        gameOfLifeBoard.setCoordinate(4, 4, true);
        gameOfLifeBoard.doStep();
        assertTrue(gameOfLifeBoard.getCoordinate(3, 4));
    }

    // żywa komórka z więcej niż trzema sąsiadami umiera "z zatłoczenia"
    @Test
    void shouldDieWithMoreThanThreeNeighbors() {
        gameOfLifeBoard.fillFalse();
        gameOfLifeBoard.setCoordinate(3, 3, true);
        gameOfLifeBoard.setCoordinate(3, 4, true);
        gameOfLifeBoard.setCoordinate(3, 5, true);
        gameOfLifeBoard.setCoordinate(4, 4, true);
        gameOfLifeBoard.setCoordinate(2, 4, true);
        gameOfLifeBoard.doStep();
        assertFalse(gameOfLifeBoard.getCoordinate(3, 4));
    }

    // dwa kolejne wykonania konstruktora klasy org.nikodem.GameOfLifeBoard
    // generują inny początkowy układ komórek żywych i martwych
    @Test
    void shouldFillBoardsWithDifferentStartValues() throws Exception {
        GameOfLifeBoard gameOfLifeBoard1 = new GameOfLifeBoard(8, 8);
        assertFalse(Arrays.deepEquals(gameOfLifeBoard.getBoard(), gameOfLifeBoard1.getBoard()));
    }
}
