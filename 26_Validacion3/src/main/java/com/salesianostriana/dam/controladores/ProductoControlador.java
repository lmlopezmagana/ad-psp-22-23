package com.salesianostriana.dam.controladores;


import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.servicios.ProductoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producto")
@Validated
public class ProductoControlador {

    private final ProductoServicio servicio;

    @GetMapping("/")
    public List<Producto> todos() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public Producto uno(@PathVariable @Min(value = 0, message = "No se pueden buscar productos con un identificador negativo") Long id) {
        return servicio.findById(id);
    }

    @PostMapping("/")
    public Producto crear(@Valid @RequestBody Producto producto) {
        return servicio.save(producto);
    }



}
