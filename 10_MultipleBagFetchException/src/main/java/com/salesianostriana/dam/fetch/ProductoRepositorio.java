package com.salesianostriana.dam.fetch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {



    @Query("""
            select distinct p from Producto p
            left join fetch p.imagenes
            """)
    List<Producto> productosConImagenes();

}
