package com.example.appdimex.DB;

import com.example.appdimex.Enums.Afiliacion;
import com.example.appdimex.model.Prospecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProspectoDao {

    public void registrar(Prospecto prospecto) {

        String sql = "INSERT INTO prospecto (nombre, apellidos, telefono, afiliado, banco, fechaNacimiento, direccion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conecction.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {


            ps.setString(1, prospecto.getNombre());
            ps.setString(2, prospecto.getApellidos());
            ps.setInt(3, prospecto.getTelefono());
            String afiliacion = prospecto.getAfiliado().name();
            System.out.println(" Afiliación a guardar: '" + afiliacion + "'");
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
    public List<Prospecto> consultar(String nombre, String apellidos) {
        List<Prospecto> resultados = new ArrayList<>();

        // ✅ Usando PreparedStatement (SEGURO contra inyección SQL)
        String sql = "SELECT * FROM prospecto WHERE nombre LIKE ? AND apellidos LIKE ?";

        try (Connection con = Conecction.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Configurar los parámetros con comodines para búsqueda parcial
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + apellidos + "%");

            System.out.println(" Buscando: Nombre='" + nombre + "', Apellidos='" + apellidos + "'");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prospecto prospecto = new Prospecto();
                //prospecto.setIdProspecto(rs.getInt("idprospecto"));
                prospecto.setNombre(rs.getString("nombre"));
                prospecto.setApellidos(rs.getString("apellidos"));
                prospecto.setTelefono(rs.getInt("telefono"));
                prospecto.setAfiliado(Afiliacion.valueOf(rs.getString("afiliado")));
                prospecto.setBanco(rs.getString("banco"));
                prospecto.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                prospecto.setDireccion(rs.getString("direccion"));

                resultados.add(prospecto);
            }

            System.out.println("Encontrados " + resultados.size() + " prospectos");

        } catch (SQLException e) {
            System.err.println("Error al consultar: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }
    public List<Prospecto> consultarTodos() {
        List<Prospecto> resultados = new ArrayList<>();
        String sql = "SELECT * FROM prospecto";

        try (Connection con = Conecction.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prospecto p = new Prospecto();
                //p.setIdProspecto(rs.getInt("idprospecto"));
                p.setNombre(rs.getString("nombre"));
                p.setApellidos(rs.getString("apellidos"));
                p.setTelefono(rs.getInt("telefono"));

                String afiliacionStr = rs.getString("afiliado");
                if (afiliacionStr != null) {
                    p.setAfiliado(Afiliacion.valueOf(afiliacionStr));
                }

                p.setBanco(rs.getString("banco"));
                p.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                p.setDireccion(rs.getString("direccion"));

                resultados.add(p);
            }

        } catch (SQLException e) {
            System.err.println(" Error al consultar todos: " + e.getMessage());
            e.printStackTrace();
        }

        return resultados;
    }
}