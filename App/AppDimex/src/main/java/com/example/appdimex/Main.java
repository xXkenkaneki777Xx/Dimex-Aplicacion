package com.example.appdimex;

import com.example.appdimex.util.Estilos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/RegistroP.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/InicioSesion.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root, 340, 650);

            Estilos.aplicar(scene);


            primaryStage.setTitle("Cotización Express");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la aplicación: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
