package com.salesianostriana.dam.controller;

import com.salesianostriana.dam.dto.curso.CursoDtoConverter;
import com.salesianostriana.dam.dto.curso.GetCursoConAlumnosDto;
import com.salesianostriana.dam.dto.curso.GetCursoDto;
import com.salesianostriana.dam.model.Curso;
import com.salesianostriana.dam.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/curso")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService servicio;
    private final CursoDtoConverter cursoDtoConverter;

    @GetMapping("/")
    public ResponseEntity<List<GetCursoDto>> todos() {
        List<Curso> data = servicio.findAll();
        return buildResponseFromQuery(data);

    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCursoConAlumnosDto> unCurso(@PathVariable Long id) {
        return ResponseEntity.of(servicio.findById(id).map(cursoDtoConverter::convertCursoToGetCursoConAlumnosDto));
    }


    /**
     * Si tenemos dos peticiones con la misma ruta, Spring produce error de compilación.
     * Esta petición tiene la misma ruta que la petición todos(), pero solamente se invoca
     * si incluye el parámetro nombre en la query string
     * @param nombre
     * @return
     */
    @GetMapping(value = "/", params = {"nombre"})
    public ResponseEntity<List<GetCursoDto>> cursoPorNombre(@RequestParam String nombre) {
        return buildResponseFromQuery(servicio.buscarPorNombre(nombre));
    }

    /**
     * Este método nos permite unificar, para varios métodos de controlador diferentes
     * una salida unificada de un listado de GetCursoDto. Evita código repetitivo
     * @param data Lista de Curso. Puede estar empty
     * @return ResponseEntity adecuado
     */
    private ResponseEntity<List<GetCursoDto>> buildResponseFromQuery(List<Curso> data) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(data.stream()
                    .map(cursoDtoConverter::convertCursoToGetCursoDto)
                    .collect(Collectors.toList())
            );

    }


}
