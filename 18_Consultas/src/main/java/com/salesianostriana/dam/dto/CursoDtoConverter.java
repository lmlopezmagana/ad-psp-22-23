package com.salesianostriana.dam.dto;

import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.model.Curso;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CursoDtoConverter {

    public GetCursoDto convertCursoToGetCursoDto(Curso c) {
        return GetCursoDto.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .tutor(c.getTutor())
                .build();

    }

    public GetCursoConAlumnosDto convertCursoToGetCursoConAlumnosDto(Curso c) {
        return GetCursoConAlumnosDto.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .tutor(c.getTutor())
                .alumnos(c.getAlumnos().stream().map(this::convertAlumnoToGetAlumnoSinCursoDto).collect(Collectors.toList()))
                .build();
    }

    public GetAlumnoSinCursoDto convertAlumnoToGetAlumnoSinCursoDto(Alumno a) {
        return GetAlumnoSinCursoDto.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .apellido1(a.getApellido1())
                .apellido2(a.getApellido2())
                .fechaNacimiento(a.getFechaNacimiento())
                .email(a.getEmail())
                .build();
    }



}
