package org.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

// import własnych klas
import org.team1.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


// Ta klasa odpowiada za:
// Obsługę zdarzeń związanych z interfejsem użytkownika.
// Przechowywanie referencji do elementów interfejsu użytkownika.
// Implementację logiki aplikacji.
public class GOLController {

    private int width;
    private int height;
    private int option;

    @FXML
    private TextField widthField;

    @FXML
    private TextField heightField;

    @FXML
    private Button confirmButton;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    public ToggleGroup groupOfLife;

    GameOfLifeBoard theGame;

    @FXML
    public void initialize() {

        setGroup();

        confirmButton.setOnAction(e -> {
            minMaxSize(widthField);
            width = Integer.parseInt(widthField.getText());
            minMaxSize(heightField);
            height = Integer.parseInt(heightField.getText());
            getOption();
            theGame = createGame();
            System.out.println("Width: " + widthField.getText() + " Height: " + heightField.getText()  + " Option: " + option);
            try {
                FXMLLoader loaderBoardView = GOLApplication.switchView("BoardView.fxml");
                BoardController controller = loaderBoardView.getController();
                controller.initializeBoard(width, height, theGame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void setGroup() {
        groupOfLife = new ToggleGroup();
        option1.setToggleGroup(groupOfLife);
        option2.setToggleGroup(groupOfLife);
        option3.setToggleGroup(groupOfLife);
    }

    private GameOfLifeBoard createGame() {
        final GameOfLifeSimulator gameOfLifeSimulator = new PlainGameOfLifeSimulator();
        GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard(height, width, gameOfLifeSimulator, option);

        return gameOfLifeBoard;
    }

    private void getOption() {
        if (option1.isSelected()) {
            option = 10;
        } else if (option2.isSelected()) {
            option = 30;
        } else if (option3.isSelected()) {
            option = 50;
        }
    }

    private void minMaxSize(TextField field) {
        String input = field.getText();
        try {
            int size = Integer.parseInt(input);
            if (size >= 4 && size <= 20) {
                field.setText(String.valueOf(size));
            } else {
                field.setText(String.valueOf(4));
            }
        } catch (NumberFormatException e) {
            field.setText(String.valueOf(4));
        }

    }

}


