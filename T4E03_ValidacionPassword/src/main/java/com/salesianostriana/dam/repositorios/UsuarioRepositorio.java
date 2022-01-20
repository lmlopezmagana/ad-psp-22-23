package com.salesianostriana.dam.repositorios;

import com.salesianostriana.dam.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
