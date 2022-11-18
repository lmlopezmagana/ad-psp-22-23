package com.salesianostriana.dam.dto.controller;

import com.salesianostriana.dam.dto.model.Categoria;
import com.salesianostriana.dam.dto.model.CategoriaRepository;
import com.salesianostriana.dam.dto.model.Monumento;
import com.salesianostriana.dam.dto.model.MonumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Categoria>> findAll() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findOne(@PathVariable Long id) {

        return ResponseEntity
                .of(repository.findById(id));

    }


    @PostMapping("/")
    public ResponseEntity<Categoria> create(@RequestBody Categoria nueva) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(nueva));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> edit(
            @RequestBody Categoria e,
            @PathVariable Long id) {

        return ResponseEntity.of(
                repository.findById(id).map(c -> {
                    c.setNombre(e.getNombre());
                    repository.save(c);
                    return c;
                })
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
