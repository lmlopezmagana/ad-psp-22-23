package com.salesianostriana.dam.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Producto {

    @Id @GeneratedValue
    private Long id;

    private String codigoProducto;
    private String nombre;
    private double precio;

}
