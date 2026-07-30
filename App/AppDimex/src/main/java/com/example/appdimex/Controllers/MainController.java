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
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button cambiarTasasButton; // Nuevo botón

    private Usuario usuarioLogueado;

    @FXML
    private ImageView logoImageView;

    @FXML
    public void initialize() {
        cargarLogo();
    }

    // NAVEGACIÓN A COTIZACIÓN
    @FXML
    private void abrirCotizacion(ActionEvent event) {
        navegarA(event, "/com/example/appdimex/Cotizacion.fxml", "Cotización");
    }

    // NAVEGACIÓN A REGISTRO DE PROSPECTO
    @FXML
    private void abrirRegistroProspecto(ActionEvent event) {
        navegarA(event, "/com/example/appdimex/RegistroP.fxml", "Registro de Prospecto");
    }

    // NAVEGACIÓN A CONSULTA DE PROSPECTOS
    @FXML
    private void abrirConsultaProspectos(ActionEvent event) {
        navegarA(event, "/com/example/appdimex/ConsultaProspectos.fxml", "Consulta de Prospectos");
    }

    // Método auxiliar reutilizable para cambiar de pantalla en la misma Scene (340x650)
    private void navegarA(ActionEvent event, String rutaFxml, String nombreVista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista de " + nombreVista + ": " + e.getMessage());
        }
    }

    @FXML
    private void abrirCambiarTasas(ActionEvent event) {
        // Método para abrir la ventana donde se editarán las tasas de nómina/domiciliado
        navegarA(event, "/com/example/appdimex/CambiarTasas.fxml", "Cambiar Tasas");
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;

        if (usuario != null && "ADMIN".equalsIgnoreCase(usuario.getRol())) {
            // Si es ADMIN, mostramos el botón y hacemos que ocupe espacio en el layout
            cambiarTasasButton.setVisible(true);
            cambiarTasasButton.setManaged(true);
        } else {
            // Si es prospecto o vendedor, lo ocultamos por completo
            cambiarTasasButton.setVisible(false);
            cambiarTasasButton.setManaged(false);
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