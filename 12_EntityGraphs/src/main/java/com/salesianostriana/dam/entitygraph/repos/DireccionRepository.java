package com.salesianostriana.dam.entitygraph.repos;

import com.salesianostriana.dam.entitygraph.model.Direccion;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.QueryHint;
import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @EntityGraph("grafo-direccion-ciudad-usuario")
    List<Direccion> findAll();

    @EntityGraph("grafo-direccion-ciudad-usuario")
    List<Direccion> findByUsuarioId(Long id);

    @EntityGraph(value = "grafo-direccion-usuario", type = EntityGraph.EntityGraphType.LOAD)
    List<Direccion> findByIdNotNull();

}
