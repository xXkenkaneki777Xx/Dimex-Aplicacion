package com.example.appdimex.Views;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AyudaView {

        public void mostrar(String titulo, String contenido) {
            Stage stage = new Stage();

            // Crear el texto
            Text texto = new Text(contenido);
            texto.setStyle("-fx-font-size: 14px; -fx-font-family: Arial;");

            // Contenedor
            VBox vbox = new VBox(10);
            vbox.setStyle("-fx-padding: 20px; -fx-background-color: #f8f9fa;");
            vbox.getChildren().add(texto);

            // Escena
            Scene scene = new Scene(vbox, 500, 300);

            // Configurar ventana
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        }

}
