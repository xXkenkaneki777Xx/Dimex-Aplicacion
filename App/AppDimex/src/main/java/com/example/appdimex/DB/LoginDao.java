package com.example.appdimex.DB;

import com.example.appdimex.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

    public Usuario autenticar(String usuario, String contrasena) {

        Usuario usuarioEncontrado = verificarAdministrador(usuario, contrasena);


        if (usuarioEncontrado == null) {
            usuarioEncontrado = verificarPromotor(usuario, contrasena);
        }

        return usuarioEncontrado;
    }

    private Usuario verificarAdministrador(String usuario, String contrasena) {
        String sql = "SELECT idadministrador, usuario, contrasena, nombre, apellidos FROM administrador WHERE usuario = ? AND contrasena = ?";

        try (Connection con = Conecction.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuarioAutenticado = new Usuario();
                usuarioAutenticado.setId(rs.getInt("idadministrador"));
                usuarioAutenticado.setUsuario(rs.getString("usuario"));
                usuarioAutenticado.setContrasena(rs.getString("contrasena"));
                usuarioAutenticado.setNombre(rs.getString("nombre"));
                usuarioAutenticado.setRol("ADMIN");
                usuarioAutenticado.setIdPersona(rs.getInt("idadministrador"));

                System.out.println("Administrador autenticado: " + usuario);
                return usuarioAutenticado;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar administrador: " + e.getMessage());
        }

        return null;
    }


    private Usuario verificarPromotor(String usuario, String contrasena) {
        String sql = "SELECT idpromotor, nombre, apellidos, correo, usuario, contrasena, telefono FROM promotor WHERE usuario = ? AND contrasena = ?";

        try (Connection con = Conecction.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuarioAutenticado = new Usuario();
                usuarioAutenticado.setId(rs.getInt("idpromotor"));
                usuarioAutenticado.setUsuario(rs.getString("usuario"));
                usuarioAutenticado.setContrasena(rs.getString("contrasena"));
                usuarioAutenticado.setNombre(rs.getString("nombre"));
                usuarioAutenticado.setRol("PROMOTOR");
                usuarioAutenticado.setIdPersona(rs.getInt("idpromotor"));

                System.out.println("Promotor autenticado: " + usuario);
                return usuarioAutenticado;
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar promotor: " + e.getMessage());
        }

        return null;
    }


}
