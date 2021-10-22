package com.salesianostriana.dam.springdata.repos;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlumnoRepository
        extends JpaRepository<Alumno, Long> {

    List<Alumno> findByCurso(Curso curso);
}
