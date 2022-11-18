package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.repos.ClienteRepository;
import com.salesianostriana.dam.service.base.BaseService;
import com.salesianostriana.dam.service.base.BaseUsuarioService;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends BaseUsuarioService<Cliente, ClienteRepository> {
}
