package com.salesianostriana.dam.ejerciciotesting.repos;

import com.salesianostriana.dam.ejerciciotesting.model.Producto;
import com.salesianostriana.dam.ejerciciotesting.model.Venta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VentaRepositorio implements Repositorio<Venta, Long> {

    private List<Venta> ventas = new ArrayList<>();

    @Override
    public List<Venta> findAll() {
        return Collections.unmodifiableList(ventas);
    }

    @Override
    public Venta findOne(Long id) {
        return findOneOptional(id).orElse(null);
    }

    @Override
    public Optional<Venta> findOneOptional(Long id) {
        return ventas.stream().filter(v-> v.getId() == id)
                .findFirst();
    }

    @Override
    public Venta save(Venta venta) {
        ventas.add(venta);
        return venta;
    }

    @Override
    public Venta edit(Venta venta) {
        ventas.removeIf(v -> v.getId() == venta.getId());
        ventas.add(venta);
        return venta;
    }

    @Override
    public void delete(Venta venta) {
        ventas.removeIf(v -> v.getId() == venta.getId());
    }

    @Override
    public void deleteById(Long id) {
        ventas.removeIf(v -> v.getId() == id);
    }
}
