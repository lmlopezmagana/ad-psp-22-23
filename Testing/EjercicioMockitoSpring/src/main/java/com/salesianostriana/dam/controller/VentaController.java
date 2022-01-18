package com.salesianostriana.dam.controller;

import com.salesianostriana.dam.dto.CreateVentaDto;
import com.salesianostriana.dam.dto.GetVentaDto;
import com.salesianostriana.dam.dto.ProductoCantidadDto;
import com.salesianostriana.dam.dto.VentaConverter;
import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.model.Venta;
import com.salesianostriana.dam.repos.ClienteRepositorio;
import com.salesianostriana.dam.service.VentaServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VentaController {

    private final VentaServicio ventaServicio;
    private final ClienteRepositorio clienteRepositorio;
    private final VentaConverter ventaConverter;

    @PostMapping
    public ResponseEntity<GetVentaDto> nuevaVenta(CreateVentaDto createVentaDto) {

        Cliente cliente = clienteRepositorio.findById(createVentaDto.getClienteId()).get();

        Map<Long, Integer> productos = createVentaDto.getProductos().stream()
                .collect(Collectors.toMap(ProductoCantidadDto::getIdProducto, ProductoCantidadDto::getCantidad));


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaConverter.ventaToGetVentaDto(ventaServicio.nuevaVenta(productos, cliente)));
        

    }



}
