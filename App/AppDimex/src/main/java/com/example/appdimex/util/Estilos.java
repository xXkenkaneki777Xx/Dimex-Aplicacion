package com.example.appdimex.util;

import javafx.scene.Scene;
import java.util.Objects;

public class Estilos {

    public static void aplicar(Scene scene) {
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        Estilos.class.getResource("/styles/estilos.css"),
                        "No se encontró estilos.css"
                ).toExternalForm()
        );
    }
}