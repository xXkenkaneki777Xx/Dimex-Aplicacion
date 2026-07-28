module com.example.appdimex {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.appdimex to javafx.fxml;
    exports com.example.appdimex;
    exports com.example.appdimex.Controllers;
    opens com.example.appdimex.Controllers to javafx.fxml;
}