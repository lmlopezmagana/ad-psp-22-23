package com.salesianostriana.dam.service;

import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.model.LineaDeVenta;
import com.salesianostriana.dam.model.Producto;
import com.salesianostriana.dam.model.Venta;
import com.salesianostriana.dam.repos.ProductoRepositorio;
import com.salesianostriana.dam.repos.VentaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaServicio {

    private final ProductoRepositorio productoRepositorio;
    private final VentaRepositorio ventaRepositorio;

    public Venta nuevaVenta(Map<Long, Integer> productos, Cliente cliente) {

        // Creamos la venta
        Venta v = new Venta();
        v.setCliente(cliente);

        // Buscamos los productos y los añadimos como líneas de venta
        productos.keySet().stream()
                .map(id -> productoRepositorio.findById(id).get())
                .map(producto -> LineaDeVenta.builder()
                        .producto(producto)
                        .pvp(producto.getPrecio())
                        .cantidad(productos.get(producto.getId()))
                        .build()
                )
                .forEach(v::addLineaVenta);

        ventaRepositorio.save(v);
        return v;
    }

    public Venta addProductoToVenta(Long idVenta, Long codigoProducto, int cantidad) {

        // Buscamos la venta

        Optional<Venta> optionalVenta = ventaRepositorio.findById(idVenta);

        if (optionalVenta.isPresent()) {

            Producto producto = productoRepositorio.findById(codigoProducto).get();

            Venta venta = optionalVenta.get();

            venta.addLineaVenta(
                    LineaDeVenta.builder()
                            .producto(producto)
                            .cantidad(cantidad)
                            .pvp(producto.getPrecio())
                            .build()
            );

            ventaRepositorio.save(venta);

            return venta;
        }

        return null;


    }

    public Venta removeLineaVenta(Long idVenta, Long idProducto) {

        Optional<Venta> optionalVenta = ventaRepositorio.findById(idVenta);

        if (optionalVenta.isPresent()) {

            Venta venta = optionalVenta.get();
            Optional<LineaDeVenta> optLineaDeVenta =
                    venta.getLineasDeVenta().stream().filter(lv -> lv.getProducto().getId() == idProducto)
                            .findFirst();


            venta.removeLineaVenta(optLineaDeVenta.get());

            ventaRepositorio.save(venta);

            return venta;
        }

        return null;


    }
}