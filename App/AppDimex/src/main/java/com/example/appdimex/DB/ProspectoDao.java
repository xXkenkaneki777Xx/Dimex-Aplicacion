package com.example.appdimex.DB;

import com.example.appdimex.model.Prospecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ProspectoDao {

    public void registrar(Prospecto prospecto) {

        String sql = "INSERT INTO prospecto (nombre, apellidos, telefono, afiliado, banco, fechaNacimiento, direccion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conecction.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {


            ps.setString(1, prospecto.getNombre());
            ps.setString(2, prospecto.getApellidos());
            ps.setInt(3, prospecto.getTelefono());
            String afiliacion = prospecto.getAfiliado().name();
            System.out.println("📝 Afiliación a guardar: '" + afiliacion + "'");
            ps.setString(4, afiliacion);
            ps.setString(5, prospecto.getBanco());
            ps.setDate(6, prospecto.getFechaNacimiento());
            ps.setString(7, prospecto.getDireccion());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Prospecto guardado exitosamente!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Error: Prospecto duplicado - " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general al insertar en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void consultar(Prospecto prospecto){
        String nombre = prospecto.getNombre();
        String apellidos = prospecto.getApellidos();
        String sql = "SELECT * FROM prospecto WHERE nombre = '"+nombre+"' AND apellidos = '"+apellidos+"' ;";

    }
}