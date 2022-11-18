package com.salesianostriana.dam.springdata.repos;

import com.salesianostriana.dam.springdata.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
