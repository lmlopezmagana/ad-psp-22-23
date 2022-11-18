package com.salesianostriana.dam;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EntradaBytes {

    private static final int EOF = -1;

    public void leer() {

        try(FileInputStream fis =
                new FileInputStream("numeros.dat")) {

            int c;
            while ((c = fis.read()) != EOF) {
                System.out.println((char)c);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }





    }


}
