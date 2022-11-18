package com.salesianostriana.dam.dto.curso;

import com.salesianostriana.dam.dto.alumno.AlumnoDtoConverter;
import com.salesianostriana.dam.dto.alumno.GetAlumnoSinCursoDto;
import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CursoDtoConverter {

    @Autowired
    private AlumnoDtoConverter alumnoDtoConverter;


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
                .alumnos(c.getAlumnos().stream().map(alumnoDtoConverter::convertAlumnoToGetAlumnoSinCursoDto).collect(Collectors.toList()))
                .build();
    }





}
