package com.example.appdimex.model;

public class PlanPago {
    private int meses;
    private double pagoMensual;

    public PlanPago(int meses, double pagoMensual) {
        this.meses = meses;
        this.pagoMensual = pagoMensual;
    }

    public int getMeses() { return meses; }
    public double getPagoMensual() { return pagoMensual; }
}
