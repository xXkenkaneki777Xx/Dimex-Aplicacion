package com.example.appdimex.Controllers;

import com.example.appdimex.DB.LoginDao;
import com.example.appdimex.model.Usuario;
import com.example.appdimex.Views.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField contrasenaField;

    @FXML
    private Button ingresarButton;

    @FXML
    private Label mensajeLabel;

    private LoginDao loginDao = new LoginDao();

    @FXML
    public void initialize() {
        cargarLogo();
        configurarEventos();


        contrasenaField.setOnAction(event -> iniciarSesion());
        usuarioTextField.setOnAction(event -> contrasenaField.requestFocus());
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

    private void configurarEventos() {
        ingresarButton.setOnAction(event -> iniciarSesion());
    }

    private void iniciarSesion() {
        String usuario = usuarioTextField.getText().trim();
        String contrasena = contrasenaField.getText().trim();


        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mensajeLabel.setText("Por favor, complete todos los campos");
            mensajeLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Deshabilitar botón mientras se procesa
        ingresarButton.setDisable(true);
        mensajeLabel.setText("Verificando credenciales...");
        mensajeLabel.setStyle("-fx-text-fill: #007bff;");


        Usuario usuarioAutenticado = loginDao.autenticar(usuario, contrasena);

        if (usuarioAutenticado != null) {
            mensajeLabel.setText("¡Bienvenido " + usuarioAutenticado.getNombre() + "!");
            mensajeLabel.setStyle("-fx-text-fill: green;");


            if (usuarioAutenticado.getRol().equals("ADMIN")) {
                abrirVentanaAdmin(usuarioAutenticado);
            } else {
                abrirVentanaProspecto(usuarioAutenticado);
            }
        } else {
            mensajeLabel.setText("Usuario o contraseña incorrectos");
            mensajeLabel.setStyle("-fx-text-fill: red;");
            contrasenaField.clear();
            contrasenaField.requestFocus();
        }


        ingresarButton.setDisable(false);
    }

    private void abrirVentanaAdmin(Usuario usuario) {

    }

    private void abrirVentanaProspecto(Usuario usuario) {

    }
}
