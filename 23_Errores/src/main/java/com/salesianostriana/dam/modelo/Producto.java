package com.salesianostriana.dam.modelo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

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

    private Double precio;

    @Lob
    private String descripcion;


}
