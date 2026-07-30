package com.example.appdimex.Controllers;

import com.example.appdimex.Enums.Cobro;
import com.example.appdimex.util.ConfiguracionTasas;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CambiarTasasController {

    @FXML
    private ImageView logoImageView;
    @FXML
    private ChoiceBox<Cobro> tipoCobroChoiceBox;
    @FXML
    private ListView<Double> tasasListView;
    @FXML
    private TextField nuevaTasaTextField;
    @FXML
    private Label mensajeLabel;

    @FXML
    public void initialize() {
        cargarLogo();

        tipoCobroChoiceBox.getItems().setAll(Cobro.values());
        tipoCobroChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            cargarListaTasas(newVal);
        });

        tipoCobroChoiceBox.setValue(Cobro.Domiciliado);
        cargarListaTasas(Cobro.Domiciliado);
    }

    private void cargarListaTasas(Cobro tipo) {
        if (tipo == null) return;

        List<Double> lista = (tipo == Cobro.Nomina)
                ? ConfiguracionTasas.getTasasNomina()
                : ConfiguracionTasas.getTasasDomiciliado();

        tasasListView.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void agregarNuevaTasa() {
        String texto = nuevaTasaTextField.getText().trim();
        if (texto.isEmpty()) {
            mostrarMensaje("Ingresa un valor para la tasa.", "#DC2626");
            return;
        }

        try {
            double nuevaTasa = Double.parseDouble(texto);
            if (nuevaTasa <= 0) {
                mostrarMensaje("La tasa debe ser mayor a 0.", "#DC2626");
                return;
            }

            Cobro seleccion = tipoCobroChoiceBox.getValue();
            if (seleccion == Cobro.Nomina) {
                ConfiguracionTasas.agregarTasaNomina(nuevaTasa);
            } else {
                ConfiguracionTasas.agregarTasaDomiciliado(nuevaTasa);
            }

            nuevaTasaTextField.clear();
            cargarListaTasas(seleccion);
            mostrarMensaje("Tasa agregada con éxito.", "#00A859");

        } catch (NumberFormatException e) {
            mostrarMensaje("Ingresa un número válido (ej. 32.5)", "#DC2626");
        }
    }

    @FXML
    private void eliminarTasaSeleccionada() {
        Double seleccionada = tasasListView.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("Selecciona una tasa de la lista para eliminar.", "#DC2626");
            return;
        }

        Cobro tipo = tipoCobroChoiceBox.getValue();
        if (tipo == Cobro.Nomina) {
            ConfiguracionTasas.eliminarTasaNomina(seleccionada);
        } else {
            ConfiguracionTasas.eliminarTasaDomiciliado(seleccionada);
        }

        cargarListaTasas(tipo);
        mostrarMensaje("Tasa eliminada correctamente.", "#00A859");
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

    @FXML
    private void regresarAlMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/main-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String texto, String colorHex) {
        mensajeLabel.setText(texto);
        mensajeLabel.setStyle("-fx-text-fill: " + colorHex + "; -fx-font-weight: bold;");
    }
}