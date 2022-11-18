package com.salesianostriana.dam.springdata;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.model.Asignatura;
import com.salesianostriana.dam.springdata.model.Curso;
import com.salesianostriana.dam.springdata.repos.AlumnoRepository;
import com.salesianostriana.dam.springdata.service.AlumnoService;
import com.salesianostriana.dam.springdata.service.AsignaturaService;
import com.salesianostriana.dam.springdata.service.CursoService;
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

    @PostConstruct
    public void test() {


        Alumno alumno = Alumno.builder()
                .nombre("Pepe")
                .apellidos("Gotera")
                .email("pepe.gotera@salesianos.edu")
                .asignaturas(new ArrayList<>())
                .build();


        List<Asignatura> asignaturas = List.of(
            Asignatura.builder().nombre("PSP").profesor("Luismi").build(),
            Asignatura.builder().nombre("EIE").profesor("Jesús").build(),
            Asignatura.builder().nombre("PMDM").profesor("Miguel").build()
        );



        alumnoService.save(alumno);

        asignaturaService.saveAll(asignaturas);


        //alumno.getAsignaturas().addAll(asignaturas);
        //asignaturas.forEach(asignatura -> alumno.addAsignatura(asignatura));
        asignaturas.forEach(alumno::addAsignatura);

        alumnoService.edit(alumno);

        System.out.println("Alumno: " + alumno.getNombre() + " " + alumno.getApellidos());
        System.out.println("\tAsignaturas:");
        alumno.getAsignaturas().forEach(a -> System.out.println("\t\t" + a.getNombre() + ", Profesor: " + a.getProfesor()));


        Alumno al = alumnoService.findById(1l).orElse(null);
        al.getAsignaturas().forEach(a -> System.out.println("\t\t" + a.getNombre() + ", Profesor: " + a.getProfesor()));


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

        Asignatura accesoADatos = asignaturaService.findById(5l).orElse(null);

        System.out.println("Asignatura: " + accesoADatos.getNombre());
        System.out.println("\tAlumnos:");
        accesoADatos.getAlumnos().forEach(v -> {
            System.out.println("\t\t- " + v.getNombre() + " " + v.getApellidos());
        });






    }

}
