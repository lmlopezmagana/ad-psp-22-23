package com.salesianostriana.dam.shop.repositorio;

import com.salesianostriana.dam.shop.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio
    extends JpaRepository<Producto, Long> {
}
