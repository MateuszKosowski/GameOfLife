module org.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.team1;

    opens org.view to javafx.fxml;
    exports org.view;
}