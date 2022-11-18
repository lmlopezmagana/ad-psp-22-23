package com.salesianostriana.dam;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SalidaString {

    public void salidaTexto() {
        try (PrintWriter pw = new PrintWriter(
                     new BufferedWriter(new FileWriter("texto.txt")))) {

            String quijoti = "En un lugar de La Mancha de cuyo nombre no quiero acordarme";

            String[] palabras = quijoti.split(" ");

            for (int i=0; i < palabras.length; i++) {
                pw.println(palabras[i]);
                //bw.write(palabras[i]);
                //bw.newLine();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



}
