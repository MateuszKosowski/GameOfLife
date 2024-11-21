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

import java.io.Serializable;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator, Serializable {

    @Override
    public void doStep(GameOfLifeBoard board) {
        GameOfLifeCell[][] boardCopy = board.getBoard();
        boolean[][] newBoard = new boolean[board.getBoard().length][board.getBoard()[0].length];
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[0].length; j++) {
                newBoard[i][j] = boardCopy[i][j].nextState();
            }
        }
        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[0].length; j++) {
                board.set(i, j, newBoard[i][j]);
            }
        }
    }
}
