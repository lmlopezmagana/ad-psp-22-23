package com.salesianostriana.dam.entitygraph.service;

import com.salesianostriana.dam.entitygraph.model.Grupo;
import com.salesianostriana.dam.entitygraph.repos.GrupoRepository;
import com.salesianostriana.dam.entitygraph.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService extends BaseService<Grupo, Long, GrupoRepository> {

    public List<Grupo> findByNombre(String nombre) {
        return repositorio.findByNombre(nombre);
    }

}
