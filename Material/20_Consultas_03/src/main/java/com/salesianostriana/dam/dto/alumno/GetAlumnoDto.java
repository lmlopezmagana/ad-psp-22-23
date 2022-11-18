package com.salesianostriana.dam.dto.alumno;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetAlumnoDto extends GetAlumnoSinCursoDto{

    private String curso;

}
