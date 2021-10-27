package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c where TYPE(c) = Cliente")
    public List<Cliente> clientesNoVip();
}
