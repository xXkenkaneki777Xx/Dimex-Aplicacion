package com.example.appdimex.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfiguracionTasas {

    private static final List<Double> tasasNomina = new ArrayList<>(Arrays.asList(28.6, 31.8));
    private static final List<Double> tasasDomiciliado = new ArrayList<>(Arrays.asList(85.0, 90.0, 95.0, 100.0, 105.0));

    public static List<Double> getTasasNomina() {
        return new ArrayList<>(tasasNomina);
    }

    public static void agregarTasaNomina(double tasa) {
        if (!tasasNomina.contains(tasa)) {
            tasasNomina.add(tasa);
        }
    }

    public static void eliminarTasaNomina(Double tasa) {
        tasasNomina.remove(tasa);
    }

    public static List<Double> getTasasDomiciliado() {
        return new ArrayList<>(tasasDomiciliado);
    }

    public static void agregarTasaDomiciliado(double tasa) {
        if (!tasasDomiciliado.contains(tasa)) {
            tasasDomiciliado.add(tasa);
        }
    }

    public static void eliminarTasaDomiciliado(Double tasa) {
        tasasDomiciliado.remove(tasa);
    }
}