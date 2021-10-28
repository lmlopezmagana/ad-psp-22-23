package com.salesianostriana.dam.herencia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuperBuilder
@Getter @Setter
@MappedSuperclass
@NoArgsConstructor @AllArgsConstructor
public abstract class Producto {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    private float precio;

}
