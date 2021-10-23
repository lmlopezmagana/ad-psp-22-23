package com.salesianostriana.dam.entitygraph.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, email, password, telefono;

    @Builder.Default
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Direccion> direcciones = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Grupo grupo;


    public void addToGrupo(Grupo g) {
        grupo = g;
        g.getUsuarios().add(this);
    }

    public void removeFromGrupo(Grupo g) {
        grupo = null;
        g.getUsuarios().remove(this);
    }


}
