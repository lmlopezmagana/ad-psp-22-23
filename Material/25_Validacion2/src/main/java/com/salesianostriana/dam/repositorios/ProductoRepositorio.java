package com.salesianostriana.dam.repositorios;


import com.salesianostriana.dam.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}
