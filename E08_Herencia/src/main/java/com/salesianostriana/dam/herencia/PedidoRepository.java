package com.salesianostriana.dam.herencia;

import com.salesianostriana.dam.herencia.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
