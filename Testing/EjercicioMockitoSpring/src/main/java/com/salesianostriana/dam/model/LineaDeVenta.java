package com.salesianostriana.dam.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class LineaDeVenta {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Producto producto;

    private int cantidad;
    private double pvp; // precio unitario

    @ManyToOne
    private Venta venta;
}
