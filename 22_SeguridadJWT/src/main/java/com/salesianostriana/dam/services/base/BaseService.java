package com.salesianostriana.dam.services.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID, R extends JpaRepository<T,ID>> {

    @Autowired
    protected R repositorio;

    public List<T> findAll() {
        return repositorio.findAll();
    }

    public Optional<T> findById(ID id) {
        return repositorio.findById(id);
    }

    public T save(T t) {
        return repositorio.save(t);
    }

    public T edit(T t) {
        return save(t);
    }

    public void delete(T t) {
        repositorio.delete(t);
    }

    public void deleteById(ID id) {
        repositorio.deleteById(id);
    }

}
