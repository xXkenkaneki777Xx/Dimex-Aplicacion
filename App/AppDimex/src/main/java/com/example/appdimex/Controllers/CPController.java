package com.example.appdimex.Controllers;

import com.example.appdimex.DB.ProspectoDao;
import com.example.appdimex.Enums.Afiliacion;
import com.example.appdimex.model.Prospecto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Date;
import java.util.List;

public class CPController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField apellidosTextField;

    @FXML
    private Button buscarButton;

    @FXML
    private Button todosButton;

    @FXML
    private Button limpiarButton;

    @FXML
    private TableView<Prospecto> tablaProspectos;


    @FXML
    private TableColumn<Prospecto, String> colNombre;

    @FXML
    private TableColumn<Prospecto, String> colApellidos;

    @FXML
    private TableColumn<Prospecto, Integer> colTelefono;

    @FXML
    private TableColumn<Prospecto, String> colAfiliacion;

    @FXML
    private TableColumn<Prospecto, String> colBanco;

    @FXML
    private TableColumn<Prospecto, Date> colFechaNac;

    @FXML
    private TableColumn<Prospecto, String> colDireccion;

    @FXML
    private Label estadoLabel;

    @FXML
    private Label contadorLabel;

    private ObservableList<Prospecto> listaProspectos = FXCollections.observableArrayList();
    private ProspectoDao prospectoDao = new ProspectoDao();

    @FXML
    public void initialize() {
        cargarLogo();
        configurarColumnas();
        configurarBotones();
        // Cargar todos los prospectos al iniciar
        cargarTodos();
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

    private void configurarColumnas() {
        // ✅ Usar Callback en lugar de PropertyValueFactory

        colNombre.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre())
        );

        colApellidos.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellidos())
        );

        colTelefono.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getTelefono()).asObject()
        );

        colAfiliacion.setCellValueFactory(cellData -> {
            String afiliacion = cellData.getValue().getAfiliado() != null ?
                    cellData.getValue().getAfiliado().name() : "";
            return new javafx.beans.property.SimpleStringProperty(afiliacion);
        });

        colBanco.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBanco())
        );

        colFechaNac.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFechaNacimiento())
        );

        colDireccion.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDireccion())
        );

        tablaProspectos.setItems(listaProspectos);
        tablaProspectos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    private void configurarBotones() {
        buscarButton.setOnAction(event -> buscarProspectos());
        todosButton.setOnAction(event -> cargarTodos());
        limpiarButton.setOnAction(event -> limpiarCampos());
    }

    private void buscarProspectos() {
        String nombre = nombreTextField.getText().trim();
        String apellidos = apellidosTextField.getText().trim();

        if (nombre.isEmpty() && apellidos.isEmpty()) {
            mostrarAlerta("Búsqueda", "Ingresa al menos un criterio de búsqueda");
            return;
        }

        estadoLabel.setText("🔍 Buscando prospectos...");

        List<Prospecto> resultados = prospectoDao.consultar(nombre, apellidos);
        actualizarTabla(resultados);

        if (resultados.isEmpty()) {
            estadoLabel.setText(" No se encontraron prospectos con esos criterios");
        } else {
            estadoLabel.setText(" Búsqueda completada");
        }
    }

    private void cargarTodos() {
        estadoLabel.setText(" Cargando todos los prospectos...");

        List<Prospecto> todos = prospectoDao.consultarTodos();
        actualizarTabla(todos);

        if (todos.isEmpty()) {
            estadoLabel.setText(" No hay prospectos registrados");
        } else {
            estadoLabel.setText(" Mostrando todos los prospectos");
        }
    }

    private void actualizarTabla(List<Prospecto> prospectos) {
        listaProspectos.clear();
        listaProspectos.addAll(prospectos);
        contadorLabel.setText(prospectos.size() + " registros encontrados");
    }

    private void limpiarCampos() {
        nombreTextField.clear();
        apellidosTextField.clear();
        estadoLabel.setText(" Campos limpiados. Listo para buscar");
        // Opcional: recargar todos los datos
        cargarTodos();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    private Afiliacion convertirAfiliacion(String valor) {
        if (valor == null) return null;
        try {
            return Afiliacion.valueOf(valor);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
