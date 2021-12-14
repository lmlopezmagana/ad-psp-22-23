package com.salesianostriana.dam.servicios;

import com.salesianostriana.dam.dto.CreateUsuarioDtoV1;
import com.salesianostriana.dam.dto.CreateUsuarioDtoV2;
import com.salesianostriana.dam.modelo.Usuario;
import com.salesianostriana.dam.repositorios.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    public Usuario saveV1(CreateUsuarioDtoV1 createUsuarioDto) {
        return usuarioRepositorio.save(Usuario.builder()
                .login(createUsuarioDto.getLogin())
                .email(createUsuarioDto.getEmail())
                .password(createUsuarioDto.getPassword())
                .createdAt(LocalDateTime.now())
                .build());
    }

    public Usuario saveV2(CreateUsuarioDtoV2 createUsuarioDto) {
        return usuarioRepositorio.save(Usuario.builder()
                .login(createUsuarioDto.getLogin())
                .email(createUsuarioDto.getEmail())
                .password(createUsuarioDto.getPassword())
                .createdAt(LocalDateTime.now())
                .build());
    }


}
