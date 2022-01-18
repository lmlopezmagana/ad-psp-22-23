package com.salesianostriana.dam.dto.converter;

import javax.annotation.PostConstruct;

import com.salesianostriana.dam.dto.ProductoDTO;
import com.salesianostriana.dam.modelo.Producto;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductoDTOConverter {
	

	public ProductoDTO convertToDto(Producto producto) {
		return ProductoDTO.builder()
				.nombre(producto.getNombre())
				.imagen(producto.getImagen())
				.categoria(producto.getCategoria().getNombre())
				.id(producto.getId())
				.build();	}
	

	
	
	
}
