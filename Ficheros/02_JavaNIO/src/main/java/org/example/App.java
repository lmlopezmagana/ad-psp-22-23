package org.example;

import java.io.IOException;
import java.nio.file.*;

public class App {
    
    public static void main(String[] args) throws IOException {

        /*
        Path p1 = Paths.get("temario", "java.txt");
        System.out.println(p1.toAbsolutePath().toString());

        Path p2 = FileSystems.getDefault().getPath("java", "javanio.txt");
        System.out.println(p2.toAbsolutePath().toString());

        Path p3 = Paths.get(System.getProperty("user.home"),"Documents", "java", "temario.txt");
        System.out.println(System.getProperty("user.home"));
        System.out.println(p3.toAbsolutePath().toString());
        */

        Path notas = Paths.get("notas.txt");
        if (Files.exists(notas)) {
            Path bakDir = Paths.get("backup");

            if (!Files.exists(bakDir)) {
                Files.createDirectories(bakDir);
            }

            Path bakFile = bakDir.resolve(Paths.get("notas.bak"));

            int oldVersion = 0;

            while (Files.exists(bakFile)) {
                String oldCompleteFilename = bakFile.getFileName().toString();
                String oldFilename = oldCompleteFilename.split("\\.")[0];
                if (oldFilename.contains("–")) {
                    oldVersion = Integer.parseInt(oldFilename.split("\\-")[1]);
                }
                String newFilename = "notas";
                newFilename += "-" + Integer.toString(++oldVersion) + ".bak";

                bakFile = bakDir.resolve(
                        Paths.get(newFilename)
                );
            }

            Files.copy(notas, bakFile, StandardCopyOption.REPLACE_EXISTING);

        }
        else
            System.out.println("Ánimo XDDDDD");


    }
    
}
