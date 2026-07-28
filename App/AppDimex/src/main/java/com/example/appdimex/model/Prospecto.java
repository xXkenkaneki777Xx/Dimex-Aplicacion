package com.example.appdimex.model;

public class Prospecto {

    private int idProspecto;
    private String nombre;
    private int pension;
    private String direccion;

    public Prospecto(int idProspecto, String nombre, int pension, String direccion) {
        this.idProspecto = idProspecto;
        this.nombre = nombre;
        this.pension = pension;
        this.direccion = direccion;
    }

    public Prospecto( String nombre, int pension, String direccion) {
        this.nombre = nombre;
        this.pension = pension;
        this.direccion = direccion;
    }

    public Prospecto() {

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

    public int getPension() {
        return pension;
    }

    public void setPension(int pension) {
        this.pension = pension;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
