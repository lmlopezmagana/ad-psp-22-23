package com.salesianostriana.dam.springdata.repos;

import com.salesianostriana.dam.springdata.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository
        extends JpaRepository<Asignatura, Long> {
}
