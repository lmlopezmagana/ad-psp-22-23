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
        categoriaRepositorio.flush();

        Categoria c11 = categoriaRepositorio.findById(1L).orElse(null);


        printCategorias(c11);



    }

    public void printCategorias(Categoria raiz) {
        String msg = (raiz.getCategoriaPadre() != null) ? "Categoria Padre: " + raiz.getCategoriaPadre().getNombre() + "; " : "";
        msg += "Categoria: " + raiz.getNombre();
        System.out.println(msg);
        if (raiz.getCategoriasHijas() != null && raiz.getCategoriasHijas().size() > 0)
            raiz.getCategoriasHijas().forEach(this::printCategorias);
    }

}
