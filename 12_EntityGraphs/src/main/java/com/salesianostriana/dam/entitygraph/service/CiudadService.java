package com.salesianostriana.dam.entitygraph.service;

import com.salesianostriana.dam.entitygraph.model.Ciudad;
import com.salesianostriana.dam.entitygraph.repos.CiudadRepository;
import com.salesianostriana.dam.entitygraph.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CiudadService extends BaseService<Ciudad, Long, CiudadRepository> {
}
