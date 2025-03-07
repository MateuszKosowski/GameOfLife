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
import org.apache.logging.log4j.LogManager;

import java.io.Serializable;
import java.util.*;

public class GameOfLifeBoard implements Serializable, Cloneable {

    private String name = "";
    private final GameOfLifeCell[][] board;
    private final GameOfLifeSimulator gameOfLifeSimulator;

    // Konstruktor, który tworzy planszę o podanych wymiarach i przypisuje obiekt GameOfLifeSimulator
    public GameOfLifeBoard(int height, int width, GameOfLifeSimulator gameOfLifeSimulator) {
        if (height < 0 || width < 0) {
            height = 3;
            width = 3;
        }
        name = String.valueOf(new Random().nextInt());
        this.gameOfLifeSimulator = java.util.Objects
                .requireNonNullElseGet(gameOfLifeSimulator, PlainGameOfLifeSimulator::new);
        this.board = new GameOfLifeCell[height][width];

        fillBoard();
    }

    // Konstruktor z opcja, który tworzy planszę o podanych wymiarach i przypisuje obiekt GameOfLifeSimulator
    public GameOfLifeBoard(int height, int width, GameOfLifeSimulator gameOfLifeSimulator, int option) {
        if (height < 0 || width < 0) {
            height = 3;
            width = 3;
        }
        name = String.valueOf(new Random().nextInt());
        this.gameOfLifeSimulator = java.util.Objects
                .requireNonNullElseGet(gameOfLifeSimulator, PlainGameOfLifeSimulator::new);
        this.board = new GameOfLifeCell[height][width];

        fillBoardWithOption(option);
    }

    // Konstruktor z opcja, który tworzy planszę o podanych wymiarach i przypisuje obiekt GameOfLifeSimulator
    public GameOfLifeBoard(String name, int height, int width, GameOfLifeSimulator gameOfLifeSimulator, int option) {
        if (name.isBlank() || name.isEmpty()) {
            name = String.valueOf(new Random().nextInt());
        } else {
            this.name = name;
        }
        if (height < 0 || width < 0) {
            height = 3;
            width = 3;
        }
        this.gameOfLifeSimulator = java.util.Objects
                .requireNonNullElseGet(gameOfLifeSimulator, PlainGameOfLifeSimulator::new);
        this.board = new GameOfLifeCell[height][width];

        fillBoardWithOption(option);
    }

    public GameOfLifeBoard(String name, int height, int width) {
        if (name.isBlank()) {
            name = String.valueOf(new Random().nextInt());
        }
        if (height < 0 || width < 0) {
            height = 3;
            width = 3;
        }
        this.gameOfLifeSimulator = new PlainGameOfLifeSimulator();
        this.board = new GameOfLifeCell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new GameOfLifeCell(false);
            }
        }
    }

    // Zwracamy kopię naszej planszy
    public GameOfLifeCell[][] getBoard() {
        GameOfLifeCell[][] tempBoard = new GameOfLifeCell[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, board[0].length);
        }
        return tempBoard;
    }

    public String getName() {
        LogManager.getLogger().info("NAME: {}", name);
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    // Zwracamy wartość obiektu Cell na podstawie współrzędnych
    public boolean get(int x, int y) {
        return board[x][y].getValue();
    }

    // Ustawiamy wartość dla obiektu Cell na podstawie współrzędnych
    public void set(int x, int y, boolean value) {
        board[x][y].updateState(value);
    }

    // Wykonujemy krok symulacji przekazując obiekt GameOfLifeBoard do obiektu GameOfLifeSimulator
    public void doSimulationStep() {
        gameOfLifeSimulator.doStep(this);
    }

    // Wypełniamy planszę wartościami false i przypisujemy sąsiadów dla każdego Cell
    public void fillFalse() {
        for (GameOfLifeCell[] gameOfLifeCells : this.board) {
            for (int j = 0; j < this.board[0].length; j++) {
                gameOfLifeCells[j].updateState(false);
            }
        }

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                assignNeighborsToCell(this.board, i, j);
            }
        }
    }

    // Wypełniamy planszę losowymi wartościami i przypisujemy sąsiadów dla każdego Cell
    private void fillBoard() {
        Random rand = new Random();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                board[i][j] = new GameOfLifeCell(rand.nextBoolean());
            }
        }
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                assignNeighborsToCell(board, i, j);
            }
        }
    }

    private void fillBoardWithOption(int option) {
        Random rand = new Random();
        int width = board[0].length;
        int height = board.length;
        int totalCells = width * height;
        int cellsToFill = (int) (totalCells * (option / 100.0));

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                board[i][j] = new GameOfLifeCell(false);
            }
        }

        //Tworzony jest pusty Set, który będzie przechowywać unikalne indeksy komórek do wypełnienia.
        Set<Integer> filledCells = new HashSet<>();

        //Dopóki Set nie zawiera odpowiedniej liczby indeksów, losowane są kolejne indeksy i dodawane do Setu.
        while (filledCells.size() < cellsToFill) {
            // Generuje losową liczbę całkowitą od 0 do totalCells - 1
            int cellIndex = rand.nextInt(totalCells);
            // Próbuje dodać losowy indeks komórki do zestawu, jeśli już tam jest, to nie zostanie dodany.
            filledCells.add(cellIndex);
        }

        //Iteruje po wszystkich indeksach komórek w zestawie i ustawia je na true.
        for (int cellIndex : filledCells) {
            // Oblicza współrzędne komórki na podstawie indeksu.
            // Dzieli indeks komórki przez szerokość planszy, aby uzyskać indeks wiersza.
            int i = cellIndex / width;
            // Oblicza resztę z dzielenia indeksu komórki przez szerokość planszy, aby uzyskać indeks kolumny.
            int j = cellIndex % width;
            set(i, j, true);
        }

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                assignNeighborsToCell(board, i, j);
            }
        }
    }

    // Każdy obiekt Cell ma 8 sąsiadów, więc przypisujemy sąsiadów dla każdego Cell
    public void assignNeighborsToCell(GameOfLifeCell[][] board, int x, int y) {
        List<GameOfLifeCell> neighbors = Arrays.asList(new GameOfLifeCell[8]);
        int index = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                int tempI = i;
                int tempJ = j;

                if (tempI < 0) {
                    tempI = board.length - 1;
                }
                if (tempI == board.length) {
                    tempI = 0;
                }
                if (tempJ < 0) {
                    tempJ = board[0].length - 1;
                }
                if (tempJ == board[0].length) {
                    tempJ = 0;
                }

                if (tempI != x || tempJ != y) {
                    neighbors.set(index, board[tempI][tempJ]);
                    index++;
                }
            }
        }
        board[x][y].setNeighbors(neighbors);
    }

    // Zwracamy wiersz planszy na podstawie indeksu jako obiekt GameOfLifeRow
    public GameOfLifeRow getRow(int index) {
        List<GameOfLifeCell> row = Arrays.asList(new GameOfLifeCell[board[0].length]);
        for (int i = 0; i < board[0].length; i++) {
            row.set(i, board[index][i]);
        }
        return new GameOfLifeRow(row);
    }

    // Zwracamy kolumnę planszy na podstawie indeksu jako obiekt GameOfLifeColumn
    public GameOfLifeColumn getColumn(int index) {
        List<GameOfLifeCell> column = Arrays.asList(new GameOfLifeCell[board.length]);
        for (int i = 0; i < board.length; i++) {
            column.set(i, board[i][index]);
        }
        return new GameOfLifeColumn(column);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", Arrays.deepToString(board))
                .add("gameOfLifeSimulator", gameOfLifeSimulator.toString())
                .toString();
    }

    @Override
    public int hashCode() {
        int result = 2137;
        result = 31 * result + calculateBoardHashCode();
        return result;
    }

    private int calculateBoardHashCode() {
        int result = 420;
        for (GameOfLifeCell[] gameOfLifeCells : board) {
            for (int j = 0; j < board[0].length; j++) {
                result = 31 * result + (gameOfLifeCells[j].getValue() ? 1 : 0);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GameOfLifeBoard that = (GameOfLifeBoard) obj;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getValue() != that.board[i][j].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public GameOfLifeBoard clone() {
        GameOfLifeBoard clone = new GameOfLifeBoard(board.length, board[0].length, gameOfLifeSimulator);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                clone.board[i][j] = board[i][j].clone();
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                assignNeighborsToCell(clone.board, i, j);
            }
        }
        return clone;
    }
}
