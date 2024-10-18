package org.team1;

import java.util.Random;

public class GameOfLifeBoard {
    private boolean[][] board;

    public boolean[][] getBoard() {
        boolean[][] tempBoard = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, board[0].length);
        }
        return tempBoard;
    }

    public boolean getCoordinate(int x, int y) {
        return board[x][y];
    }

    public void setCoordinate(int x, int y, boolean value) {
        board[x][y] = value;
    }

    public GameOfLifeBoard(int width, int height) throws Exception {
        if (width < 0 || height < 0) {
            throw new Exception("Width or height is negative number");
        }
        this.board = new boolean[width][height];
        fillBoard();
    }

    public void fillFalse() {
        board = new boolean[board.length][board[0].length];
    }

    public void doStep() {
        boolean[][] newBoard = new boolean[board.length][board[0].length];
        int aliveNeighbors;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                aliveNeighbors = countAliveNeighbors(i, j);
                if (board[i][j]) {
                    newBoard[i][j] = aliveNeighbors == 2 || aliveNeighbors == 3;
                } else {
                    if (aliveNeighbors == 3) {
                        newBoard[i][j] = true;
                    }
                }
            }
        }

        board = newBoard;
    }

    private void fillBoard() {
        Random rand = new Random();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = rand.nextBoolean();
            }
        }
    }

    private int countAliveNeighbors(int x, int y) {
        int aliveNeighbors = 0;
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

                if (board[tempI][tempJ] && (tempI != x || tempJ != y)) {
                    aliveNeighbors++;
                }
            }
        }
        return aliveNeighbors;
    }
}
