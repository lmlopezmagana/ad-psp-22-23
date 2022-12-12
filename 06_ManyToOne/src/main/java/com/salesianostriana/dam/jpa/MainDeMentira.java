package com.salesianostriana.dam.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final CursoRepository cursoRepository;
    private final AlumnoRepository alumnoRepository;

    @PostConstruct
    public void run() {

        Curso c = Curso.builder()
                .nombre("2º DAM")
                .tutor("Luismi")
                .build();

        cursoRepository.save(c);

        List<Alumno> alumnoList =
                List.of(
                        Alumno.builder()
                                .nombre("Pepe")
                                .apellidos("Pérez")
                                .email("pepe@perez.com")
                                .curso(c)
                                .build(),
                        Alumno.builder()
                                .nombre("Ana")
                                .apellidos("Sin Nombre")
                                .email("laanuska@gmail.com")
                                .curso(c)
                                .build()
                );

        alumnoRepository.saveAll(alumnoList);


        List<Alumno> result = alumnoRepository.findAll();

        result.forEach(System.out::println);


    }

}
