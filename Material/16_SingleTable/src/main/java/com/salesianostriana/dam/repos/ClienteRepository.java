package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Admin;
import com.salesianostriana.dam.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends BaseUsuarioRepository<Cliente>  {
}
