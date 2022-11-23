package com.salesianostriana.dam.dto.model;

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

    @ManyToOne
    private Categoria categoria;


    public Monumento(String codigoPais, String pais, String ciudad, String loc, String nombre, String descripcion, String urlImagen) {
        this.codigoPais = codigoPais;
        this.pais = pais;
        this.ciudad = ciudad;
        this.loc = loc;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
    }
}
