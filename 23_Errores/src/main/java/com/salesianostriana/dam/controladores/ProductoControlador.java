package com.salesianostriana.dam.controladores;


import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.servicios.ProductoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
