package com.salesianostriana.dam.errores.excepciones;

import com.salesianostriana.dam.modelo.Producto;

public class SearchProductoNoResultException extends ListEntityNotFoundException {

    public SearchProductoNoResultException() {
        super(Producto.class);
    }
}
