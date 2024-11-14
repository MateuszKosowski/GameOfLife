package org.team1;

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
