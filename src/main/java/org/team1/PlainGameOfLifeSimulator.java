package org.team1;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator {

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
