package org.team1;

public class GameOfLifeCell {
    private boolean value;
    private GameOfLifeCell[] neighbors;

    public GameOfLifeCell(boolean value) {
        this.value = value;
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
        value = newState;
    }

}
