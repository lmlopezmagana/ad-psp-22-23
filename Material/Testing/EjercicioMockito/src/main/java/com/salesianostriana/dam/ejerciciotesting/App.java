package com.salesianostriana.dam.ejerciciotesting;

import com.salesianostriana.dam.ejerciciotesting.model.Cliente;
import com.salesianostriana.dam.ejerciciotesting.model.Producto;
import com.salesianostriana.dam.ejerciciotesting.model.Venta;
import com.salesianostriana.dam.ejerciciotesting.repos.ClienteRepositorio;
import com.salesianostriana.dam.ejerciciotesting.repos.ProductoRepositorio;
import com.salesianostriana.dam.ejerciciotesting.repos.VentaRepositorio;
import com.salesianostriana.dam.ejerciciotesting.services.VentaServicio;

import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        ClienteRepositorio clienteRepositorio = new ClienteRepositorio();
        ProductoRepositorio productoRepositorio = new ProductoRepositorio();
        VentaRepositorio ventaRepositorio = new VentaRepositorio();

        VentaServicio ventaServicio = new VentaServicio(ventaRepositorio, productoRepositorio);


        Cliente c = Cliente.builder()
                .dni("12345A")
                .nombre("Pepe Pérez")
                .email("pepeperez@email.com")
                .build();

        clienteRepositorio.save(c);

        List<Producto> productos = List.of(
          new Producto("1", "Ordenador portátil", 699.0),
          new Producto("2", "Ordenador de sobremesa", 499.0),
          new Producto("3", "Teclado mecánico retroiluminado", 59.99),
          new Producto("4", "Ratón 3 botones", 19.99)
        );

        productos.forEach(productoRepositorio::save);


        Venta v = ventaServicio.nuevaVenta(Map.of("2", 10, "3", 10, "4", 10), c);

        System.out.println(v);

    }

}
