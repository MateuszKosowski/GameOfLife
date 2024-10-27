package org.team1;

public abstract class GameOfLifeLine {

    protected GameOfLifeCell[] line;

    public GameOfLifeLine(GameOfLifeCell[] line) {
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
        return this.line.length - countAliveCells();
    }
}
