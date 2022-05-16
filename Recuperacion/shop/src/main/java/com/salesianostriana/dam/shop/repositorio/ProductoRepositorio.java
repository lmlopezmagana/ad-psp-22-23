package com.salesianostriana.dam.shop.repositorio;

import com.salesianostriana.dam.shop.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepositorio
    extends JpaRepository<Producto, Long> {

    List<Producto> findByPrecioLessThan(double precio);

    List<Producto> findByNombreContainsIgnoreCase(String nombre);


}
