package com.salesianostriana.dam.springdata.service;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.repos.AlumnoRepository;
import com.salesianostriana.dam.springdata.service.base.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService
        extends BaseService<Alumno, Long, AlumnoRepository> {


}
