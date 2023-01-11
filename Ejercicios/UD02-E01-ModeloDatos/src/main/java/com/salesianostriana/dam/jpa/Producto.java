package com.salesianostriana.dam.jpa;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Producto {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    private double pvp;

    @ManyToOne
    @JoinColumn(name = "categoria_id",
            foreignKey = @ForeignKey(name = "FK_PRODUCTO_CATEGORIA"))
    private Categoria categoria;


    // Helper de la asociación con Categoria

    public void addToCategoria(Categoria c) {
        categoria = c;
        c.getProductos().add(this);
    }

    public void removeFromCategoria(Categoria c) {
        categoria = null;
        c.getProductos().remove(this);
    }


}
