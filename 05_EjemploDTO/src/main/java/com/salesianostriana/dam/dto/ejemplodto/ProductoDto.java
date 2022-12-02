package com.salesianostriana.dam.dto.ejemplodto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoDto {

    @JsonView(ProductoViews.Master.class)
    private Long id;
    @JsonView(ProductoViews.Master.class)
    private String nombre;
    @JsonView(ProductoViews.Detail.class)
    private String descripcion;
    @JsonView(ProductoViews.Detail.class)
    private List<String> images;
    @JsonView(ProductoViews.Detail.class)
    private double precio;


    public static ProductoDto productoSinDetalle(ProductoDto productoDto) {
        return ProductoDto.builder()
                .id(productoDto.getId())
                .nombre(productoDto.getNombre())
                .precio(productoDto.getPrecio())
                .build();
    }


}
