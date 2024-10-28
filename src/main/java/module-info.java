module org.example.ejercicioi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;


    opens org.example.ejercicioi to javafx.fxml;
    exports org.example.ejercicioi.model;
    opens org.example.ejercicioi.model to javafx.fxml;
    exports org.example.ejercicioi.app;
    opens org.example.ejercicioi.app to javafx.fxml;
}