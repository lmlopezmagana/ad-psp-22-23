package com.salesianostriana.dam.shop.utilidades;

import com.salesianostriana.dam.shop.modelo.Categoria;
import com.salesianostriana.dam.shop.modelo.Producto;
import com.salesianostriana.dam.shop.repositorio.CategoriaRepositorio;
import com.salesianostriana.dam.shop.repositorio.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;


    @PostConstruct
    public void init() {

        Categoria c1 = Categoria.builder()
                .nombre("Papelería")
                .build();

        Categoria c2 = Categoria.builder()
                .nombre("Bazar")
                .build();

        categoriaRepositorio.save(c1);
        categoriaRepositorio.save(c2);

        // Inventar dos productos y guardarlos
        // en la base de datos
        Producto p1 = Producto.builder()
                .nombre("4 Pilas alcalinas")
                .precio(1.0)
                .categoria(c2)
                .build();

        Producto p2 = Producto.builder()
                .nombre("3 Bolígrafos")
                .precio(1.25)
                .categoria(c1)
                .build();

        Producto p3 = Producto.builder()
                .nombre("Paquete 500 folios")
                .precio(8.0)
                .categoria(c1)
                .build();

        productoRepositorio.save(p3);
        productoRepositorio.save(p1);
        productoRepositorio.save(p2);


    }


}
