package org.team1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.List;


public class GameOfLifeCell {
    private boolean value;
    // Stała referencja do listy sąsiadów, ale wartości w liście mogą się zmieniać
    private final List<GameOfLifeCell> neighbors;
    private final PropertyChangeSupport support;

    public GameOfLifeCell(boolean value) {
        this.value = value;
        this.neighbors = Arrays.asList(new GameOfLifeCell[8]);
        this.support = new PropertyChangeSupport(this);
    }

    // Metoda do dodawania listenerów do naszego obiektu Cell
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setNeighbors(List<GameOfLifeCell> newNeighbors) {
        for (int i = 0; i < newNeighbors.size(); i++) {
            neighbors.set(i, newNeighbors.get(i));
        }
    }

    public boolean getValue() {
        return value;
    }

    public boolean nextState() {
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

    public void updateState(boolean newState) {
        boolean oldState = value;
        value = newState;
        support.firePropertyChange("value", oldState, newState);
    }

    // Getter dla listy sąsiadów
    public List<GameOfLifeCell> getNeighbors() {
        return neighbors;
    }

}
