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
