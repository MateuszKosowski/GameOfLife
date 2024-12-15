package org.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import org.team1.*;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import java.io.File;


public class BoardController {
    @FXML
    private GridPane boardPane;

    @FXML
    private Label boardSizeLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    private GameOfLifeBoard gameOfLifeBoard;

    public void initializeBoard(int width, int height, GameOfLifeBoard game) {
        gameOfLifeBoard = game;
        GameOfLifeCell[][] boardState = gameOfLifeBoard.getBoard();
        int cellSize = 35;
        // Wyświetl informacje o wymiarach planszy
        boardSizeLabel.setText("Plansza: " + width + " x " + height);
        saveButton = new Button("Zapisz plansze");
        loadButton = new Button("Wczytaj plansze");

        saveButton.setOnAction(e -> saveFile());
        loadButton.setOnAction(e -> loadFile());

        // Dodaj przykładowe kafelki do planszy (np. przyciski lub pola)
        boardPane.getChildren().clear(); // Wyczyść, jeśli istnieje zawartość
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Tworzenie przycisku jako komórki
                Button cell = new Button();

                cell.setMinSize(cellSize, cellSize);
                cell.setMaxSize(cellSize, cellSize);

                // Ustawienie koloru w zależności od stanu
                boolean isFilled = boardState[y][x].getValue();
                updateCellStyle(cell, isFilled);

                final int r = y, c = x; // Potrzebne do lambdy
                cell.setOnAction(event -> {
                    gameOfLifeBoard.set(r, c, !boardState[r][c].getValue()); // Zmień stan komórki
                    updateCellStyle(cell, boardState[r][c].getValue());
                });

                boardPane.add(cell, x, y);
            }
        }
    }

    private void updateCellStyle(Button cell, boolean isFilled) {
        if (isFilled) {
            cell.setStyle("-fx-background-color: black; -fx-border-color: black;");
        } else {
            cell.setStyle("-fx-background-color: white; -fx-border-color: black;");
        }
    }

    // Funkcja do zapisywania planszy
    @FXML
    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Pokazuje okno wyboru pliku
        File file = fileChooser.showSaveDialog(boardPane.getScene().getWindow());

        if (file != null) {
            try {
                // Ensure file has correct extension
                String path = file.getPath();
                if (!path.endsWith(".txt")) {
                    path += ".txt";
                }

                File saveFile = new File(path);
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }

                try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(path)) {
                    dao.write(gameOfLifeBoard);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Game saved successfully!");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Could not save game: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Funkcja do wczytywania planszy
    @FXML
    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Pokazuje okno wyboru pliku
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // Wczytaj planszę z pliku (tutaj musisz zaimplementować odczyt planszy)
            System.out.println("Wczytano z: " + file.getAbsolutePath());
            try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(file.getName())) {
                gameOfLifeBoard = dao.read();
                GameOfLifeCell[][] board = gameOfLifeBoard.getBoard();
                int width = board[0].length;
                int height = board.length;
                initializeBoard(width, height, gameOfLifeBoard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
