
package com.example.appdimex.Controllers;

import com.example.appdimex.model.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ProspectoController {

    @FXML
    private Label welcomeText;

    private Usuario usuarioLogueado;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML
    private void registrarProducto() {

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void regresarAlMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/appdimex/main-view.fxml"
                    )
            );

            Parent root = loader.load();

            MainController mainController = loader.getController();

            mainController.setUsuarioLogueado(usuarioLogueado);

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.getScene().setRoot(root);

            stage.setWidth(340);
            stage.setHeight(650);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(
                    "No se pudo regresar a la pantalla principal: "
                            + e.getMessage()
            );
            alert.showAndWait();
        }
    }
}
