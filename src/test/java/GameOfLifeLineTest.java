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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.team1.GameOfLifeCell;
import org.team1.GameOfLifeColumn;
import org.team1.GameOfLifeLine;
import org.team1.GameOfLifeRow;

import java.beans.PropertyChangeEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(3, lineList.size());
        assertEquals(3, lineList1.size());
    }

    @Test
    void countAliveCellsTest() {
        assertEquals(1, line.countAliveCells());
        assertEquals(2, line1.countAliveCells());
    }

    @Test
    void countDeadCellsTest() {
        assertEquals(2, line.countDeadCells());
        assertEquals(1, line1.countDeadCells());
    }

    @Test
    void propertyChangeTest() {
        line.propertyChange(new PropertyChangeEvent(this, "value", false, true));
        assertEquals(2, line.countAliveCells());
        line.propertyChange(new PropertyChangeEvent(this, "value", true, false));
        assertEquals(1, line.countAliveCells());
    }

}
