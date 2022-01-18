package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
