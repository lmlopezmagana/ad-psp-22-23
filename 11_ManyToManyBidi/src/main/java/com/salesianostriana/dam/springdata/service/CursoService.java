package com.salesianostriana.dam.springdata.service;

import com.salesianostriana.dam.springdata.model.Curso;
import com.salesianostriana.dam.springdata.repos.CursoRepository;
import com.salesianostriana.dam.springdata.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CursoService extends BaseService<Curso, Long, CursoRepository> {


}
