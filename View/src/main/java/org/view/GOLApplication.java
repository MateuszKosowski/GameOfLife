package org.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Ta klasa odpowiada za:
// Uruchamianie aplikacji.
// Załadowanie pliku GOLSettings.fxml.
// Ustawienie sceny i wyświetlenie głównego okna aplikacji (Stage).

public class GOLApplication extends Application {

    private static Stage stage;

    @Override
    // Metoda start() odpowiada za inicjalizację aplikacji JavaFX.
    // Jej argumentem jest obiekt Stage, który reprezentuje główne okno aplikacji.
    public void start(Stage pStage) throws IOException {

        stage = pStage;

        // FXMLLoader jest odpowiedzialny za wczytanie pliku FXML, który definiuje interfejs użytkownika.
        FXMLLoader fxmlLoader = new FXMLLoader(GOLApplication.class.getResource("GOLSettings.fxml"));

        // Scene reprezentuje zawartość okna aplikacji. przyjmuje jako argumenty obiekt Parent oraz szerokość i wysokość okna.
        Scene sceneSettings = new Scene(fxmlLoader.load(), 1200, 800);
        // Ustawienie tytułu okna.
        stage.setTitle("Ustawienia Game of Life");
        // Ustawienie sceny w oknie.
        stage.setScene(sceneSettings);
        // Wyświetlenie okna.
        stage.show();
    }


    public static FXMLLoader switchView(String fxmlFilePath) throws Exception {
        FXMLLoader loader = new FXMLLoader(GOLApplication.class.getResource(fxmlFilePath));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Game of Life");
        stage.setScene(scene);
        return loader;
    }

    public static void main(String[] args) {
        // Metoda launch() uruchamia aplikację JavaFX.
        launch();
    }
}
