package com.salesianostriana.dam.springdata.service;

import com.salesianostriana.dam.springdata.model.Asignatura;
import com.salesianostriana.dam.springdata.repos.AsignaturaRepository;
import com.salesianostriana.dam.springdata.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignaturaService
        extends BaseService<Asignatura, Long, AsignaturaRepository> {


   /*public List<Asignatura> saveAll(Iterable<Asignatura> list) {
       return this.repositorio.saveAll(list);
   }*/


}
