package com.salesianostriana.dam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GetProductoDto {

    private long id;
    private String nombre;
    private String categoria;

}
