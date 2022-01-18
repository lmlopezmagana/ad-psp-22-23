package com.salesianostriana.dam.controller;

import javax.servlet.http.HttpServletRequest;

import com.salesianostriana.dam.errores.excepciones.LoteNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.salesianostriana.dam.dto.CreateLoteDTO;
import com.salesianostriana.dam.modelo.Lote;
import com.salesianostriana.dam.service.LoteServicio;
import com.salesianostriana.dam.util.pagination.PaginationLinksUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/lote")
@RequiredArgsConstructor
public class LoteController {
	
	private final LoteServicio loteServicio;
	private final PaginationLinksUtils paginationLinksUtils;
	
	
	@GetMapping("/")
	public ResponseEntity<?> lotes(Pageable pageable, HttpServletRequest request) throws LoteNotFoundException {
		Page<Lote> result = loteServicio.findAll(pageable);
		
		
		if (result.isEmpty()) {
			throw new LoteNotFoundException();
		} else {
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(result, uriBuilder))
					.body(result);

		}
	}
	
	
	@PostMapping("/")
	public ResponseEntity<?> nuevoLote(@RequestBody CreateLoteDTO nuevoLote) {
		Lote lote = loteServicio.nuevoLote(nuevoLote);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lote);
	}

}
