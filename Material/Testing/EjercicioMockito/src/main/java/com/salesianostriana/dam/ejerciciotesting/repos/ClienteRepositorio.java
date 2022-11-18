package com.salesianostriana.dam.ejerciciotesting.repos;

import com.salesianostriana.dam.ejerciciotesting.model.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClienteRepositorio implements Repositorio<Cliente, String> {

    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public List<Cliente> findAll() {
        return Collections.unmodifiableList(clientes);
    }

    @Override
    public Cliente findOne(String s) {
        return findOneOptional(s).orElse(null);
    }

    @Override
    public Optional<Cliente> findOneOptional(String s) {
        return clientes.stream().filter(c-> c.getDni().contentEquals(s))
                .findFirst();
    }

    @Override
    public Cliente save(Cliente cliente) {
        clientes.add(cliente);
        return cliente;
    }

    @Override
    public Cliente edit(Cliente cliente) {
        clientes.removeIf(c -> c.getDni() == cliente.getDni());
        clientes.add(cliente);
        return cliente;
    }

    @Override
    public void delete(Cliente cliente) {
        clientes.removeIf(c -> c.getDni() == cliente.getDni());
    }

    @Override
    public void deleteById(String s) {
        clientes.removeIf(c -> c.getDni() == s);
    }
}
