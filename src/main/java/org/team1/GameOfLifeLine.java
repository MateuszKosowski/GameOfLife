package org.team1;

import java.util.List;

public abstract class GameOfLifeLine {
    protected GameOfLifeBoard board;
    protected int index;

    public GameOfLifeLine(GameOfLifeBoard board, int index) {
        this.board = board;
        this.index = index;
    }

    protected abstract List<GameOfLifeCell> getLine();

    public int countAliveCells() {
        int aliveCells = 0;
        for (GameOfLifeCell cell : getLine()) {
            if (cell.getValue()) {
                aliveCells++;
            }
        }
        return aliveCells;
    }

    public int countDeadCells() {
        return getLine().size() - countAliveCells();
    }
}
