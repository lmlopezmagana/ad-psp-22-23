package com.salesianostriana.dam.upload.image;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.*;

@Service
public class ScalrImageScaler implements ImageScaler {

    public final static String DEFAULT_TYPE = "jpg";

    @Override
    public byte[] scale(byte[] image, int width, String type) {

        String outputType = (type.toLowerCase().equalsIgnoreCase("jpg") ||
                type.toLowerCase().equalsIgnoreCase("png") ||
                type.toLowerCase().equalsIgnoreCase("gif")) ? type : DEFAULT_TYPE;


        try (InputStream inputStream = new ByteArrayInputStream(image);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            BufferedImage original = ImageIO.read(inputStream);
            BufferedImage scaled = scale(original, width);
            ImageIO.write(scaled, outputType, outputStream);
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw new ImageProcessException("Error al escalar la imagen", ex);
        }

    }

    @Override
    public byte[] scale(byte[] image, int width) {
        return scale(image, width, DEFAULT_TYPE);
    }

    @Override
    public OutputStream scale(InputStream inputStream, int width) {
        return null;
    }

    @Override
    public BufferedImage scale(BufferedImage image, int width) {
        try {
            return Scalr.resize(image, width);
        } catch (IllegalArgumentException ex) {
            throw new ImageProcessException("Error al escalar la imagen", ex);
        } catch (ImagingOpException ex) {
            throw new ImageProcessException("Error al escalar la imagen", ex);
        }
    }
}
