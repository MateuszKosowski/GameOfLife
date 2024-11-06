package org.team1;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class GameOfLifeLine implements PropertyChangeListener {

    private int aliveCount = 0;
    private int deadCount = 0;
    protected GameOfLifeCell[] line;

    public GameOfLifeLine(GameOfLifeCell[] line) {
        this.line = line;
        for (GameOfLifeCell cell : line) {
            cell.addPropertyChangeListener(this);
            if (cell.getValue()) {
                aliveCount++;
            } else {
                deadCount++;
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("cell".equals(evt.getPropertyName())) {
            boolean newState = (boolean) evt.getNewValue();
            if (newState) {
                aliveCount++;
                deadCount--;
            } else {
                aliveCount--;
                deadCount++;
            }
        }
    }

    public GameOfLifeCell[] getLine() {
        return this.line;
    }

    public int countAliveCells() {
        return aliveCount;
    }

    public int countDeadCells() {
        return deadCount;
    }
}
