package com.salesianostriana.dam.ejerciciotesting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Venta {

    private static long numeroDeVentas = 1L;

    @Builder.Default
    private Long id = numeroDeVentas++;

    @Builder.Default
    private LocalDate fecha = LocalDate.now();

    @Builder.Default
    private List<LineaDeVenta> lineasDeVenta = new ArrayList<>();

    private Cliente cliente;

    public Venta() {
        id = numeroDeVentas++;
        fecha = LocalDate.now();
        lineasDeVenta = new ArrayList<>();
    }

    public Venta(Long id, LocalDate fecha, List<LineaDeVenta> lineasDeVenta, Cliente cliente) {
        this.id = id;
        this.fecha = fecha;
        this.lineasDeVenta = lineasDeVenta;
        this.cliente = cliente;
    }

    public void addLineaVenta(LineaDeVenta lineaDeVenta) {
        lineasDeVenta.add(lineaDeVenta);
    }

    public void removeLineaVenta(LineaDeVenta lineaDeVenta) {
        lineasDeVenta.remove(lineaDeVenta);
    }
}
