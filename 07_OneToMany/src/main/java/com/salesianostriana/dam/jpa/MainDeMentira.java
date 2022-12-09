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

        Curso c1 = Curso.builder()
                .nombre("1º DAM")
                .tutor("Miguel")
                .build();

        cursoRepository.save(c);
        cursoRepository.save(c1);

        Alumno a1 = Alumno.builder()
                .nombre("Pepe")
                .apellidos("Pérez")
                .email("pepe@perez.com")
                //.curso(c)
                .build();
        Alumno a2 = Alumno.builder()
                .nombre("Ana")
                .apellidos("Sin Nombre")
                .email("laanuska@gmail.com")
                //.curso(c1)
                .build();

        List<Alumno> alumnoList = List.of(a1, a2);

        a1.addToCurso(c);
        a2.addToCurso(c1);

        alumnoRepository.saveAll(alumnoList);

        //cursoRepository.delete(c);

        cursoRepository.findAll()
                .forEach(curso -> {
                    System.out.println("Curso: " +  curso.toString());
                    curso.getAlumnos().forEach(System.out::println);
                });






    }

}
