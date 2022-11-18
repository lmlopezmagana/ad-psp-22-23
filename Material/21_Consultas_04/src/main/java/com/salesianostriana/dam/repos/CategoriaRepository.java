package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
