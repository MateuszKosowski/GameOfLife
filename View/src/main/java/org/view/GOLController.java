package org.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.IntegerStringConverter;


// Ta klasa odpowiada za:
// Obsługę zdarzeń związanych z interfejsem użytkownika.
// Przechowywanie referencji do elementów interfejsu użytkownika.
// Implementację logiki aplikacji.
public class GOLController {

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

    @FXML
    private ToggleGroup groupOfLife;

    @FXML
    public void initialize() {
        confirmButton.setOnAction(e -> {
            minMaxSize(widthField);
            minMaxSize(heightField);
            System.out.println("Width: " + widthField.getText() + " Height: " + heightField.getText());
        });
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
