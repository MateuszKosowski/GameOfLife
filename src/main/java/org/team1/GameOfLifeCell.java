package org.team1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameOfLifeCell {
    private boolean value;
    private GameOfLifeCell[] neighbors;
    private final PropertyChangeSupport support;

    public GameOfLifeCell(boolean value) {
        this.value = value;
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    void setNeighbors(GameOfLifeCell[] newNeighbors) {
        this.neighbors = newNeighbors;
    }

    // Jak nic nie ma to dostęp jest domyślny, czyli tylko w pakiecie
    public boolean getValue() {
        return value;
    }

    boolean nextState() {
        int aliveNeighbors = 0;
        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor.getValue()) {
                aliveNeighbors++;
            }
        }
        if (value) {
            return aliveNeighbors == 2 || aliveNeighbors == 3;
        } else {
            return aliveNeighbors == 3;
        }
    }

    void updateState(boolean newState) {
        boolean oldState = value;
        value = newState;
        support.firePropertyChange("cell", oldState, newState);
    }

}
