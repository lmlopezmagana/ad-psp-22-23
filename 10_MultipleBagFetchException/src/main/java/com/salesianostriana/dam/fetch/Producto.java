package com.salesianostriana.dam.fetch;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Producto {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    private double precio;

    @ManyToOne
    private Categoria categoria;

    @Builder.Default
    @OneToMany(mappedBy = "producto")
    //@OrderColumn(name="id")
    private List<ImagenProducto> imagenes = new ArrayList<>();
    //private Set<ImagenProducto> imagenes = new HashSet<>();
}
