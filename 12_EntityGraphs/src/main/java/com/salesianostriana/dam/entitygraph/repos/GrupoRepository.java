package com.salesianostriana.dam.entitygraph.repos;

import com.salesianostriana.dam.entitygraph.model.Grupo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {


    @EntityGraph("grafo-con-usuario-y-subgrafos")
    List<Grupo> findAll();

    @EntityGraph(attributePaths = {"usuarios"})
    List<Grupo> findByNombre(String nombre);

}
