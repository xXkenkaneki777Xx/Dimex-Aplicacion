module com.example.appdimex {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires java.desktop;


    opens com.example.appdimex.model to javafx.base, javafx.fxml;

    opens com.example.appdimex to javafx.fxml;
    exports com.example.appdimex;
    exports com.example.appdimex.Controllers;
    opens com.example.appdimex.Controllers to javafx.fxml;
}