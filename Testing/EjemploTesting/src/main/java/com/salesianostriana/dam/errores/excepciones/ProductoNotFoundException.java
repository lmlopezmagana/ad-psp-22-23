package com.salesianostriana.dam.errores.excepciones;

public class ProductoNotFoundException extends EntityNotFoundException{
    public ProductoNotFoundException(Long id) {
        super(String.format("No se puede encontrar el producto con ID: %d", id));
    }
}
