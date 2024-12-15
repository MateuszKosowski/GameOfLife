package org.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

// import własnych klas
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.team1.*;


public class BoardController {
    @FXML
    private GridPane boardPane;

    @FXML
    private Label boardSizeLabel;

    private GameOfLifeBoard gameOfLifeBoard;

    public void initializeBoard(int width, int height, GameOfLifeBoard game) {
        gameOfLifeBoard = game;
        GameOfLifeCell[][] boardState = gameOfLifeBoard.getBoard();
        int cellSize = 35;
        // Wyświetl informacje o wymiarach planszy
        boardSizeLabel.setText("Plansza: " + width + " x " + height);

        // Dodaj przykładowe kafelki do planszy (np. przyciski lub pola)
        boardPane.getChildren().clear(); // Wyczyść, jeśli istnieje zawartość
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Tworzenie przycisku jako komórki
                Pane cell = new Pane();

                cell.setMinSize(cellSize, cellSize);
                cell.setMaxSize(cellSize, cellSize);

                // Ustawienie koloru w zależności od stanu
                boolean isFilled = boardState[y][x].getValue();
                updateCellStyle(cell, isFilled);

                boardPane.add(cell, x, y);
            }
        }
    }

    private void updateCellStyle(Pane cell, boolean isFilled) {
        if (isFilled) {
            cell.setStyle("-fx-background-color: black; -fx-border-color: black;");
        } else {
            cell.setStyle("-fx-background-color: white; -fx-border-color: black;");
        }
    }
}
