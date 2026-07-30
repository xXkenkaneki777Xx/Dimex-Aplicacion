package com.example.appdimex.DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conecction {
    private static final String URL = "jdbc:mysql://localhost:3306/dbdimex";
    private static final String USER = "root";
    private static final String PASSWORD = "8787";
//    private static final String PASSWORD = "Leviat@n45";

    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión a la base de datos realizada con éxito!");
            return conexion;
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }
}