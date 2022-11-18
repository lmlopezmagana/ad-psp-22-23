package com.salesianostriana.dam.dto.dto;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetMonumentoDto {

    private String nombre;
    private String loc;
    private String ubicacion;
    private String categoria;

}
