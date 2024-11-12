package org.team1;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameOfLifeBoard {

    private final GameOfLifeCell[][] board;
    private final GameOfLifeSimulator gameOfLifeSimulator;

    // Konstruktor, który tworzy planszę o podanych wymiarach i przypisuje obiekt GameOfLifeSimulator
    public GameOfLifeBoard(int width, int height, GameOfLifeSimulator gameOfLifeSimulator) throws Exception {
        if (width < 0 || height < 0) {
            throw new Exception("Width or height is negative number");
        }
        this.gameOfLifeSimulator = Objects.requireNonNullElseGet(gameOfLifeSimulator, PlainGameOfLifeSimulator::new);
        this.board = new GameOfLifeCell[width][height];

        fillBoard();
    }

    // Zwracamy kopię naszej planszy
    public GameOfLifeCell[][] getBoard() {
        GameOfLifeCell[][] tempBoard = new GameOfLifeCell[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, board[0].length);
        }
        return tempBoard;
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
                assignNeighborsToCell(i, j);
            }
        }
    }

    // Wypełniamy planszę losowymi wartościami i przypisujemy sąsiadów dla każdego Cell
    private void fillBoard() {
        Random rand = new Random();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = new GameOfLifeCell(rand.nextBoolean());
            }
        }
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                assignNeighborsToCell(i, j);
            }
        }
    }

    // Każdy obiekt Cell ma 8 sąsiadów, więc przypisujemy sąsiadów dla każdego Cell
    public void assignNeighborsToCell(int x, int y) {
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

}
