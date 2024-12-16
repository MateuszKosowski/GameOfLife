package org.view;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

public class TextFieldUtils {

    public static void applyMinMaxFormatter(TextField field, int min, int max) {
        // Konwerter tekstu na liczbę całkowitą
        IntegerStringConverter converter = new IntegerStringConverter();

        // TextFormatter bez natychmiastowego ograniczenia zakresu
        TextFormatter<Integer> formatter = new TextFormatter<>(converter, min, change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("\\d*")) { // Pozwala na puste pole i cyfry
                return change;
            }
            return null; // Odrzuca inne znaki
        });

        // Po zakończeniu edycji wymuszamy wartość w zakresie
        formatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) { // Jeśli wartość jest ustawiona
                if (newValue < min) {
                    formatter.setValue(min); // Ustawiamy minimalną wartość
                } else if (newValue > max) {
                    formatter.setValue(max); // Ustawiamy maksymalną wartość
                }
            }
        });

        // Ustawienie TextFormatter na TextField
        field.setTextFormatter(formatter);

        // Pole tekstowe pozostaje puste na starcie
        field.clear();
    }
}
