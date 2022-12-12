package com.salesianostriana.dam.jpa;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class Categoria {

    @Id @GeneratedValue
    private Long id;

    private String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER)
    //@Fetch(FetchMode.JOIN)
    private List<Producto> productos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "categoria_padre", foreignKey =
    @ForeignKey(name = "FK_CATEGORIA_CATEGORIA"))
    private Categoria categoriaPadre;

    @Builder.Default
    @OneToMany(mappedBy = "categoriaPadre", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<Categoria> categoriasHijas = new ArrayList<>();

    // Helpers de Categoria Padre - Categoria Hija

    public void addToCategoriaPadre(Categoria padre) {
        categoriaPadre = padre;
        padre.getCategoriasHijas().add(this);
    }

    public void removeFromCategoriaPadre(Categoria padre) {
        categoriaPadre = null;
        padre.getCategoriasHijas().remove(this);
    }

    // Si la opción es SET NULL
    //@PreRemove
    public void setNullCategoriasHijas() {
        categoriasHijas.forEach(c -> c.setCategoriaPadre(null));
    }


}
