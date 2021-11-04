package com.salesianostriana.dam.controller;


import com.salesianostriana.dam.dto.alumno.AlumnoDtoConverter;
import com.salesianostriana.dam.dto.alumno.GetAlumnoDto;
import com.salesianostriana.dam.dto.alumno.GetAlumnoSinCursoDto;
import com.salesianostriana.dam.dto.curso.GetCursoDto;
import com.salesianostriana.dam.model.Alumno;
import com.salesianostriana.dam.model.Curso;
import com.salesianostriana.dam.search.AlumnoSpecificationBuilder;
import com.salesianostriana.dam.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alumno")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService servicio;
    private final AlumnoDtoConverter dtoConverter;


    @GetMapping("/")
    public ResponseEntity<List<?>> todos() {
        return buildResponseFromQuery(servicio.findAll(), dtoConverter::convertAlumnoToGetAlumnoSinCursoDto);
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


    @GetMapping(value = "/", params = {"search"})
    public ResponseEntity<List<?>> busquedaDeAlumnos(@RequestParam("search") String search) {
        AlumnoSpecificationBuilder builder = new AlumnoSpecificationBuilder();

        // Validamos la cadena de búsqueda
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Alumno> spec = builder.build();
        return buildResponseFromQuery(servicio.alumnosConSpecification(spec), dtoConverter::convertAlumnoToGetAlumnoSinCursoDto);

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
