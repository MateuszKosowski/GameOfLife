package org.team1;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class GameOfLifeLine implements PropertyChangeListener {

    private int aliveCount = 0;
    protected List<GameOfLifeCell> line;

    public GameOfLifeLine(List<GameOfLifeCell> line) {
        this.line = line;
        for (GameOfLifeCell cell : line) {
            cell.addPropertyChangeListener(this);
            if (cell.getValue()) {
                aliveCount++;
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("value".equals(evt.getPropertyName())) {
            boolean newState = (boolean) evt.getNewValue();
            if (newState) {
                aliveCount++;
            } else {
                aliveCount--;
            }
        }
    }

    public List<GameOfLifeCell> getLine() {
        return this.line;
    }

    public int countAliveCells() {
        return aliveCount;
    }

    public int countDeadCells() {
        return line.size() - aliveCount;
    }
}
