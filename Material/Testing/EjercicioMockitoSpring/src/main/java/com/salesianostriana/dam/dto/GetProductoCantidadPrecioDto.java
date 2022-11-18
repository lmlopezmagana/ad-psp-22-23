package com.salesianostriana.dam.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetProductoCantidadPrecioDto {

    private String producto;
    private int cantidad;
    private double pvp;
    private double subtotal;

}
