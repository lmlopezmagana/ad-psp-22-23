package com.salesianostriana.dam.springdata.repos;

import com.salesianostriana.dam.springdata.model.Monumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MonumentoRepository extends JpaRepository<Monumento, UUID> {
}
