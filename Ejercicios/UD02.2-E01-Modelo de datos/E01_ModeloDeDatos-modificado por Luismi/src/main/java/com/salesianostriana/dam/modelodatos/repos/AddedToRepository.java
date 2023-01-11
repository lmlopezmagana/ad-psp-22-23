package com.salesianostriana.dam.modelodatos.repos;

import com.salesianostriana.dam.modelodatos.model.AddedTo;
import com.salesianostriana.dam.modelodatos.model.AddedToPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddedToRepository extends JpaRepository<AddedTo, AddedToPK> {
}
