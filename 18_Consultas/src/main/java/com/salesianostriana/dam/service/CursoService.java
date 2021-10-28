package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Curso;
import com.salesianostriana.dam.repos.AlumnoRepository;
import com.salesianostriana.dam.repos.CursoRepository;
import com.salesianostriana.dam.service.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService extends BaseService<Curso, Long, CursoRepository> {

    private final AlumnoRepository alumnoRepository;


    /**
     * Envoltorio par el método findByNombreContains
     */
    public List<Curso> buscarPorNombre(String nombre) {
        return repositorio.findByNombreContains(nombre);
    }

    /**
     * Devuelve el número de alumnos en un curso
     */
    public long alumnoEnUnCurso(Curso curso) {
        return alumnoRepository.countByCurso(curso);
    }



}
