package com.salesianostriana.dam.dto.controller;

import com.salesianostriana.dam.dto.model.Categoria;
import com.salesianostriana.dam.dto.model.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@RequiredArgsConstructor
@Tag(name = "Categoria", description = "El controlador de categorías, como no podía ser de otra manera")
public class CategoriaController {

    private final CategoriaRepository repository;

    @Operation(summary = "Obtiene todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado categorías",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Categoria.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "Iglesias"},
                                                {"id": 2, "nombre": "Románico"}
                                            ]                                          
                                            """
                            )}
                            )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna categoría",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<Categoria>> findAll() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());

    }

    @Operation(summary = "Obtiene una categoría en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                description = "Se ha encontrado la categoría",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Categoria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la categoría por el ID",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findOne(
            @Parameter(description = "ID de la categoría a buscar")
            @PathVariable Long id
    ) {

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
