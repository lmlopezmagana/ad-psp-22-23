package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}
