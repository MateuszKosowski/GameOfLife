package org.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.team1.*;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import java.io.File;
import java.util.ResourceBundle;


public class BoardController {

    private static final Logger logger = LogManager.getLogger(BoardController.class);

    @FXML
    private GridPane boardPane;

    @FXML
    private Label boardSizeLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    @FXML Button doStepButton;

    private GameOfLifeBoard gameOfLifeBoard;

    public void initializeBoard(int width, int height, GameOfLifeBoard game) {
        gameOfLifeBoard = game;
        GameOfLifeCell[][] boardState = gameOfLifeBoard.getBoard();


        ResourceBundle messages = GOLApplication.getBundle();
        GOLApplication.setLanguage(messages.getLocale(), "game.title");
        boardSizeLabel.setText(messages.getString("game.size") + " " + width + " x " + height);
        saveButton.setText(messages.getString("game.save.button"));
        loadButton.setText(messages.getString("game.load.button"));
        doStepButton.setText(messages.getString("game.doStep.button"));

        saveButton.setOnAction(e -> {
            try {
                saveFile();
            } catch (GolWriteExp ex) {
                logger.error("{}", Bundle.getInstance().getString("exp.write.file"));
            }
        });
        loadButton.setOnAction(e -> {
            try {
                loadFile();
            } catch (GolReadExp ex) {
                logger.error("{}", Bundle.getInstance().getString("exp.read.file"));
            }
        });

        initializeCells(boardState, width, height);
    }

    private void initializeCells(GameOfLifeCell[][] boardState, int width, int height) {
        // Dodaj przykładowe kafelki do planszy (np. przyciski lub pola)
        boardPane.getChildren().clear(); // Wyczyść, jeśli istnieje zawartość
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Tworzenie przycisku jako komórki
                GameOfLifeCellAdapter adapter = new GameOfLifeCellAdapter(boardState[y][x]);
                Button cell = new Button();

                cell.setMinSize(35, 35);
                cell.setMaxSize(35, 35);

                // Ustawienie koloru w zależności od stanu
                boolean isFilled = boardState[y][x].getValue();
                updateCellStyle(cell, isFilled);

                cell.setOnAction(event -> {
                    adapter.valueProperty().set(!adapter.valueProperty().get());
                    updateCellStyle(cell, adapter.valueProperty().get());
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
    private void saveFile() throws GolWriteExp {
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
                showAlert(Alert.AlertType.ERROR, messages.getString("game.fail"), messages.getString("game.save.fail.message"));
                throw new GolWriteExp(e);
            }
        }
    }

    // Funkcja do wczytywania planszy
    @FXML
    private void loadFile() throws GolReadExp {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(boardPane.getScene().getWindow());

        if (file != null) {
                try (FileGameOfLifeBoardDao dao = new FileGameOfLifeBoardDao(file.getPath())) {
                    gameOfLifeBoard = dao.read();
                    GameOfLifeCell[][] board = gameOfLifeBoard.getBoard();
                    int width = board[0].length;
                    int height = board.length;
                    initializeBoard(width, height, gameOfLifeBoard);
                    ResourceBundle messages = GOLApplication.getBundle();
                    showAlert(Alert.AlertType.INFORMATION, messages.getString("game.success"), messages.getString("game.load.success.message"));
                } catch (Exception e) {
                    ResourceBundle messages = GOLApplication.getBundle();
                    showAlert(Alert.AlertType.ERROR, messages.getString("game.fail"), messages.getString("game.load.fail.message"));
                    throw new GolReadExp(e);
                }
        }
    }

    @FXML
    private void doStep() {
        gameOfLifeBoard.doSimulationStep();
        GameOfLifeCell[][] board = gameOfLifeBoard.getBoard();
        initializeCells(board, board[0].length, board.length);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
