package com.salesianostriana.dam.repositorios;


import com.salesianostriana.dam.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

    boolean existsByNombre(String nombre);

}
