module org.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.view to javafx.fxml;
    exports org.view;
}