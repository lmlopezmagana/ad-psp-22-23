package com.salesianostriana.dam.ejerciciotesting.repos;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T, ID> {

    public List<T> findAll();

    public T findOne(ID id);

    public Optional<T> findOneOptional(ID id);

    public T save(T t);

    public T edit(T t);

    public void delete(T t);

    public void deleteById(ID id);


}
