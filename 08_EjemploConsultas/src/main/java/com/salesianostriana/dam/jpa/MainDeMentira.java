package com.salesianostriana.dam.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;

    @PostConstruct
    public void run() {

        Categoria c1 = Categoria.builder()
                .nombre("Electrónica")
                .build();

        categoriaRepositorio.save(c1);

        /*
        Categoria c2 = Categoria.builder()
                .nombre("Telefonía")
                //.categoriaPadre(c1)
                .build();

        c2.addToCategoriaPadre(c1);

        Categoria c3 = Categoria.builder()
                .nombre("IPhone")
                //.categoriaPadre(c2)
                .build();

        c3.addToCategoriaPadre(c2);

        Categoria c4 = Categoria.builder()
                .nombre("Xiaomi")
                //.categoriaPadre(c2)
                .build();

        c4.addToCategoriaPadre(c2);

        List<Categoria> listaCategorias = List.of(c1, c2, c3, c4);

        categoriaRepositorio.saveAll(listaCategorias);

        //c1.getCategoriasHijas()

        //printCategorias(c1);

        categoriaRepositorio.delete(c2);
        //categoriaRepositorio.flush();



        c1 = categoriaRepositorio.findById(1L).orElse(null);


        printCategorias(c1);
        */


        Producto p1 = Producto.builder()
                .nombre("Product 1")
                .categoria(c1)
                .pvp(1.0)
                .build();

        Producto p2 = Producto.builder()
                .nombre("Product 2")
                .categoria(c1)
                .pvp(2.0)
                .build();

        Producto p3 = Producto.builder()
                .nombre("Product 3")
                .categoria(c1)
                .pvp(3.0)
                .build();

        productoRepositorio.saveAll(List.of(p1, p2, p3));

        productoRepositorio.findAll();

        List<ProductoDto> dtos = productoRepositorio.findAllConDtoBy();

        dtos.forEach(System.out::println);

        productoRepositorio.findAllConProyeccionBy();



    }

    public void printCategorias(Categoria raiz) {
        String msg = (raiz.getCategoriaPadre() != null) ? "Categoria Padre: " + raiz.getCategoriaPadre().getNombre() + "; " : "";
        msg += "Categoria: " + raiz.getNombre();
        System.out.println(msg);
        if (raiz.getCategoriasHijas() != null && raiz.getCategoriasHijas().size() > 0)
            raiz.getCategoriasHijas().forEach(this::printCategorias);
    }

}
