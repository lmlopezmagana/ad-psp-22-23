package com.salesianostriana.dam.springdata.repos;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.model.AlumnoV2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlumnoV2Repository
        extends JpaRepository<AlumnoV2, UUID> {
}
