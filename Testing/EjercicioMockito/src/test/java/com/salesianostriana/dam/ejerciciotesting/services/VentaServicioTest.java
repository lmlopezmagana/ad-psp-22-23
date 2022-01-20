package com.salesianostriana.dam.ejerciciotesting.services;

import com.salesianostriana.dam.ejerciciotesting.model.Cliente;
import com.salesianostriana.dam.ejerciciotesting.model.LineaDeVenta;
import com.salesianostriana.dam.ejerciciotesting.model.Producto;
import com.salesianostriana.dam.ejerciciotesting.model.Venta;
import com.salesianostriana.dam.ejerciciotesting.repos.ProductoRepositorio;
import com.salesianostriana.dam.ejerciciotesting.repos.VentaRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VentaServicioTest {

    @Mock
    ProductoRepositorio productoRepositorio;

    @Mock
    VentaRepositorio ventaRepositorio;

    @InjectMocks
    VentaServicio ventaServicio;


    @Test
    void whenNuevaVenta_success() {

        Producto p = Producto.builder()
                .codigoProducto("1")
                .nombre("Producto")
                .precio(12.34)
                .build();

        Cliente c = Cliente.builder()
                .nombre("Rompetechos")
                .email("rompetechos@ruedelpercebe13.com")
                .dni("12345678A")
                .build();

        lenient().when(productoRepositorio.findOne("1")).thenReturn(p);

        Map<String, Integer> carrito = Map.of("1", 2);

        Venta venta = new Venta();
        venta.setId(2L);
        venta.setCliente(c);
        venta.setLineasDeVenta(List.of(new LineaDeVenta(p, 2, 12.34)));

        lenient().when(ventaRepositorio.save(venta)).thenReturn(venta);

        assertEquals(venta, ventaServicio.nuevaVenta(carrito, c));

    }
}