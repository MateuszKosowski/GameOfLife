module org.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.team1;
    requires org.apache.logging.log4j.core;

    opens org.view to javafx.fxml;
    exports org.view;
}