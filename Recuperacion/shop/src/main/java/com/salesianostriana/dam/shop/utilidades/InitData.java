package com.salesianostriana.dam.shop.utilidades;

import com.salesianostriana.dam.shop.modelo.Producto;
import com.salesianostriana.dam.shop.repositorio.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final ProductoRepositorio repositorio;


    @PostConstruct
    public void init() {

        // Inventar dos productos y guardarlos
        // en la base de datos
        Producto p1 = Producto.builder()
                .nombre("4 Pilas alcalinas")
                .precio(1.0)
                .build();

        Producto p2 = Producto.builder()
                .nombre("3 Bolígrafos")
                .precio(1.25)
                .build();

        Producto p3 = Producto.builder()
                .nombre("Paquete 500 folios")
                .precio(8.0)
                .build();

        repositorio.save(p3);
        repositorio.save(p1);
        repositorio.save(p2);


    }


}
