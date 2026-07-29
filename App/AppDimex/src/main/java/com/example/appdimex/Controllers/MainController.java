package com.example.appdimex.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private ImageView logoImageView;

    @FXML
    public void initialize() {
        cargarLogo();

    }

    @FXML
    private void abrirCotizacion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/Cotizacion.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de Cotización: " + e.getMessage());
        }
    }

    private void cargarLogo() {
        try {
            Image image = new Image(getClass().getResourceAsStream("/imagenes/logo.jpg"));
            if (image != null && !image.isError()) {
                logoImageView.setImage(image);
                logoImageView.setFitWidth(50);
                logoImageView.setFitHeight(50);
                logoImageView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar logo: " + e.getMessage());
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
