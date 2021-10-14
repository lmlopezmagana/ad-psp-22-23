package com.salesianostriana.dam.springdata.repos;

import com.salesianostriana.dam.springdata.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
