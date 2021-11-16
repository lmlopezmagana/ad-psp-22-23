package com.salesianostriana.dam.services;

import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.repos.ProductoRepository;
import com.salesianostriana.dam.services.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ProductoService extends BaseService<Producto, Long, ProductoRepository> {
}
