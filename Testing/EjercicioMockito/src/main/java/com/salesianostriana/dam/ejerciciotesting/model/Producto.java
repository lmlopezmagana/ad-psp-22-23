package com.salesianostriana.dam.ejerciciotesting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Producto {

    private String codigoProducto;
    private String nombre;
    private double precio;

}
