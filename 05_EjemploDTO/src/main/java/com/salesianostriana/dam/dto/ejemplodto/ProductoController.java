package com.salesianostriana.dam.dto.ejemplodto;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto/")
public class ProductoController {

    private List<ProductoDto> todos =
            new ArrayList<>(List.of(
                    ProductoDto.builder()
                            .id(1L)
                            .nombre("Café Catunambú")
                            .descripcion("El auténtico café de Andalucía")
                            .precio(1.30)
                            .images(List.of(
                                    "https://www.catunambu.es/wp-content/uploads/2020/08/imagen-grano-natural.png",
                                    "https://www.elcafedeandalucia.es/wp-content/uploads/2018/12/molido_mezcla_ok.jpg"
                            ))
                            .build(),
                    ProductoDto.builder()
                            .id(2L)
                            .nombre("Jamón Ibérico Joselito")
                            .descripcion("Sin palabras")
                            .precio(499.0)
                            .build()
            ));


    @JsonView(ProductoViews.Master.class)
    @GetMapping("/")
    public ResponseEntity<List<ProductoDto>> getAll() {
        /*return ResponseEntity.ok(
                todos
                        .stream().map(ProductoDto::productoSinDetalle)
                        .toList()
                );*/
        return ResponseEntity.ok(todos);
    }

    @JsonView(ProductoViews.Detail.class)
    @GetMapping("/details")
    public ResponseEntity<List<ProductoDto>> getAllWithDetails() {
        /*return ResponseEntity.ok(
                todos
                        .stream().map(ProductoDto::productoSinDetalle)
                        .toList()
                );*/
        return ResponseEntity.ok(todos);
    }

    @JsonView(ProductoViews.Detail.class)
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> getById(@PathVariable Long id) {

        Optional<ProductoDto> elegido =
                todos.stream()
                        .filter(p -> p.getId() == id)
                        .findFirst();

        return ResponseEntity.of(elegido);

    }

    @JsonView(ProductoViews.Detail.class)
    @PostMapping("/")
    public ResponseEntity<ProductoDto>
            newProduct(@RequestBody ProductoDto newProductDto) {

        newProductDto.setId((long) todos.size()+1);
        todos.add(newProductDto);

        /*return ResponseEntity.status(HttpStatus.CREATED).
                body(newProductDto);*/

        return ResponseEntity.created(null).body(newProductDto);


    }

}
