package com.salesianostriana.dam.springdata;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.model.Asignatura;
import com.salesianostriana.dam.springdata.model.Curso;
import com.salesianostriana.dam.springdata.repos.AlumnoRepository;
import com.salesianostriana.dam.springdata.service.AlumnoService;
import com.salesianostriana.dam.springdata.service.AsignaturaService;
import com.salesianostriana.dam.springdata.service.CursoService;
import com.salesianostriana.dam.springdata.service.NotasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final AlumnoService alumnoService;
    private final CursoService cursoService;
    private final AsignaturaService asignaturaService;
    private final NotasService notasService;

    @PostConstruct
    public void test() {


        Alumno alumno = Alumno.builder()
                .nombre("Pepe")
                .apellidos("Gotera")
                .email("pepe.gotera@salesianos.edu")
                .build();

        alumnoService.edit(alumno);


        Curso dam2 = Curso.builder()
                .nombre("2º DAM")
                .tutor("Luismi")
                .build();

        cursoService.save(dam2);

        List<Asignatura> asignaturas = List.of(
            Asignatura.builder().nombre("PSP").profesor("Luismi").build(),
            Asignatura.builder().nombre("EIE").profesor("Jesús").build(),
            Asignatura.builder().nombre("PMDM").profesor("Miguel").build()
        );

        for(int i = 0; i < asignaturas.size(); i++){
            asignaturas.get(i).addToCurso(dam2);
        }

        asignaturaService.saveAll(asignaturas);


        /*

        Asignatura asignatura = Asignatura.builder()
                .nombre("AD")
                .profesor("Luismi")
                .alumnos(new ArrayList<>())
                .build();
        asignaturaService.save(asignatura);

        List<Alumno> nuevosAlumnos = List.of(
          Alumno.builder().nombre("Alejandro").asignaturas(new ArrayList<>()).build(),
          Alumno.builder().nombre("Cynthia").asignaturas(new ArrayList<>()).build(),
          Alumno.builder().nombre("Pablo").asignaturas(new ArrayList<>()).build(),
          Alumno.builder().nombre("Manuel").asignaturas(new ArrayList<>()).build()
        );

        nuevosAlumnos.forEach(nuevoAlumno -> nuevoAlumno.addAsignatura(asignatura));

        alumnoService.saveAll(nuevosAlumnos);
        */

        alumno = notasService.matriculaCurso(alumno, dam2);





    }

}
