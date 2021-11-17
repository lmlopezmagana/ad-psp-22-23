package com.salesianostriana.dam.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private double precio;

}
