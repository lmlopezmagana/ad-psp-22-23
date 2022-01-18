package com.salesianostriana.dam.ejerciciotesting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineaDeVenta {

    private Producto producto;
    private int cantidad;
    private double pvp; // precio unitario
}
