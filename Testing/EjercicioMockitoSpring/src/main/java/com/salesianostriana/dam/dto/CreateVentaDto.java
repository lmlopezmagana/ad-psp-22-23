package com.salesianostriana.dam.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateVentaDto {

    private List<ProductoCantidadDto> productos;
    private Long clienteId;


}
