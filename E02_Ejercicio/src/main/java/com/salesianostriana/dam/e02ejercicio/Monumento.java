package com.salesianostriana.dam.e02ejercicio;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
public class Monumento {

    @Id
    @GeneratedValue
    private Long id;

    private String codigoPais;

    private String pais;

    private String ciudad;

    private String loc;

    private String nombre;

    @Lob
    //@Column(length = 1000)
    //@Column(columnDefinition = "TEXT")
    private String descripcion;

    private String urlImagen;



}
