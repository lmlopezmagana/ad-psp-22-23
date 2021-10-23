package com.salesianostriana.dam.entitygraph.service;

import com.salesianostriana.dam.entitygraph.model.Usuario;
import com.salesianostriana.dam.entitygraph.repos.UsuarioRepository;
import com.salesianostriana.dam.entitygraph.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseService<Usuario, Long, UsuarioRepository> {
}
