package com.salesianostriana.dam.entitygraph.service;

import com.salesianostriana.dam.entitygraph.model.Direccion;
import com.salesianostriana.dam.entitygraph.repos.DireccionRepository;
import com.salesianostriana.dam.entitygraph.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService extends BaseService<Direccion, Long, DireccionRepository> {

    public List<Direccion> findAllCiudadUsuario() {
        return repositorio.findAll();
    }

}
