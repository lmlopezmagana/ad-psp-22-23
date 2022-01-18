package com.salesianostriana.dam.dto;

import com.salesianostriana.dam.model.LineaDeVenta;
import com.salesianostriana.dam.model.Venta;

import java.util.List;
import java.util.stream.Collectors;

public class VentaConverter {

    public GetProductoCantidadPrecioDto lineaDeVentaToGetProductoCantidadPrecioDto(LineaDeVenta lv) {

        return GetProductoCantidadPrecioDto.builder()
                .producto(lv.getProducto().getNombre())
                .cantidad(lv.getCantidad())
                .pvp(lv.getPvp())
                .subtotal(lv.getCantidad()*lv.getPvp())
                .build();


    }

    public GetVentaDto ventaToGetVentaDto(Venta venta) {

        List<GetProductoCantidadPrecioDto> productos =  venta.getLineasDeVenta()
                .stream()
                .map(this::lineaDeVentaToGetProductoCantidadPrecioDto).collect(Collectors.toList());


        return GetVentaDto.builder()
                .nombreCliente(venta.getCliente().getNombre())
                .fecha(venta.getFecha())
                .productos(productos)
                .total(productos.stream().mapToDouble(GetProductoCantidadPrecioDto::getSubtotal).sum())
                .build();
    }


}
