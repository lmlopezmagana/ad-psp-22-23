package com.salesianostriana.dam.servicios;

import com.salesianostriana.dam.errores.excepciones.ListEntityNotFoundException;
import com.salesianostriana.dam.errores.excepciones.SingleEntityNotFoundException;
import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.repositorios.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


// En este caso no vamos a hacer que extienda a BaseService, pero no hay inconveniente en que lo haga
@Service
@RequiredArgsConstructor
public class ProductoServicio {

    private final ProductoRepositorio repositorio;


    public List<Producto> findAll() {
        List<Producto> result = repositorio.findAll();

        if (result.isEmpty()) {
            throw new ListEntityNotFoundException(Producto.class);
        } else {
            return result;
        }
    }


    public Producto findById(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new SingleEntityNotFoundException(id.toString(), Producto.class));
    }


    public Producto save(Producto producto) {
        return repositorio.save(producto);
    }

}
