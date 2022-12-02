package com.salesianostriana.dam.dto.ejemplodto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class ProductoExtendidoDto extends ProductoDto{

    private List<Integer> valoraciones;
    private double valoracionMedia;

    public static ProductoExtendidoDto of(Producto producto) {
        return new ProductoExtendidoDto();
    }



}
