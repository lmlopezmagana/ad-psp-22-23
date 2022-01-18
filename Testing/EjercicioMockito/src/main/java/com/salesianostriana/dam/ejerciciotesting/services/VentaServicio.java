package com.salesianostriana.dam.ejerciciotesting.services;

import com.salesianostriana.dam.ejerciciotesting.model.Cliente;
import com.salesianostriana.dam.ejerciciotesting.model.LineaDeVenta;
import com.salesianostriana.dam.ejerciciotesting.model.Producto;
import com.salesianostriana.dam.ejerciciotesting.model.Venta;
import com.salesianostriana.dam.ejerciciotesting.repos.ProductoRepositorio;
import com.salesianostriana.dam.ejerciciotesting.repos.VentaRepositorio;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VentaServicio {

    private final VentaRepositorio ventaRepositorio;
    private final ProductoRepositorio productoRepositorio;

    public Venta nuevaVenta(Map<String, Integer> productos, Cliente cliente) {

        // Creamos la venta
        Venta v = new Venta();
        v.setCliente(cliente);

        // Buscamos los productos y los añadimos como líneas de venta
       productos.keySet().stream()
                .map(codigo -> productoRepositorio.findOne(codigo))
                .map(producto -> new LineaDeVenta(producto, productos.get(producto.getCodigoProducto()), producto.getPrecio()))
                .forEach(v::addLineaVenta);

        return v;
    }


    public Venta addProductoToVenta(Long idVenta, String codigoProducto, int cantidad) {

        // Buscamos la venta

        Optional<Venta> optionalVenta = ventaRepositorio.findOneOptional(idVenta);

        if (optionalVenta.isPresent()) {

            Producto producto = productoRepositorio.findOne(codigoProducto);

            Venta venta = optionalVenta.get();
            venta.addLineaVenta(new LineaDeVenta(
                    producto,
                    cantidad,
                    producto.getPrecio()
            ));

            ventaRepositorio.edit(venta);

            return venta;
        }

        return null;


    }

    public Venta removeLineaVenta(Long idVenta, String codigoProducto) {

        Optional<Venta> optionalVenta = ventaRepositorio.findOneOptional(idVenta);

        if (optionalVenta.isPresent()) {

            Venta venta = optionalVenta.get();
            Optional<LineaDeVenta> optLineaDeVenta =
                    venta.getLineasDeVenta().stream().filter(lv -> lv.getProducto().getCodigoProducto() == codigoProducto)
                            .findFirst();


            venta.removeLineaVenta(optLineaDeVenta.get());

            ventaRepositorio.edit(venta);

            return venta;
        }

        return null;



    }


}
