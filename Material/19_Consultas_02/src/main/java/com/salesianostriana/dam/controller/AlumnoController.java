package com.salesianostriana.dam.controller;


import com.salesianostriana.dam.dto.alumno.AlumnoDtoConverter;
import com.salesianostriana.dam.dto.alumno.GetAlumnoDto;
import com.salesianostriana.dam.dto.alumno.GetAlumnoSinCursoDto;
import com.salesianostriana.dam.dto.curso.GetCursoDto;
import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.model.Curso;
import com.salesianostriana.dam.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alumno")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService servicio;
    private final AlumnoDtoConverter dtoConverter;


    @GetMapping("/")
    public ResponseEntity<List<?>> todos() {
        return buildResponseFromQuery(servicio.findAll(), dtoConverter::convertAlumnoToGetAlumnoDto);
    }


    @GetMapping("/sincurso")
    public ResponseEntity<List<?>> alumnosSinCurso() {
        return buildResponseFromQuery(servicio.alumnosSinCurso(), dtoConverter::convertAlumnoToGetAlumnoSinCursoDto);
    }

    @GetMapping("/curso/{curso}/nacido/{fecha}")
    public ResponseEntity<List<?>> alumnosDeUnCursoNacidosDespuesDe(@PathVariable("curso") String nombreCurso, @PathVariable String fecha) {
        if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return ResponseEntity.badRequest().build();
        }

        return buildResponseFromQuery(servicio.alumnosDeUnCursoNacidosDespuesDe(fecha, nombreCurso), dtoConverter::convertAlumnoToGetAlumnoDto);

    }



    /**
     * Este método es más genérico aun que el del ejemplo 18, puesto que acepta el método conversor. Así podemos utilizarlo
     * para la conversión del DTO de alumno más genérico, GetAlumnosinCursoDto, o para alguno de sus derivados.
     * @param data
     * @param function
     * @return
     */
    private ResponseEntity<List<?>> buildResponseFromQuery(List<Alumno> data, Function<Alumno, GetAlumnoSinCursoDto> function) {
        if (data.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(data.stream()
                    //.map(dtoConverter::convertAlumnoToGetAlumnoDto)
                    .map(function)
                    .collect(Collectors.toList())
            );

    }

}
