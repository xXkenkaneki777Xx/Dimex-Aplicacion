package com.example.appdimex.Controllers;

import com.example.appdimex.Enums.Cobro;
import com.example.appdimex.Enums.TipoTransaccion;
import com.example.appdimex.model.PlanPago;
import com.example.appdimex.util.ConfiguracionTasas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.DecimalFormat;

public class CotizacionController {

    @FXML
    private ChoiceBox<Cobro> tipoCobroChoiceBox;
    @FXML
    private ChoiceBox<Double> tazaInteresChoiceBox;
    @FXML
    private ChoiceBox<TipoTransaccion> formaPagoChoiceBox;
    @FXML
    private TextField cantidadTextField;
    @FXML
    private Button cotizarButton;
    @FXML
    private Button borrarButton;
    @FXML
    private ImageView logoImageView;

    @FXML
    private TableView<PlanPago> cotizacionTableView;
    @FXML
    private TableColumn<PlanPago, Integer> columnaMeses;
    @FXML
    private TableColumn<PlanPago, Double> columnaPagoMensual;

    private final int[] MESES_FIJOS = {12, 18, 24, 30, 36, 48};

    @FXML
    public void initialize() {
        cargarLogo();

        // 1. Llenar el ChoiceBox con los valores del Enum
        tipoCobroChoiceBox.getItems().clear();
        tipoCobroChoiceBox.getItems().addAll(Cobro.values()); // O TipoCobro.NOMINA, TipoCobro.DOMICILIADO

        // 2. Escuchar el cambio recibiendo el objeto Enum en lugar de un String
        tipoCobroChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarTasasInteres(newVal);
            }
        });

        tazaInteresChoiceBox.setConverter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return object == null ? "" : object + "%";
            }

            @Override
            public Double fromString(String string) {
                return null; // No es necesario implementar esto para un ChoiceBox
            }
        });
        configurarChoiceBox();
        configurarTabla();
        configurarBotones();
        cargarLogo();
    }

    private void actualizarTasasInteres(Cobro tipoCobro) {
        if (tipoCobro == null) return;

        tazaInteresChoiceBox.getItems().clear();

        if (tipoCobro == Cobro.Domiciliado) {
            tazaInteresChoiceBox.getItems().addAll(ConfiguracionTasas.getTasasDomiciliado());
        } else if (tipoCobro == Cobro.Nomina) {
            tazaInteresChoiceBox.getItems().addAll(ConfiguracionTasas.getTasasNomina());
        }

        if (!tazaInteresChoiceBox.getItems().isEmpty()) {
            tazaInteresChoiceBox.setValue(tazaInteresChoiceBox.getItems().get(0));
        }
    }

    private void configurarChoiceBox() {
        tazaInteresChoiceBox.setConverter(new StringConverter<Double>() {
            private final DecimalFormat df = new DecimalFormat("0.##'%'");

            @Override
            public String toString(Double valor) {
                return (valor == null) ? "" : df.format(valor);
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });

        tipoCobroChoiceBox.getItems().setAll(Cobro.values());
        formaPagoChoiceBox.getItems().setAll(TipoTransaccion.values());
        formaPagoChoiceBox.setValue(TipoTransaccion.Monto);

        tipoCobroChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, viejoValor, nuevoValor) -> {
                    actualizarTasasInteres(nuevoValor);
                });

        tipoCobroChoiceBox.setValue(Cobro.Domiciliado);
        actualizarTasasInteres(tipoCobroChoiceBox.getValue());
    }

    private void configurarBotones() {
        cotizarButton.setOnAction(event -> calcular());
        borrarButton.setOnAction(event -> limpiarCampos());
    }

    private void limpiarCampos() {
        cantidadTextField.clear();
    }



    private void configurarTabla() {
        columnaMeses.setCellValueFactory(new PropertyValueFactory<>("meses"));
        columnaPagoMensual.setCellValueFactory(new PropertyValueFactory<>("pagoMensual"));

        columnaMeses.setCellFactory(tc -> new TableCell<PlanPago, Integer>() {
            @Override
            protected void updateItem(Integer meses, boolean empty) {
                super.updateItem(meses, empty);
                setText((empty || meses == null) ? null : meses + " meses");
            }
        });

        DecimalFormat formatoMoneda = new DecimalFormat("$#,##0.00");
        columnaPagoMensual.setCellFactory(tc -> new TableCell<PlanPago, Double>() {
            @Override
            protected void updateItem(Double valor, boolean empty) {
                super.updateItem(valor, empty);
                setText((empty || valor == null) ? null : formatoMoneda.format(valor));
            }
        });
    }

    @FXML
    private void calcular() {
        try {
            String cantidadTexto = cantidadTextField.getText().trim();
            if (cantidadTexto.isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor ingresa una cantidad.");
                return;
            }

            double cantidad = Double.parseDouble(cantidadTexto);
            Double tasa = tazaInteresChoiceBox.getValue();

            if (tasa == null) {
                mostrarAlerta("Atención", "Selecciona una tasa de interés.");
                return;
            }

            TipoTransaccion formaPago = formaPagoChoiceBox.getValue();
            if (formaPago == null) {
                mostrarAlerta("Atención", "Selecciona una forma de pago.");
                return;
            }

            double factorTasa = 1.0 + (tasa / 100.0);
            ObservableList<PlanPago> listaPlan = FXCollections.observableArrayList();

            if (formaPago == TipoTransaccion.Monto) {
                double totalPagar = cantidad * factorTasa;

                for (int meses : MESES_FIJOS) {
                    double pagoMensual = totalPagar / meses;
                    listaPlan.add(new PlanPago(meses, pagoMensual));
                }

            } else if (formaPago == TipoTransaccion.Pago) {
                double basePago = cantidad * factorTasa;

                for (int meses : MESES_FIJOS) {
                    double resultadoMultiplicacion = basePago * meses;
                    listaPlan.add(new PlanPago(meses, resultadoMultiplicacion));
                }
            }

            cotizacionTableView.setItems(listaPlan);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "Ingresa una cantidad numérica válida.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error inesperado: " + e.getMessage());
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

    @FXML
    private void regresarAlMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/main-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo regresar a la pantalla principal: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}