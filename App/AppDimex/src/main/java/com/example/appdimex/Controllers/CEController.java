package com.example.appdimex.Controllers;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.swing.JOptionPane.*;
import static java.lang.Integer.parseInt;
import static java.lang.Double.parseDouble;

public class CEController {
    public static void main(String[] args) {
        int opcion = parseInt(showInputDialog("1: Domiciliado, 2: Nomina"));
        Double tasa = 0.0;
        List<Integer> domiciliado = Arrays.asList(85, 90, 95, 100, 105);
        List<Double> nomina = Arrays.asList(28.6, 33.5);


        if (opcion == 1) {
            int opcionTaza = parseInt(showInputDialog("1: 85%, 2: 90%, 3: 95%, 4: 100%, 5: 105%"));
            if (opcionTaza >= 1 && opcionTaza <= domiciliado.size()) {
                tasa = 1.0 + (domiciliado.get(opcionTaza - 1) / 100.0);
                System.out.println("Tasa calculada: " + tasa);
            } else {
                System.out.println("Opción inválida.");
            }
        } else if (opcion == 2) {
            int opcionTaza = parseInt(showInputDialog("1: 28.6%, 2: 33.5%"));
            if (opcionTaza >= 1 && opcionTaza <= nomina.size()) {
                tasa = 1.0 + (nomina.get(opcionTaza - 1) / 100.0);
                System.out.println("Tasa calculada: " + tasa);
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }

}

