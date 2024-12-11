package org.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


// Ta klasa odpowiada za:
// Obsługę zdarzeń związanych z interfejsem użytkownika.
// Przechowywanie referencji do elementów interfejsu użytkownika.
// Implementację logiki aplikacji.
public class GOLController {

    // Adnotacja @FXML oznacza, że pole będzie wstrzykiwane przez JavaFX.
    @FXML
    // TextField to pole tekstowe, w którym użytkownik może wprowadzać tekst.
    private TextField widthField;
    
    @FXML
    private TextField heightField;

}
