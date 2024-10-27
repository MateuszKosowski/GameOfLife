package org.team1;

import java.util.List;

public abstract class GameOfLifeLine {

    protected List<GameOfLifeCell> line;

    public GameOfLifeLine(List<GameOfLifeCell> line) {
        this.line = line;
    }

    public int countAliveCells() {
        int aliveCells = 0;
        for (GameOfLifeCell cell : this.line) {
            if (cell.getValue()) {
                aliveCells++;
            }
        }
        return aliveCells;
    }

    public int countDeadCells() {
        return this.line.size() - countAliveCells();
    }
}
