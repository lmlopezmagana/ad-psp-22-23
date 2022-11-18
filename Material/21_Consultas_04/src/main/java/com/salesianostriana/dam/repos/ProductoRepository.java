package com.salesianostriana.dam.repos;


import com.salesianostriana.dam.dto.GetProductoConPrecioDto;
import com.salesianostriana.dam.dto.GetProductoDto;
import com.salesianostriana.dam.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("""
            select new com.salesianostriana.dam.dto.GetProductoDto(
                p.id, p.nombre, c.nombre
            )
            from Producto p LEFT JOIN p.categoria c
            """)
    List<GetProductoDto> todosLosProductosDto();

    @Query("""
            select new com.salesianostriana.dam.dto.GetProductoConPrecioDto(
                p.id, p.nombre, c.nombre, p.precio
            )
            from Producto p LEFT JOIN p.categoria c
            where p.precio <= :precio
            """)
    List<GetProductoConPrecioDto> precioMenorQue(@Param("precio") Double precio);

}
