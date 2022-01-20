package com.salesianostriana.dam.repositorios;


import com.salesianostriana.dam.modelo.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final ProductoRepositorio repositorio;

    @PostConstruct
    public void init() {

        repositorio.save(Producto.builder()
                .nombre("Un producto")
                .build());

    }


}
