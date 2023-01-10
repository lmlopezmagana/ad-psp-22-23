package com.salesianostriana.dam.fetch;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NamedEntityGraph
        (name="categoria-con-productos",
            attributeNodes = {
        @NamedAttributeNode(value = "productos",
                            subgraph = "imagenes-producto")
        }, subgraphs = {
                @NamedSubgraph(name="imagenes-producto",
                                attributeNodes = {
                                        @NamedAttributeNode("imagenes")
                                })
        })
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos = new ArrayList<>();
    //private Set<Producto> productos = new HashSet<>();

}
