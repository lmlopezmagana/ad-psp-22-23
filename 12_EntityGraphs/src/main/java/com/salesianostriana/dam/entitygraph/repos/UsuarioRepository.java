package com.salesianostriana.dam.entitygraph.repos;

import com.salesianostriana.dam.entitygraph.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
