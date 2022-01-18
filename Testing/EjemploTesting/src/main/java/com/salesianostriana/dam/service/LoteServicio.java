package com.salesianostriana.dam.service;

import java.util.Optional;

import com.salesianostriana.dam.errores.excepciones.LoteCreateException;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.dto.CreateLoteDTO;
import com.salesianostriana.dam.modelo.Lote;
import com.salesianostriana.dam.repos.LoteRepositorio;
import com.salesianostriana.dam.service.base.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoteServicio extends BaseService<Lote, Long, LoteRepositorio> {

	private final ProductoServicio productoServicio;
	
	@Override
	public Optional<Lote> findById(Long id) {
		return repositorio.findByIdJoinFetch(id);
	}

	public Lote nuevoLote(CreateLoteDTO nuevoLote) {
		
		Lote l = Lote.builder()
					.nombre(nuevoLote.getNombre())
					.build();
		
		nuevoLote.getProductos().stream()
			.map(id -> {
				return productoServicio.findByIdConLotes(id).orElseThrow(() -> new LoteCreateException());
			})
			.forEach(l::addProducto);
			
		return save(l);
		
	}
	
	

}
