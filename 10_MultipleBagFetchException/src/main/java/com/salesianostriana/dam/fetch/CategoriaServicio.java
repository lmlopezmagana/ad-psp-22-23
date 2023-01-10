package com.salesianostriana.dam.fetch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;
    private final ProductoRepositorio productoRepositorio;

    @Transactional
    public List<Categoria> findTodoDeTodo() {

        List<Categoria> result =
                categoriaRepositorio.categoriaConProductos();

        if (!result.isEmpty())
            productoRepositorio.productosConImagenes();

        return result;

    }


}
