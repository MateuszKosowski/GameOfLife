package org.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

// import własnych klas
import javafx.scene.layout.GridPane;
import org.team1.*;


public class BoardController {
    private int width;
    private int height;
    @FXML
    private GridPane boardPane;

    @FXML
    private Label boardSizeLabel;

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void initializeBoard(int width, int height) {
        // Wyświetl informacje o wymiarach planszy
        boardSizeLabel.setText("Plansza: " + width + " x " + height);

        // Dodaj przykładowe kafelki do planszy (np. przyciski lub pola)
        boardPane.getChildren().clear(); // Wyczyść, jeśli istnieje zawartość
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Label cell = new Label("[" + x + "," + y + "]");
                cell.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-padding: 5px;");
                boardPane.add(cell, x, y); // Dodaj do GridPane
            }
        }
    }

    public void initialize() {
        initializeBoard(width, height);
    }

}
