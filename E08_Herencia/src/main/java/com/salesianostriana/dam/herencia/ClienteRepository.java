package com.salesianostriana.dam.herencia;

import com.salesianostriana.dam.herencia.model.Cliente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    @Query("select c from Cliente c WHERE TYPE(c) != ClienteCorporativo and TYPE(c) != ClienteParticular")
    List<Cliente> listarClientesNormales();

    @Query("select c from Cliente c WHERE TYPE(c) = ClienteCorporativo")
    List<Cliente> listarClientesCorporativo();


    @EntityGraph(Cliente.CLIENTE_PEDIDO)
    Optional<Cliente> findById(Long id);

}
