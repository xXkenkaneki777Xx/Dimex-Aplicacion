package com.example.appdimex.model;

import com.example.appdimex.Enums.Afiliacion;

import java.sql.Date;

public class Prospecto {

    private int idProspecto;
    private String nombre;
    private String apellidos;
    private int telefono;
    private Afiliacion afiliado;
    private String banco;
    private Date fechaNacimiento;
    private String direccion;

    public Prospecto( String nombre, String apellidos, int telefono, Afiliacion afiliado, String banco, Date fechaNacimiento, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.afiliado = afiliado;
        this.banco = banco;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
    }
    public Prospecto(){

    }

    public int getIdProspecto() {
        return idProspecto;
    }

    public void setIdProspecto(int idProspecto) {
        this.idProspecto = idProspecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Afiliacion getAfiliado() {
        return afiliado;
    }

    public void setAfiliado(Afiliacion afiliado) {
        this.afiliado = afiliado;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
