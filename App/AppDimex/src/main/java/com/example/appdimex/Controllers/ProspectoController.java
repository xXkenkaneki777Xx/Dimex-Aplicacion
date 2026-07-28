package com.example.appdimex.Controllers;

import com.example.appdimex.DB.ProspectoDao;
import com.example.appdimex.model.Prospecto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProspectoController {

    @FXML
    private void registrarProducto() {
        // tu código aquí
    }
    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize() {
//        System.out.println("Inicializando ventana y registrando prospecto de prueba...");
//        Prospecto nuevoProspecto = new Prospecto("Carlos Mendoza", 12500, "Av. Hidalgo 456, Col. Centro");
//        ProspectoDao prospectoDao = new ProspectoDao();
//        prospectoDao.registrar(nuevoProspecto);
    }
}
