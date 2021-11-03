package com.salesianostriana.dam.controller;

import com.salesianostriana.dam.dto.GetProductoConPrecioDto;
import com.salesianostriana.dam.dto.GetProductoDto;
import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.repos.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class MainController {

    private final ProductoRepository repository;

    /**
     * Método findAll, sin aplicación de DTO
     */
    @GetMapping("/")
    public ResponseEntity<List<Producto>> todos() {
        // Faltaría manejar error 404.
        return ResponseEntity.ok(repository.findAll());
    }

    /**
     * Método findAll, pero con aplicación de DTO
     */
    @GetMapping("/todosDto")
    public ResponseEntity<List<GetProductoDto>> todosDto() {
        // Faltaría manejar error 404.
        return ResponseEntity.ok(repository.todosLosProductosDto());
    }

    /**
     * Método con parámetro para filtrar, y con aplicación de DTO
     */
    @GetMapping(value = "/", params = {"precio"})
    public ResponseEntity<List<GetProductoConPrecioDto>> precioMenorQue(
            @RequestParam(defaultValue = "5.0") Double precio) {
        // Faltaría manejar error 404.
        return ResponseEntity.ok(repository.precioMenorQue(precio));
    }



}
