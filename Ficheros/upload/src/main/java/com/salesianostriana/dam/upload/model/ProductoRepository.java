package com.salesianostriana.dam.upload.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends
        JpaRepository<Producto, Long> {
}
