package com.example.appdimex.Controllers;

import com.example.appdimex.DB.ProspectoDao;
import com.example.appdimex.Enums.Afiliacion;
import com.example.appdimex.Views.AyudaView;
import com.example.appdimex.model.Prospecto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class RegistroController {


    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private TextField bancoTextField;
    @FXML
    private TextArea direccionTextArea;

    @FXML
    private ChoiceBox<Afiliacion> afiliacionChoiceBox;

    @FXML
    private DatePicker fechaNacimientoDatePicker;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button realizarButton;

    @FXML
    private Button cancelarButton;
    @FXML
    private MenuItem consultarMenuItem;
    @FXML
    private MenuItem eliminarMenuItem;
    @FXML
    private MenuItem modificarMenuItem;
    @FXML
    private MenuItem ayudaMenuItem;

    @FXML
    public void initialize() {
        configurarChoiceBox();
        configurarBotones();
        cargarLogo();
        configurarMenu();
    }

    private void configurarMenu() {
        // Configurar acción para "Consultar"
        consultarMenuItem.setOnAction(event -> abrirVentanaConsulta());

        // Configurar acción para "Eliminar" (ejemplo)
        eliminarMenuItem.setOnAction(event -> {
            mostrarAlerta("Eliminar", "Funcionalidad en desarrollo");
        });

        // Configurar acción para "Modificar" (ejemplo)
        modificarMenuItem.setOnAction(event -> {
            mostrarAlerta("Modificar", "Funcionalidad en desarrollo");
        });
        ayudaMenuItem.setOnAction(event -> ventanaAyuda());
    }

    private void ventanaAyuda(){
        AyudaView ventanaAyuda = new AyudaView();
        ventanaAyuda.mostrar("Ventana de ayuda","Para atender asustos relacionados \na la institucion " +
                "le recomendamos asistir de forma presensial \nsi no puede llevar acabo esta accion o prefiere un trato a distancia" +
                "puede llamar a nuestra linea de ayuda al sigueite numero 715 120 16 04");
    }
    private void abrirVentanaConsulta() {
        try {
            // Cargar el FXML de la ventana de consulta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/ConsultaProspectos.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root, 800, 650);

            // Crear un nuevo Stage (ventana)
            Stage stage = new Stage();
            stage.setTitle("Consulta de Prospectos");
            stage.setScene(scene);


            // Mostrar la ventana
            stage.show();

            System.out.println("✅ Ventana de consulta abierta");

        } catch (Exception e) {
            System.err.println("❌ Error al abrir la ventana de consulta: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de consulta: " + e.getMessage());
        }
    }
    private void configurarChoiceBox() {
        afiliacionChoiceBox.getItems().setAll(Afiliacion.values());
        afiliacionChoiceBox.setValue(Afiliacion.IMSS);
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

    private void configurarBotones() {
        realizarButton.setOnAction(event -> realizarCotizacion());
        cancelarButton.setOnAction(event -> cancelar());

    }

    private void realizarCotizacion() {
        try {

            String nombre = nombreTextField.getText().trim();
            String apellidos = apellidosTextField.getText().trim();


            if (nombre.isEmpty() || apellidos.isEmpty()) {
                mostrarAlerta("Error", "Nombre y apellidos son obligatorios");
                return;
            }


            int telefono = 0;
            try {
                telefono = Integer.parseInt(telefonoTextField.getText().trim());
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "El teléfono debe ser un número válido");
                return;
            }

            String banco = bancoTextField.getText().trim();
            String direccion = direccionTextArea.getText().trim();


            Afiliacion afiliacion = afiliacionChoiceBox.getValue();
            if (afiliacion == null) {
                mostrarAlerta("Error", "Debes seleccionar una afiliación");
                return;
            }


            Date fechaNacimiento = null;
            if (fechaNacimientoDatePicker.getValue() != null) {

                fechaNacimiento = Date.valueOf(fechaNacimientoDatePicker.getValue());
            } else {
                mostrarAlerta("Error", "Debes seleccionar una fecha de nacimiento");
                return;
            }


            Prospecto nuevoProspecto = new Prospecto(
                    nombre,
                    apellidos,
                    telefono,
                    afiliacion,
                    banco,
                    fechaNacimiento,
                    direccion
            );


            ProspectoDao prospectoDao = new ProspectoDao();
            prospectoDao.registrar(nuevoProspecto);




        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    private void cancelar() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        nombreTextField.clear();
        apellidosTextField.clear();
        telefonoTextField.clear();
        bancoTextField.clear();
        direccionTextArea.clear();
        afiliacionChoiceBox.setValue(Afiliacion.IMSS);
        fechaNacimientoDatePicker.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}