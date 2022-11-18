package com.salesianostriana.dam.upload.service;

import com.salesianostriana.dam.upload.dto.CreateProductoDto;
import com.salesianostriana.dam.upload.model.Producto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductoService {
    Producto save(CreateProductoDto createProductoDto, MultipartFile file);
    List<Producto> findAll();
}
