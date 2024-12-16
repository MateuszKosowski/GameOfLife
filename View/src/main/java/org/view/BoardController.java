package org.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import org.team1.*;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import java.io.File;
import java.util.ResourceBundle;


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

        ResourceBundle messages = GOLApplication.getBundle();
        GOLApplication.setLanguage(messages.getLocale(), "game.title");
        boardSizeLabel.setText(messages.getString("game.size") + " " + width + " x " + height);
        saveButton.setText(messages.getString("game.save.button"));
        loadButton.setText(messages.getString("game.load.button"));

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
                    ResourceBundle messages = GOLApplication.getBundle();
                    showAlert(Alert.AlertType.INFORMATION, messages.getString("game.success"), messages.getString("game.save.success.message"));
                }
            } catch (Exception e) {
                ResourceBundle messages = GOLApplication.getBundle();
                showAlert(Alert.AlertType.ERROR, messages.getString("game.fail"), messages.getString("game.save.fail.message") + " " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // Funkcja do wczytywania planszy
    @FXML
    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(boardPane.getScene().getWindow());

        if (file != null) {
            try {
                // Ensure file has correct extension
                String path = file.getPath();
                if (!path.endsWith(".txt")) {
                    path += ".txt";
                }

                try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(file.getPath())) {
                    gameOfLifeBoard = dao.read();
                    GameOfLifeCell[][] board = gameOfLifeBoard.getBoard();
                    int width = board[0].length;
                    int height = board.length;
                    initializeBoard(width, height, gameOfLifeBoard);
                    ResourceBundle messages = GOLApplication.getBundle();
                    showAlert(Alert.AlertType.INFORMATION, messages.getString("game.success"), messages.getString("game.load.success.message"));
                }
            } catch (Exception e) {
                ResourceBundle messages = GOLApplication.getBundle();
                showAlert(Alert.AlertType.ERROR, messages.getString("game.fail"), messages.getString("game.load.fail.message") + " " + e.getMessage());
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
