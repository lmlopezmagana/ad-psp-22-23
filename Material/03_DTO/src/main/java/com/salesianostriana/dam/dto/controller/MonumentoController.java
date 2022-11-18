package com.salesianostriana.dam.dto.controller;

import com.salesianostriana.dam.dto.dto.CreateMonumentoDto;
import com.salesianostriana.dam.dto.dto.GetMonumentoDto;
import com.salesianostriana.dam.dto.dto.MonumentoDtoConverter;
import com.salesianostriana.dam.dto.model.Categoria;
import com.salesianostriana.dam.dto.model.CategoriaRepository;
import com.salesianostriana.dam.dto.model.Monumento;
import com.salesianostriana.dam.dto.model.MonumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monumento")
public class MonumentoController {

    private final MonumentoRepository repository;
    private final MonumentoDtoConverter dtoConverter;
    private final CategoriaRepository categoriaRepository;

    @GetMapping("/")
    public ResponseEntity<List<GetMonumentoDto>> findAll() {

        List<Monumento> data = repository.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<GetMonumentoDto> result =
                    data.stream()
                    .map(dtoConverter::monumentoToGetMonumentoDto)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .ok()
                    .body(result);

        }



    }

    @GetMapping("/{id}")
    public ResponseEntity<Monumento> findOne(@PathVariable Long id) {

        return ResponseEntity
                .of(repository.findById(id));

    }


    @PostMapping("/")
    //public ResponseEntity<Monumento> create(@RequestBody Monumento nuevo) {
    public ResponseEntity<Monumento> create(@RequestBody CreateMonumentoDto dto) {

        if (dto.getCategoriaId() == null) {
            return ResponseEntity.badRequest().build();
        }


        Monumento nuevo = dtoConverter.createMonumentoDtoToMonumento(dto);

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);

        nuevo.setCategoria(categoria);


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
