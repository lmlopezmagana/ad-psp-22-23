package com.salesianostriana.dam.fetch;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final CategoriaRepositorio categoriaRepositorio;

    private final CategoriaServicio categoriaServicio;

    @PostConstruct
    public void init() {


        //categoriaRepositorio.findTodoDeTodo().forEach(this::printCategoria);

        categoriaServicio.findTodoDeTodo().forEach(this::printCategoria);

    }

    public void printCategoria(Categoria c) {
        System.out.println("Categoria: " + c.getNombre());
        System.out.println("Productos");
        System.out.println("==========");
        c.getProductos().forEach(p -> {
            System.out.println("Producto: " +  p.getNombre());
            if (!p.getImagenes().isEmpty())
                /*System.out.println("Imágenes:" + p.getImagenes()
                        .stream()
                        .map(ImagenProducto::getUrl)
                        .collect(Collectors.joining(", "))
                );*/
                for(ImagenProducto im : p.getImagenes()) {
                    if (im != null)
                        System.out.println("Imagen:" + im.getUrl());
                }
        });
        System.out.println("\n");
    }

}
