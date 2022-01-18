package com.salesianostriana.dam.service;

import com.salesianostriana.dam.service.base.BaseService;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.modelo.Categoria;
import com.salesianostriana.dam.repos.CategoriaRepositorio;

@Service
public class CategoriaServicio extends BaseService<Categoria, Long, CategoriaRepositorio> {

}
