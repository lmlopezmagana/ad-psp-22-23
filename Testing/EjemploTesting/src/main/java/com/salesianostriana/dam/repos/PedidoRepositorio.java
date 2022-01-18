package com.salesianostriana.dam.repos;

import com.salesianostriana.dam.modelo.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.users.model.UserEntity;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
	
	Page<Pedido> findByCliente(UserEntity cliente, Pageable pageable);
	
	

}
