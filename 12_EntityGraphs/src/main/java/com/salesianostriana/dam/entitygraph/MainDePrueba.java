package com.salesianostriana.dam.entitygraph;

import com.salesianostriana.dam.entitygraph.model.Ciudad;
import com.salesianostriana.dam.entitygraph.model.Direccion;
import com.salesianostriana.dam.entitygraph.model.Grupo;
import com.salesianostriana.dam.entitygraph.model.Usuario;
import com.salesianostriana.dam.entitygraph.service.CiudadService;
import com.salesianostriana.dam.entitygraph.service.DireccionService;
import com.salesianostriana.dam.entitygraph.service.GrupoService;
import com.salesianostriana.dam.entitygraph.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final CiudadService ciudadService;
    private final UsuarioService usuarioService;
    private final DireccionService direccionService;
    private final GrupoService grupoService;


    @PostConstruct
    public void init() {

        Ciudad ciudad1 = Ciudad.builder().nombre("Sevilla").build();
        Ciudad ciudad2 = Ciudad.builder().nombre("Matalascañas").build();

        ciudadService.saveAll(List.of(ciudad1, ciudad2));

        Usuario usuario1 = Usuario.builder()
                .nombre("Luismi")
                .password("1234")
                .email("luismi.lopez@salesianos.edu")
                .telefono("954331488").build();

        Usuario usuario2 = Usuario.builder()
                .nombre("Miguel")
                .password("4567")
                .email("miguel.campos@salesianos.edu")
                .telefono("954331488")
                .build();

        usuarioService.saveAll(List.of(usuario1, usuario2));

        Direccion direccion1 = Direccion.builder()
                .ciudad(ciudad1)
                .usuario(usuario1)
                .tipo("Calle")
                .calle("Rue del Percebe 13")
                .piso("Ático")
                .codigoPostal("41010")
                .build();

        Direccion direccion2 = Direccion.builder()
                .ciudad(ciudad1)
                .usuario(usuario1)
                .tipo("Calle")
                .calle("Condes de Bustillo 17")
                .piso("1º DAM")
                .codigoPostal("41010")
                .build();

        Direccion direccion3 = Direccion.builder()
                .ciudad(ciudad2)
                .usuario(usuario2)
                .tipo("Calle")
                .calle("Primera linea de playa s/n")
                .piso("Bajo")
                .codigoPostal("21760")
                .build();

        direccionService.saveAll(List.of(direccion1, direccion2, direccion3));


        direccionService.findAll();

        direccionService.findAllCiudadUsuario();

        Grupo grupo = Grupo.builder()
                .nombre("Profesores")
                .build();

        grupoService.save(grupo);

        usuario1.addToGrupo(grupo);
        usuario2.addToGrupo(grupo);

        usuarioService.saveAll(List.of(usuario1, usuario2));

        grupoService.findAll();

        grupoService.findByNombre("Profesores");


    }

}
