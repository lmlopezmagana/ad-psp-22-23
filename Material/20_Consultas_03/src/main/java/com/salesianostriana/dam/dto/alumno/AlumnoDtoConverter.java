package com.salesianostriana.dam.dto.alumno;

import com.salesianostriana.dam.model.Alumno;
import org.springframework.stereotype.Component;

@Component
public class AlumnoDtoConverter {

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

    public GetAlumnoDto convertAlumnoToGetAlumnoDto(Alumno a) {
        return GetAlumnoDto.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .apellido1(a.getApellido1())
                .apellido2(a.getApellido2())
                .fechaNacimiento(a.getFechaNacimiento())
                .email(a.getEmail())
                .curso(a.getCurso().getNombre())
                .build();
    }

}
