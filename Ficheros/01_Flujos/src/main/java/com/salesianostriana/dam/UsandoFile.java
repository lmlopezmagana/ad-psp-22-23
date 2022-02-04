package com.salesianostriana.dam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UsandoFile {

    public void escribir() {

        File f = new File("file.txt");

        try(PrintWriter pw = new PrintWriter(
                new FileWriter(f)
        )) {
            pw.println("Ola k ase, mercadona o k ase");
            pw.flush();
            //pw.close();
            System.out.println(f.length());

        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }


}
