package com.salesianostriana.dam.controladores;


import com.salesianostriana.dam.dto.CreateUsuarioDtoV1;
import com.salesianostriana.dam.dto.CreateUsuarioDtoV2;
import com.salesianostriana.dam.modelo.Usuario;
import com.salesianostriana.dam.servicios.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UsuarioServicio servicio;

    @PostMapping("/v1")
    public ResponseEntity<Usuario> nuevoUsuario(@Valid @RequestBody CreateUsuarioDtoV1 newUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.saveV1(newUser));
    }

    @PostMapping("/v2")
    public ResponseEntity<Usuario> nuevoUsuario(@Valid @RequestBody CreateUsuarioDtoV2 newUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.saveV2(newUser));
    }



}
