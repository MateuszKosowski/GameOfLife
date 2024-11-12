import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.team1.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellTest {
    GameOfLifeCell Cell;
    List<GameOfLifeCell> exampleNeighbors = Arrays.asList(new GameOfLifeCell[8]);

    @BeforeEach
    void createGameOfLifeCell() {
        Cell = new GameOfLifeCell(true);

        exampleNeighbors.set(0, new GameOfLifeCell(false));
        exampleNeighbors.set(1, new GameOfLifeCell(false));
        exampleNeighbors.set(2, new GameOfLifeCell(false));
        exampleNeighbors.set(3, new GameOfLifeCell(false));
        exampleNeighbors.set(4, new GameOfLifeCell(false));
        exampleNeighbors.set(5, new GameOfLifeCell(false));
        exampleNeighbors.set(6, new GameOfLifeCell(false));
        exampleNeighbors.set(7, new GameOfLifeCell(false));
    }

    @Test
    void returnValueTest() {;
        assertTrue(Cell.getValue());
    }

    @Test
    void setValuesTest() {
        Cell.updateState(false);
        assertFalse(Cell.getValue());
    }

    @Test
    void setNeighborsTest() {

        Cell.setNeighbors(exampleNeighbors);

        // Porówanie czy sąsiedzi są ustawieni poprawnie
        for (int i = 0; i < exampleNeighbors.size(); i++) {
            assertEquals(Cell.getNeighbors().get(i).getValue(), exampleNeighbors.get(i).getValue());
        }

    }

    @Test
    void nextStateTest1() {
        Cell.setNeighbors(exampleNeighbors);
        assertFalse(Cell.nextState());
    }

    @Test
    void nextStateTest2() {
        exampleNeighbors.set(0, new GameOfLifeCell(true));
        exampleNeighbors.set(1, new GameOfLifeCell(true));
        exampleNeighbors.set(2, new GameOfLifeCell(true));
        exampleNeighbors.set(3, new GameOfLifeCell(true));
        exampleNeighbors.set(4, new GameOfLifeCell(true));
        exampleNeighbors.set(5, new GameOfLifeCell(true));
        exampleNeighbors.set(6, new GameOfLifeCell(true));
        exampleNeighbors.set(7, new GameOfLifeCell(true));
        Cell.setNeighbors(exampleNeighbors);
        assertFalse(Cell.nextState());
    }

    @Test
    void nextStateTest3() {
        exampleNeighbors.set(0, new GameOfLifeCell(true));
        exampleNeighbors.set(1, new GameOfLifeCell(true));
        exampleNeighbors.set(2, new GameOfLifeCell(false));
        exampleNeighbors.set(3, new GameOfLifeCell(false));
        exampleNeighbors.set(4, new GameOfLifeCell(false));
        exampleNeighbors.set(5, new GameOfLifeCell(false));
        exampleNeighbors.set(6, new GameOfLifeCell(false));
        exampleNeighbors.set(7, new GameOfLifeCell(false));
        Cell.setNeighbors(exampleNeighbors);
        assertTrue(Cell.nextState());
    }

    @Test
    void nextStateTest4() {
        Cell.updateState(false);

        exampleNeighbors.set(0, new GameOfLifeCell(true));
        exampleNeighbors.set(1, new GameOfLifeCell(true));
        exampleNeighbors.set(2, new GameOfLifeCell(true));
        exampleNeighbors.set(3, new GameOfLifeCell(false));
        exampleNeighbors.set(4, new GameOfLifeCell(false));
        exampleNeighbors.set(5, new GameOfLifeCell(false));
        exampleNeighbors.set(6, new GameOfLifeCell(false));
        exampleNeighbors.set(7, new GameOfLifeCell(false));
        Cell.setNeighbors(exampleNeighbors);
        assertTrue(Cell.nextState());
    }
}
