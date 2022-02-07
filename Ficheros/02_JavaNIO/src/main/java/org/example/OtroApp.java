package org.example;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class OtroApp {

    public static void main(String[] args) throws IOException {

        byte[] byteImg = Files.readAllBytes(Paths.get("triana.jpeg"));

        BufferedImage original = ImageIO.read(
                new ByteArrayInputStream(byteImg)
        );


        BufferedImage scaled = Scalr.resize(original, 512);


        OutputStream out = Files.newOutputStream(Paths.get("triana-thumb.jpeg"));

        ImageIO.write(scaled, "jpg", out);

    }

}
