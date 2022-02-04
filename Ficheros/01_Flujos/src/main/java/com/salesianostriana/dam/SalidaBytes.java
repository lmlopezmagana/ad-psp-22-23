package com.salesianostriana.dam;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class SalidaBytes {

    public void escribir() {


        try(BufferedOutputStream bos =
                new BufferedOutputStream(
                        new FileOutputStream("numeros.dat"))) {


            for(int i = 0; i < 100; i++) {
                //bos.write(ThreadLocalRandom.current().nextInt());
                bos.write(65+i);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}
