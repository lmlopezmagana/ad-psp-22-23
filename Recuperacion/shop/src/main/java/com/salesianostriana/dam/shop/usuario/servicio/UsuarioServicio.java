package com.salesianostriana.dam.shop.usuario.servicio;

import com.salesianostriana.dam.shop.servicios.base.ServicioBase;
import com.salesianostriana.dam.shop.usuario.controlador.dto.NewUserRequest;
import com.salesianostriana.dam.shop.usuario.modelo.Usuario;
import com.salesianostriana.dam.shop.usuario.repo.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServicio extends
        ServicioBase<Usuario, Long, UsuarioRepositorio> {

    private PasswordEncoder passwordEncoder;


    public Optional<Usuario> findByUsername(String username) {
        return repositorio.findFirstByUsername(username);
    }


    public Usuario nuevoUsuario(NewUserRequest request) {

        return save(Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }


}
