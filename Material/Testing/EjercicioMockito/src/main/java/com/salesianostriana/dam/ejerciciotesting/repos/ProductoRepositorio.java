package com.salesianostriana.dam.ejerciciotesting.repos;

import com.salesianostriana.dam.ejerciciotesting.model.Cliente;
import com.salesianostriana.dam.ejerciciotesting.model.Producto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductoRepositorio implements Repositorio<Producto, String> {

    private List<Producto> productos = new ArrayList<>();

    @Override
    public List<Producto> findAll() {
        return Collections.unmodifiableList(productos);
    }

    @Override
    public Producto findOne(String s) {
        return findOneOptional(s).orElse(null);
    }

    @Override
    public Optional<Producto> findOneOptional(String s) {
        return productos.stream().filter(c-> c.getCodigoProducto().contentEquals(s))
                .findFirst();
    }

    @Override
    public Producto save(Producto producto) {
        productos.add(producto);
        return producto;
    }

    @Override
    public Producto edit(Producto producto) {
        productos.removeIf(c -> c.getCodigoProducto() == producto.getCodigoProducto());
        productos.add(producto);
        return producto;
    }

    @Override
    public void delete(Producto producto) {
        productos.removeIf(c -> c.getCodigoProducto() == producto.getCodigoProducto());
    }

    @Override
    public void deleteById(String s) {
        productos.removeIf(c -> c.getCodigoProducto() == s);
    }
}
