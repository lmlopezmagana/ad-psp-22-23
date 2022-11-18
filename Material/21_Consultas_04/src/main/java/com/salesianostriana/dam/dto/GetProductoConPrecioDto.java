package com.salesianostriana.dam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class GetProductoConPrecioDto extends GetProductoDto{

    private Double precio;

    public GetProductoConPrecioDto(long id, String nombre, String categoria, Double precio) {
        super(id, nombre, categoria);
        this.precio = precio;
    }
}
