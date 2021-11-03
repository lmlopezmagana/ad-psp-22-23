package com.salesianostriana.dam.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Producto implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    private String descripcion;

    private double precio;

    @ManyToOne
    private Categoria categoria;

}
