package com.salesianostriana.dam.herencia.repos;

import com.salesianostriana.dam.herencia.model.Comida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComidaRepository extends JpaRepository<Comida, Long> {
}
