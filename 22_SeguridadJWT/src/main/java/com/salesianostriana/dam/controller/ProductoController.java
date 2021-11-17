package com.salesianostriana.dam.controller;


import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    // Este método se podría paginar, incluir parámetros de filtro, ...
    @GetMapping("/")
    public ResponseEntity<List<Producto>> buscarTodos() {

        List<Producto> result = productoService.findAll();

        if (result.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);



    }


    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerUno(@PathVariable Long id) {
        return ResponseEntity.of(productoService.findById(id));
    }


    @PostMapping("/")
    public ResponseEntity<Producto> nuevoProducto(@RequestBody Producto producto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> editarProducto(@RequestBody Producto aEditar, @PathVariable Long id) {

        return productoService.findById(id).map(p -> {
            p.setNombre(aEditar.getNombre());
            p.setPrecio(aEditar.getPrecio());
            return ResponseEntity.ok(productoService.save(p));
        }).orElse(ResponseEntity.notFound().build());

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarProducto(@PathVariable Long id) {

        productoService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
