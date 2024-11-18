/*-

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed unde * #%L
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class GameOfLifeLine implements PropertyChangeListener {

    private int aliveCount = 0;
    protected List<GameOfLifeCell> line;

    // Konstruktor
    public GameOfLifeLine(List<GameOfLifeCell> line) {
        this.line = line;
        for (GameOfLifeCell cell : line) {
            cell.addPropertyChangeListener(this);
            if (cell.getValue()) {
                aliveCount++;
            }
        }
    }

    // Getter dla linii
    public List<GameOfLifeCell> getLine() {
        return this.line;
    }

    // Metoda aktualizująca liczbę żywych komórek, jeśli któraś zmieniła swoją wartość
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


    // Metoda zwracająca liczbę żywych komórek
    public int countAliveCells() {
        return aliveCount;
    }

    // Metoda zwracająca liczbę martwych komórek
    public int countDeadCells() {
        return line.size() - aliveCount;
    }

    @Override
    public String toString() {
        String lineString = line.stream()
                .map(line -> line != null ? String.valueOf(line.getValue()) : "null")
                .toList()
                .toString();

        return MoreObjects.toStringHelper(this)
                .add("Liczba zywych komorek", aliveCount)
                .add("Zawartosc lini", lineString)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(aliveCount, line);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameOfLifeLine) {
            GameOfLifeLine that = (GameOfLifeLine) obj;
            return Objects.equal(this.aliveCount, that.aliveCount) && Objects.equal(this.line, that.line);
        }
        return false;
    }
}
