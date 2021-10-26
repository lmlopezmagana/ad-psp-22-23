package com.salesianostriana.dam.springdata;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.repos.AlumnoRepository;
import com.salesianostriana.dam.springdata.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final AlumnoService service;

    @PostConstruct
    public void test() throws InterruptedException {

        Alumno alumno = Alumno.builder()
                .nombre("Luismi")
                .apellidos("López")
                .email("luismi.lopez@salesianos.edu")
                .build();

        service.save(alumno);

        service
                .findAll()
                .forEach( a -> System.out.printf("%s %s\n", a.getNombre(), a.getApellidos()));

        Thread.sleep(5000);

        alumno.setFechaNacimiento(LocalDate.of(1982, 9, 18));

        service.edit(alumno);

    }

}
