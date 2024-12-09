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

package org.team1;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class GameOfLifeCell implements Serializable, Cloneable, Comparable<GameOfLifeCell> {
    // Stała referencja do listy sąsiadów, ale wartości w liście mogą się zmieniać
    private final List<GameOfLifeCell> neighbors;
    private final PropertyChangeSupport support;
    private boolean value;

    public GameOfLifeCell(boolean value) {
        this.value = value;
        this.neighbors = Arrays.asList(new GameOfLifeCell[8]);
        this.support = new PropertyChangeSupport(this);
    }

    // Getter dla listy sąsiadów
    public List<GameOfLifeCell> getNeighbors() {
        return neighbors;
    }

    // Metoda do przypisania sąsiadów
    public void setNeighbors(List<GameOfLifeCell> newNeighbors) {
        for (int i = 0; i < newNeighbors.size(); i++) {
            neighbors.set(i, newNeighbors.get(i));
        }
    }

    // Getter dla wartości
    public boolean getValue() {
        return value;
    }

    // Metoda do dodawania listenerów do naszego obiektu Cell
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    // Obliczanie następnego stanu komórki na podstawie liczby żywych sąsiadów
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

    // Metoda do aktualizacji stanu komórki, powiadamiająca listenerów o zmianie
    public void updateState(boolean newState) {
        boolean oldState = value;
        value = newState;
        support.firePropertyChange("value", oldState, newState);
    }

    // Nadpisanie metody toString()
    @Override
    public String toString() {
        // Tworzymy reprezentację sąsiadów, wypisując tylko ich stan (value)
        String neighborsString = neighbors.stream()
                .map(neighbor -> neighbor != null ? String.valueOf(neighbor.value) : "null")
                .toList()
                .toString();

        // Główna reprezentacja toString()
        return MoreObjects.toStringHelper(this)
                .add("Stan komorki", value)
                .add("Lista sasiadow", neighborsString)
                .toString();

    }

    // Nadpisanie metody hashCode(), aby porównywać obiekty na podstawie wartości i listy sąsiadów
    @Override
    public int hashCode() {
        // Metoda hashCode() służy w Javie do zwrócenia (w miarę) unikalnej wartości liczbowej
        // typu int dla każdego unikalnego obiektu.
        return Objects.hashCode(value, neighbors);
    }

    // Nadpisanie metody equals(), aby porównywać obiekty na podstawie wartości i listy sąsiadów
    @Override
    public boolean equals(Object obj) {
        // obj instanceof GameOfLifeCell sprawdza czy obj jest instancją klasy GameOfLifeCell
        if (obj instanceof GameOfLifeCell) {
            // Rzutuje obj typu Object na typ GameOfLifeCell
            GameOfLifeCell other = (GameOfLifeCell) obj;
            // Porównuje wartości i listy sąsiadów, zwraca true jeśli są równe, w przeciwnym wypadku false
            return Objects.equal(value, other.value) && Objects.equal(neighbors, other.neighbors);
        }
        return false;
    }

    @Override
    public int compareTo(GameOfLifeCell o) {
        return Boolean.compare(value, o.value);
    }

    @Override
    public GameOfLifeCell clone() {
        GameOfLifeCell clone = new GameOfLifeCell(value);
        for (int i = 0; i < neighbors.size(); i++) {
            clone.neighbors.set(i, neighbors.get(i));
        }
        return clone;
    }
}
