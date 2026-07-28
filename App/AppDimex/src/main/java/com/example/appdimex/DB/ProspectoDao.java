package com.example.appdimex.DB;

import com.example.appdimex.model.Prospecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class ProspectoDao {

    public void registrar(Prospecto prospecto) {
        String sql = "INSERT INTO prospecto (nombre, pension, direccion) VALUES (?, ?, ?)";

        try (Connection con = Conecction.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prospecto.getNombre());
            ps.setInt(2, prospecto.getPension());
            ps.setString(3, prospecto.getDireccion());
//            ps.executeUpdate();

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("¡Prospecto guardado exitosamente!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("El prospecto duplicado");
        } catch (Exception e) {
            System.err.println("Error general al insertar en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
