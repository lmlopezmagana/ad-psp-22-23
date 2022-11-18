package com.salesianostriana.dam.springdata;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.repos.AlumnoRepository;
import com.salesianostriana.dam.springdata.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final AlumnoService service;

    @PostConstruct
    public void test() {

        Alumno alumno = Alumno.builder()
                .nombre("Luismi")
                .apellidos("López")
                .email("luismi.lopez@salesianos.edu")
                .build();

        service.save(alumno);

        service
                .findAll()
                .forEach( a -> System.out.printf("%s %s\n", a.getNombre(), a.getApellidos()));


    }

}
