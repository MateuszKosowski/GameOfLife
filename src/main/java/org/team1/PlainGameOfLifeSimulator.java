package org.team1;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {

    @Override
    public void doStep(GameOfLifeBoard board) {
        boolean[][] newBoard = new boolean[board.getBoard().length][board.getBoard()[0].length];
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                newBoard[i][j] = board.getBoard()[i][j].nextState();
            }
        }
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                board.getBoard()[i][j].updateState(newBoard[i][j]);
            }
        }
    }

    private void assignNeighborsToCell(int x, int y, GameOfLifeCell[][] board) {
        GameOfLifeCell[] neighbors = new GameOfLifeCell[8];
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

                if (board[tempI][tempJ].getValue() && (tempI != x || tempJ != y)) {
                    neighbors[index] = board[tempI][tempJ];
                }
                index++;
            }
        }
        board[x][y].setNeighbors(neighbors);
    }
}
