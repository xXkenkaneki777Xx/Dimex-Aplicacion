
package com.example.appdimex.Controllers;

import com.example.appdimex.model.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

public class MainController {

    @FXML
    private Button cambiarTasasButton;

    @FXML
    private ImageView logoImageView;

    private Usuario usuarioLogueado;

    @FXML
    public void initialize() {
        cargarLogo();
    }

    @FXML
    private void abrirCotizacion(ActionEvent event) {
        navegarA(
                event,
                "/com/example/appdimex/Cotizacion.fxml",
                "Cotización"
        );
    }

    @FXML
    private void abrirRegistroProspecto(ActionEvent event) {
        navegarA(
                event,
                "/com/example/appdimex/RegistroP.fxml",
                "Registro de Prospecto"
        );
    }

    @FXML
    private void abrirConsultaProspectos(ActionEvent event) {
        navegarA(
                event,
                "/com/example/appdimex/ConsultaProspectos.fxml",
                "Consulta de Prospectos"
        );
    }

    private void navegarA(ActionEvent event, String rutaFxml, String nombreVista) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(rutaFxml)
            );

            Parent root = loader.load();

            if (rutaFxml.equals("/com/example/appdimex/Cotizacion.fxml")) {
                CotizacionController cotizacionController =
                        loader.getController();

                cotizacionController.setUsuarioLogueado(usuarioLogueado);
            }

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            stage.getScene().setRoot(root);

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

            if (rutaFxml.equals("/com/example/appdimex/ConsultaProspectos.fxml")) {
                double nuevoAncho = bounds.getWidth() - 80;
                stage.setWidth(nuevoAncho);
            } else {
                stage.setWidth(340);
            }

            stage.setHeight(650);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();

            mostrarAlerta(
                    "Error",
                    "No se pudo cargar la vista de "
                            + nombreVista + ": " + e.getMessage()
            );
        }
    }

    @FXML
    private void abrirCambiarTasas(ActionEvent event) {
        navegarA(
                event,
                "/com/example/appdimex/CambiarTasas.fxml",
                "Cambiar Tasas"
        );
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;

        if (cambiarTasasButton != null) {
            boolean esAdministrador =
                    usuario != null
                            && "ADMIN".equalsIgnoreCase(usuario.getRol());

            cambiarTasasButton.setVisible(esAdministrador);
            cambiarTasasButton.setManaged(esAdministrador);
        }
    }

    private void cargarLogo() {
        try {
            Image image = new Image(
                    getClass().getResourceAsStream("/imagenes/logo.jpg")
            );

            if (image != null && !image.isError()) {
                logoImageView.setImage(image);
                logoImageView.setFitWidth(50);
                logoImageView.setFitHeight(50);
                logoImageView.setPreserveRatio(true);
            }

        } catch (Exception e) {
            System.err.println(
                    "Error al cargar logo: " + e.getMessage()
            );
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
