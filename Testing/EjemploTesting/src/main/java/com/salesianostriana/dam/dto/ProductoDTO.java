package com.salesianostriana.dam.dto;

import com.fasterxml.jackson.annotation.JsonView;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.salesianostriana.dam.util.views.*;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductoDTO {
	
	@JsonView(ProductoViews.Dto.class)
	private long id;
	@JsonView(ProductoViews.Dto.class)
	private String nombre;
	@JsonView(ProductoViews.Dto.class)
	private String imagen;
	@JsonView(ProductoViews.DtoConPrecio.class)
	private float precio;
	@JsonView(ProductoViews.Dto.class)
	private String categoria;
	

}
