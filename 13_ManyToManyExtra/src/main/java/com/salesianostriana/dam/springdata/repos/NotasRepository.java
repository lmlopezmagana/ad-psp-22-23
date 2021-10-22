package com.salesianostriana.dam.springdata.repos;

import com.salesianostriana.dam.springdata.model.Notas;
import com.salesianostriana.dam.springdata.model.NotasPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotasRepository extends JpaRepository<Notas, NotasPK> {
}
