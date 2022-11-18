package com.salesianostriana.dam.shop.repositorio;

import com.salesianostriana.dam.shop.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends
        JpaRepository<Categoria, Long> {
}
