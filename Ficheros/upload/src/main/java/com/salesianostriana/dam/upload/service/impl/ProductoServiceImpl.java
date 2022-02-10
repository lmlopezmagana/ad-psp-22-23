package com.salesianostriana.dam.upload.service.impl;

import com.salesianostriana.dam.upload.dto.CreateProductoDto;
import com.salesianostriana.dam.upload.model.Producto;
import com.salesianostriana.dam.upload.model.ProductoRepository;
import com.salesianostriana.dam.upload.service.ProductoService;
import com.salesianostriana.dam.upload.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;
    private final StorageService storageService;

    @Override
    public Producto save(CreateProductoDto createProductoDto, MultipartFile file) {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return repository.save(Producto.builder()
                        .nombre(createProductoDto.getNombre())
                        .pvp(createProductoDto.getPvp())
                        .imagen(uri)
                .build());


    }

    @Override
    public List<Producto> findAll() {
        return repository.findAll();
    }


}
