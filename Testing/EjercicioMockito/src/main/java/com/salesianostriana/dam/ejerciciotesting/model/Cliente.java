package com.salesianostriana.dam.ejerciciotesting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Cliente {

    private String dni; // Campo clave
    private String nombre;
    private String email;

}
