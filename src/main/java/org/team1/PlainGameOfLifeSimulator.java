package org.team1;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {
    @Override
    public void doStep(GameOfLifeBoard board) {
        boolean[][] boardCopy = board.getBoard();
        int aliveNeighbors;
        int i;
        int j;

        for (i = 0; i < boardCopy.length; i++) {
            for (j = 0; j < boardCopy[0].length; j++) {
                aliveNeighbors = countAliveNeighbors(i, j, board.getBoard());
                if (boardCopy[i][j]) {
                    boardCopy[i][j] = aliveNeighbors == 2 || aliveNeighbors == 3;
                } else {
                    if (aliveNeighbors == 3) {
                        boardCopy[i][j] = true;
                    }
                }
            }
        }

        for (i = 0; i < boardCopy.length; i++) {
            for (j = 0; j < boardCopy[0].length; j++)  {
                board.set(i, j, boardCopy[i][j]);
            }
        }
    }

    private int countAliveNeighbors(int x, int y, boolean[][] board) {
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
