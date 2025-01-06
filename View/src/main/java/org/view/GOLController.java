package org.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

// import własnych klas
import org.apache.logging.log4j.LogManager;
import org.team1.*;

import java.util.*;


// Ta klasa odpowiada za:
// Obsługę zdarzeń związanych z interfejsem użytkownika.
// Przechowywanie referencji do elementów interfejsu użytkownika.
// Implementację logiki aplikacji.
public class GOLController {

    private int width = 4;
    private int height = 4;
    private int option;

    @FXML
    private Button languageButtonPL;

    @FXML
    private Button languageButtonEN;

    @FXML
    private TextField widthField;

    @FXML
    private Label widthLabel;

    @FXML
    private TextField heightField;

    @FXML
    private Label heightLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Label optionsLabel;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    @FXML
    private Label authors;

    public ToggleGroup groupOfLife;

    GameOfLifeBoard theGame;

    @FXML
    public void initialize() {

        TextFieldUtils.applyMinMaxFormatter(widthField, 4, 20);
        TextFieldUtils.applyMinMaxFormatter(heightField, 4, 20);

        // Grupa przycisków aby wybrać opcję
        setGroup();

        // Polski język jako domyślny
        setLanguage("pl", "PL");

        // Wykorzystanie ListResourceBundle
        ResourceBundle authorsBundle = ResourceBundle.getBundle("org.view.AuthorsResource");
        authors.setText(authorsBundle.getString("author1") + ", " + authorsBundle.getString("author2"));

        confirmButton.setOnAction(e -> {
            if (!widthField.getText().isEmpty()) {
                width = Integer.parseInt(widthField.getText());

            }
            if (!heightField.getText().isEmpty()) {
                height = Integer.parseInt(heightField.getText());
            }
            getOption();
            theGame = createGame();
            try {
                FXMLLoader loaderBoardView = GOLApplication.switchView("BoardView.fxml");
                BoardController controller = loaderBoardView.getController();
                controller.initializeBoard(width, height, theGame);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        languageButtonPL.setOnAction(e -> {
            if (!Objects.equals(GOLApplication.getBundle().getLocale().getLanguage(), "pl")) {
                setLanguage("pl", "PL");
            }
        });

        languageButtonEN.setOnAction(e -> {
            if (!Objects.equals(GOLApplication.getBundle().getLocale().getLanguage(), "en")) {
                setLanguage("en", "US");
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

    private void setLanguage(String language, String country) {
        Locale currentLocale = new Locale(language, country);
        GOLApplication.setLanguage(currentLocale, "settings.title");
        Bundle.getInstance().setLocale(currentLocale);

        ResourceBundle messages = GOLApplication.getBundle();

        widthLabel.setText(messages.getString("settings.width.des"));
        heightLabel.setText(messages.getString("settings.height.des"));
        widthField.setPromptText(messages.getString("settings.size.placeholder"));
        heightField.setPromptText(messages.getString("settings.size.placeholder"));
        optionsLabel.setText(messages.getString("settings.option.des"));
        option1.setText(messages.getString("settings.option.1"));
        option2.setText(messages.getString("settings.option.2"));
        option3.setText(messages.getString("settings.option.3"));
        confirmButton.setText(messages.getString("settings.start"));
        languageButtonPL.setText(messages.getString("settings.languagePL"));
        languageButtonEN.setText(messages.getString("settings.languageEN"));

    }

}


