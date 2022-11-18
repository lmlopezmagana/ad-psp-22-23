package com.salesianostriana.dam.upload.service.impl;

import com.salesianostriana.dam.upload.config.StorageProperties;
import com.salesianostriana.dam.upload.exception.FileNotFoundException;
import com.salesianostriana.dam.upload.exception.StorageException;
import com.salesianostriana.dam.upload.service.StorageService;
import com.salesianostriana.dam.upload.utils.MediaTypeUrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }


    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//        String newFilename = "";
//        try {
//            // Si el fichero está vacío, excepción al canto
//            if (file.isEmpty())
//                throw new StorageException("El fichero subido está vacío");
//
//            /*newFilename = filename;
//            while(Files.exists(rootLocation.resolve(newFilename))) {
//                // Tratamos de generar uno nuevo
//                String extension = StringUtils.getFilenameExtension(newFilename);
//                String name = newFilename.replace("."+extension,"");
//
//                String suffix = Long.toString(System.currentTimeMillis());
//                suffix = suffix.substring(suffix.length()-6);
//
//                newFilename = name + "_" + suffix + "." + extension;
//
//            }*/
//
//            newFilename = calculateNewFilename(filename);
//
//            try (InputStream inputStream = file.getInputStream()) {
//                Files.copy(inputStream, rootLocation.resolve(newFilename),
//                        StandardCopyOption.REPLACE_EXISTING);
//            }
//
//
//
//        } catch (IOException ex) {
//            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, ex);
//        }
//
//        return newFilename;
//

        try {
            return store(file.getBytes(), file.getOriginalFilename(), file.getContentType());
        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + file.getOriginalFilename(), ex);
        }

    }

    @Override
    public String store(byte[] file, String filename, String contentType) {

        String newFilename = StringUtils.cleanPath(filename);

        if (file.length == 0)
            throw new StorageException("El fichero subido está vacío");

        newFilename = calculateNewFilename(newFilename);

        try (InputStream inputStream = new ByteArrayInputStream(file)) {
            //Files.write(rootLocation.resolve(newFilename), file);

            Files.copy(inputStream, rootLocation.resolve(newFilename),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch(IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, ex);

        }

        return newFilename;
    }

    private String calculateNewFilename(String filename) {
        String newFilename = filename;
        while(Files.exists(rootLocation.resolve(newFilename))) {
            // Tratamos de generar uno nuevo
            String extension = StringUtils.getFilenameExtension(newFilename);
            String name = newFilename.replace("."+extension,"");

            String suffix = Long.toString(System.currentTimeMillis());
            suffix = suffix.substring(suffix.length()-6);

            newFilename = name + "_" + suffix + "." + extension;

        }

        return newFilename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Error al leer los ficheros almacenados", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {

        try {
            Path file = load(filename);
            MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteFile(String filename) {
        // Pendiente
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
