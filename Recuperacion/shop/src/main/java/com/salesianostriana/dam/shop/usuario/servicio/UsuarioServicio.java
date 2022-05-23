package com.salesianostriana.dam.shop.usuario.servicio;

import com.salesianostriana.dam.shop.servicios.base.ServicioBase;
import com.salesianostriana.dam.shop.usuario.modelo.Usuario;
import com.salesianostriana.dam.shop.usuario.repo.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicio extends
        ServicioBase<Usuario, Long, UsuarioRepositorio> {

    public Optional<Usuario> findByUsername(String username) {
        return repositorio.findFirstByUsername(username);
    }



}
