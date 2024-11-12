package org.team1;

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
