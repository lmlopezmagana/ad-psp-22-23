package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseUsuarioRepository<T extends Usuario> extends JpaRepository<T, Long> {

    public T findByUsername(String Username);

}
