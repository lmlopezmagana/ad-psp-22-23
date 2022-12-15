package com.salesianostriana.dam.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {


    List<ProductoDto> findAllConDtoBy();

    List<ProductoDto2> findAllConProyeccionBy();



}
