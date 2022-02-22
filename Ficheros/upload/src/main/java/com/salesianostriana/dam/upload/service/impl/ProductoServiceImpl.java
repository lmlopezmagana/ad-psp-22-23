package com.salesianostriana.dam.upload.service.impl;

import com.salesianostriana.dam.upload.dto.CreateProductoDto;
import com.salesianostriana.dam.upload.image.ImageScaler;
import com.salesianostriana.dam.upload.model.CreateProductException;
import com.salesianostriana.dam.upload.model.Producto;
import com.salesianostriana.dam.upload.model.ProductoRepository;
import com.salesianostriana.dam.upload.service.ProductoService;
import com.salesianostriana.dam.upload.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {


    private static final String THUMBNAIL_PREFIX = "thumb_";

    private final ProductoRepository repository;
    private final StorageService storageService;
    private final ImageScaler imageScaler;

    @Value("${image.thumbnail.width:256}")
    private int thumbnailWidth;




    @Override
    public Producto save(CreateProductoDto createProductoDto, MultipartFile file) {

        try {

            // Guardamos la imagen normal
            String filename = storageService.store(file);

            // Escalamos la imagen
            byte[] scaled = imageScaler.scale(file.getBytes(), thumbnailWidth);

            // Almacenamos la imagen escalada
            String scaledFilename = storageService.store(scaled, THUMBNAIL_PREFIX + filename, file.getContentType());


            return repository.save(Producto.builder()
                    .nombre(createProductoDto.getNombre())
                    .pvp(createProductoDto.getPvp())
                    .imagen(completeUri(filename))
                    .thumbnail(completeUri(scaledFilename))
                    .build());

        } catch (IOException ex) {
            throw new CreateProductException("Error al crear un producto", ex);
        }


    }

    @Override
    public List<Producto> findAll() {
        return repository.findAll();
    }


    private String completeUri(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
    }



}

