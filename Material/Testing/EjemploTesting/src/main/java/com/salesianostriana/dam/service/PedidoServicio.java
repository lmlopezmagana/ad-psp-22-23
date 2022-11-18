package com.salesianostriana.dam.service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.salesianostriana.dam.modelo.Pedido;
import com.salesianostriana.dam.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.dto.CreatePedidoDto;
import com.salesianostriana.dam.modelo.LineaPedido;
import com.salesianostriana.dam.modelo.Producto;
import com.salesianostriana.dam.repos.PedidoRepositorio;
import com.salesianostriana.dam.users.model.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServicio extends BaseService<Pedido, Long, PedidoRepositorio> {
	
	private final ProductoServicio productoServicio;
	
	public Pedido nuevoPedido(CreatePedidoDto nuevo, UserEntity cliente) {
		
		return save(Pedido.builder()
				.cliente(cliente)
				.lineas(nuevo.getLineas().stream()
							.map(lineaDto -> {
								
								Optional<Producto> p = productoServicio.findById(lineaDto.getProductoId());								
								if (p.isPresent()) {
									Producto producto = p.get();
									return LineaPedido.builder()
											.cantidad(lineaDto.getCantidad())
											.precio(producto.getPrecio())
											.producto(producto)
											.build();
								} else {
									return null;
								}
							})
							.filter(Objects::nonNull)
							.collect(Collectors.toSet())
						)							
				.build());
		
		
	}

	public Page<Pedido> findAllByUser(UserEntity user, Pageable pageable) {
		return repositorio.findByCliente(user, pageable);
	}

}
