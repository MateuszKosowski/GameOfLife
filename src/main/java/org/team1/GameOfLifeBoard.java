package org.team1;

import java.util.Random;

public class GameOfLifeBoard {
    private boolean[][] board;

    public GameOfLifeBoard(int width, int height) throws Exception {
        if (width < 0 || height < 0) {
            throw new Exception("Width or height is negative number");
        }
        this.board = new boolean[width][height];
        fillBoard();
    }

    public boolean[][] getBoard() {
        boolean[][] tempBoard = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, tempBoard[i], 0, board[0].length);
        }
        return tempBoard;
    }

    public boolean get(int x, int y) {
        return board[x][y];
    }

    public void set(int x, int y, boolean value) {
        board[x][y] = value;
    }

    public void doSimulationStep() {
        new PlainGameOfLifeSimulator().doStep(this);
    }

    public void fillFalse() {
        board = new boolean[board.length][board[0].length];
    }

    private void fillBoard() {
        Random rand = new Random();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = rand.nextBoolean();
            }
        }
    }


}
