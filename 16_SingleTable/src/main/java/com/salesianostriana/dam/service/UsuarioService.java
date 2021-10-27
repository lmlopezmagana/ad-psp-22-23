package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    EntityManager entityManager;


    public Optional<Usuario> findUsuarioByUsername(String username) {

        Optional<Usuario> result = Optional.empty();

        TypedQuery<Usuario> query =
                entityManager.createQuery("select u from Usuario u where u.username = :username",
                        Usuario.class);

        try {
            Optional.of(query.setParameter("username", username).getSingleResult());
        } catch (NoResultException | NonUniqueResultException ex) {
            result = Optional.empty();
        }

        return result;


    }

    public List<Usuario> findThemAll() {

        List<Usuario> result = null;

        TypedQuery<Usuario> query = entityManager.createQuery("select u from Usuario u", Usuario.class);

        try {
            result = query.getResultList();
        } catch (NoResultException e) {
            result = null;
        }

        return result;
    }

}
