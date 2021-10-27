package com.salesianostriana.dam.service.base;

import com.salesianostriana.dam.model.Usuario;
import com.salesianostriana.dam.repos.BaseUsuarioRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseUsuarioService<T extends Usuario, R extends BaseUsuarioRepository<T>> extends BaseService<T, Long, R>{

    public T findByUsername(String username) {
        return repositorio.findByUsername(username);
    }

}
