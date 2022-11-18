package com.salesianostriana.dam.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductoCantidadDto {

    private Long idProducto;
    private int cantidad;

}
