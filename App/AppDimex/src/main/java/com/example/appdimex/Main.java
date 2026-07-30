package com.example.appdimex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/RegistroP.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/appdimex/main-view.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root, 340, 650);


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
