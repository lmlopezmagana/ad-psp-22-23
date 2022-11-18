package com.salesianostriana.dam.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.salesianostriana.dam.dto.CreateProductoDTO;
import com.salesianostriana.dam.dto.EditProductoDTO;
import com.salesianostriana.dam.dto.ProductoDTO;
import com.salesianostriana.dam.dto.converter.ProductoDTOConverter;
import com.salesianostriana.dam.errores.excepciones.ProductoNotFoundException;
import com.salesianostriana.dam.errores.excepciones.SearchProductoNoResultException;
import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.service.ProductoServicio;
import com.salesianostriana.dam.util.pagination.PaginationLinksUtils;
import com.salesianostriana.dam.util.views.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductoController {

	private final ProductoServicio productoServicio;
	private final ProductoDTOConverter productoDTOConverter;
	private final PaginationLinksUtils paginationLinksUtils;

	/**
	 * Método que nos permite buscar sobre una lista de productos
	 * 
	 * @param txt      Fragmento del nombre del producto
	 * @param precio   Precio tope
	 * @param pageable
	 * @param request
	 * @return 200 OK. Si hay productos (o si hay productos que cumplan los
	 *         parámetros de búsqueda).
	 */

	@JsonView(ProductoViews.DtoConPrecio.class)
	@GetMapping(value = "/producto")
	public ResponseEntity<?> buscarProductosPorVarios(@RequestParam("nombre") Optional<String> txt,
			@RequestParam("precio") Optional<Float> precio, Pageable pageable, HttpServletRequest request) {

		Page<Producto> result = productoServicio.findByArgs(txt, precio, pageable);

		if (result.isEmpty()) {
			throw new SearchProductoNoResultException();
		} else {

			Page<ProductoDTO> dtoList = result.map(productoDTOConverter::convertToDto);
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

			return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
					.body(dtoList);

		}

	}

	/**
	 * Obtenemos un producto en base a su ID
	 * 
	 * @param id
	 * @return 404 si no encuentra el producto, 200 y el producto si lo encuentra
	 */
	@GetMapping("/producto/{id}")
	public Producto obtenerUno(@PathVariable Long id) {

		return productoServicio.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));

	}

	/**
	 * Insertamos un nuevo producto
	 * 
	 * @param nuevo
	 * @return 201 y el producto insertado
	 */
	@PostMapping(value = "/producto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // Aunque no es obligatorio,
																						// podemos indicar que se
																						// consume multipart/form-data
	public ResponseEntity<Producto> nuevoProducto(@RequestPart("nuevo") CreateProductoDTO nuevo,
			@RequestPart("file") MultipartFile file) {

		return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.nuevoProducto(nuevo, file));
	}

	/**
	 * 
	 * @param editar
	 * @param id
	 * @return 200 Ok si la edición tiene éxito, 404 si no se encuentra el producto
	 */
	@PutMapping("/producto/{id}")
	public Producto editarProducto(@RequestBody EditProductoDTO editar, @PathVariable Long id) {

		return productoServicio.findById(id).map(p -> {
			p.setNombre(editar.getNombre());
			p.setPrecio(editar.getPrecio());
			return productoServicio.save(p);
		}).orElseThrow(() -> new ProductoNotFoundException(id));

	}

	/**
	 * Borra un producto del catálogo en base a su id
	 * 
	 * @param id
	 * @return Código 204 sin contenido
	 */
	@DeleteMapping("/producto/{id}")
	public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {

		Producto producto = productoServicio.findById(id).orElseThrow(() -> new ProductoNotFoundException(id));

		productoServicio.delete(producto);
		return ResponseEntity.noContent().build();

	}

}
