package com.salesianostriana.dam.controller;


import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    // Este método se podría paginar, incluir parámetros de filtro, ...
    @Operation(summary = "Obtener todos los productos", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/")
    public ResponseEntity<List<Producto>> buscarTodos() {

        List<Producto> result = productoService.findAll();

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);



    }

    @Operation(summary = "Obtener un producto", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerUno(@PathVariable Long id) {
        return ResponseEntity.of(productoService.findById(id));
    }

    @Operation(summary = "Crear un nuevo producto", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/")
    public ResponseEntity<Producto> nuevoProducto(@RequestBody Producto producto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

    @Operation(summary = "Obtener todos los productos", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Producto> editarProducto(@RequestBody Producto aEditar, @PathVariable Long id) {

        return productoService.findById(id).map(p -> {
            p.setNombre(aEditar.getNombre());
            p.setPrecio(aEditar.getPrecio());
            return ResponseEntity.ok(productoService.save(p));
        }).orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Obtener todos los productos", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {

        productoService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
