package com.salesianostriana.dam.controladores;


import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.servicios.ProductoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/producto")
public class ProductoControlador {

    private final ProductoServicio servicio;

    @GetMapping("/")
    public List<Producto> todos() {
        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public Producto uno(@PathVariable Long id) {
        return servicio.findById(id);
    }

    @PostMapping("/")
    public Producto crear(@Valid @RequestBody Producto producto) {
        return servicio.save(producto);
    }



}
