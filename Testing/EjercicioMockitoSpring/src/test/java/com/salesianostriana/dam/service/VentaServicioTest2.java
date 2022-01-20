package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.model.LineaDeVenta;
import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.model.Venta;
import com.salesianostriana.dam.repos.ProductoRepositorio;
import com.salesianostriana.dam.repos.VentaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class VentaServicioTest2 {

    @MockBean
    ProductoRepositorio productoRepositorio;

    @MockBean
    VentaRepositorio ventaRepositorio;

    //@Autowired
    VentaServicio ventaServicio;

    /*@Autowired
    ApplicationContext applicationContext;*/

    @BeforeEach
    public void setUp() {
        ventaServicio = new VentaServicio(productoRepositorio, ventaRepositorio);
    }

    @Test
    void nuevaVenta() {

        //Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);

        Producto p = Producto.builder()
                .id(1L)
                .nombre("Producto")
                .precio(12.34)
                .build();

        Cliente c = Cliente.builder()
                .id(1L)
                .nombre("Rompetechos")
                .email("rompetechos@ruedelpercebe13.com")
                .dni("12345678A")
                .build();

        Map<Long, Integer> carrito = Map.of(1L, 3);

        Venta v = Venta.builder()
                .id(1L)
                .cliente(c)
                .lineasDeVenta(List.of(LineaDeVenta.builder()
                        .producto(p)
                        .cantidad(3)
                        .pvp(p.getPrecio())
                        .build()))
                .build();

        when(productoRepositorio.findById(any(Long.class))).thenReturn(Optional.of(p));

        when(ventaRepositorio.save(any(Venta.class))).thenReturn(v);

        Venta result = ventaServicio.nuevaVenta(carrito, c);

        assertEquals(1, result.getLineasDeVenta().size());
        assertEquals(12.34*3, result.getLineasDeVenta().stream().mapToDouble(l -> l.getPvp() * l.getCantidad()).sum());
    }


}
