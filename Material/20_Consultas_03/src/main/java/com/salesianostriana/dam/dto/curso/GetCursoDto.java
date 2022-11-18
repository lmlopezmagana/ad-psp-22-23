package com.salesianostriana.dam.dto.curso;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetCursoDto {

    private Long id;
    private String nombre, tutor;


}
