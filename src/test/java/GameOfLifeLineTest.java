import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.team1.GameOfLifeCell;
import org.team1.GameOfLifeColumn;
import org.team1.GameOfLifeLine;
import org.team1.GameOfLifeRow;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class GameOfLifeLineTest {
    GameOfLifeLine line;
    GameOfLifeLine line1;

    @BeforeEach
    void createGameOfLifeLine() {
        GameOfLifeCell cell1 = new GameOfLifeCell(false);
        GameOfLifeCell cell2 = new GameOfLifeCell(true);
        GameOfLifeCell cell3 = new GameOfLifeCell(false);
        GameOfLifeCell cell4 = new GameOfLifeCell(true);
        line = new GameOfLifeRow(List.of(cell1, cell2, cell3));
        line1 = new GameOfLifeColumn(List.of(cell1, cell2, cell4));
    }

    @Test
    void getLineTest() {
        List<GameOfLifeCell> lineList = line.getLine();
        List<GameOfLifeCell> lineList1 = line1.getLine();
        assert(lineList.size() == 3);
        assert(lineList1.size() == 3);
    }

    @Test
    void countAliveCellsTest() {
        assert(line.countAliveCells() == 1);
        assert(line1.countAliveCells() == 2);
    }

    @Test
    void countDeadCellsTest() {
        assert(line.countDeadCells() == 2);
        assert(line1.countDeadCells() == 1);
    }

    @Test
    void propertyChangeTest() {
        line.propertyChange(new PropertyChangeEvent(this, "value", false, true));
        assert(line.countAliveCells() == 2);
        line.propertyChange(new PropertyChangeEvent(this, "value", true, false));
        assert(line.countAliveCells() == 1);
    }

}
