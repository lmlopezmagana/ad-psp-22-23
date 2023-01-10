package com.salesianostriana.dam.fetch;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

    @Query("""
            select c from Categoria c
            left join fetch c.productos
            where c.id = :id
            """)
    Optional<Categoria> categoriaConProductos(Long id);

    @Query("""
            select distinct c from Categoria c
            left join fetch c.productos
            """)
    List<Categoria> categoriasConProductos();


    @EntityGraph("categoria-con-productos")
    List<Categoria> findDistinctByNombre(String nombre);

    @EntityGraph("categoria-con-productos")
    Optional<Categoria> findById(Long id);

    /*
    @Query("""
            select distinct c from Categoria c
            left join fetch c.productos p
            left join fetch p.imagenes
            """)
    List<Categoria> findTodoDeTodo();
    */

    @Query("""
            select distinct c from Categoria c
            left join fetch c.productos
            """)
    List<Categoria> categoriaConProductos();



}
