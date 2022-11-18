package com.salesianostriana.dam.springdata.service;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.model.Curso;
import com.salesianostriana.dam.springdata.model.Notas;
import com.salesianostriana.dam.springdata.model.NotasPK;
import com.salesianostriana.dam.springdata.repos.NotasRepository;
import com.salesianostriana.dam.springdata.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class NotasService extends
        BaseService<Notas, NotasPK, NotasRepository> {


    public Alumno matriculaCurso(Alumno a, Curso c) {

        // 1. Obtener todas las asignaturas del curso
        // 2. Crear una nueva instancia de Notas por cada asignatura
        // 3. Insertarlas en la base de datos.

        c.getAsignaturas().forEach(asignatura -> {
            Notas n = Notas.builder()
                    .alumno(a)
                    .asignatura(asignatura)
                    .build();
            save(n);
        });

        return a;
    }


}
