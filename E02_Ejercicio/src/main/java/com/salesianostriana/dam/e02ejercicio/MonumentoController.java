package com.salesianostriana.dam.e02ejercicio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monumento")
public class MonumentoController {

    private final MonumentoRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Monumento>> findAll() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Monumento> findOne(@PathVariable Long id) {

        /*return ResponseEntity
                .ok()
                .body(repository.findById(id).orElse(null));
        */

        return ResponseEntity
                .of(repository.findById(id));

    }


    @PostMapping("/")
    public ResponseEntity<Monumento> create(@RequestBody Monumento nuevo) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(nuevo));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Monumento> edit(
            @RequestBody Monumento e,
            @PathVariable Long id) {

        return ResponseEntity.of(
            repository.findById(id).map(m -> {
               m.setCodigoPais(e.getCodigoPais());
               m.setPais(e.getPais());
               m.setCiudad(e.getCiudad());
               m.setDescripcion(e.getDescripcion());
               m.setLoc(e.getLoc());
               m.setNombre(e.getNombre());
               m.setUrlImagen(e.getUrlImagen());
               repository.save(m);
               return m;
            })
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
