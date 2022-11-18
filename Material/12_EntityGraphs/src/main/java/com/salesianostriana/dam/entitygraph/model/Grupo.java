package com.salesianostriana.dam.entitygraph.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "grafo-con-usuario-y-subgrafos",
                attributeNodes = {
                        @NamedAttributeNode( value = "usuarios", subgraph = "subgrafo-usuario") },
                subgraphs = {
                        @NamedSubgraph(
                                name = "subgrafo-usuario",
                                attributeNodes = { @NamedAttributeNode( value = "direcciones", subgraph = "subgrafo-direcciones") }
                        ),
                        @NamedSubgraph(
                                name = "subgrafo-direcciones",
                                attributeNodes = {
                                        @NamedAttributeNode("ciudad")
                                }
                        )
                }
        )
)
@Entity
@NoArgsConstructor  @AllArgsConstructor @Builder
@Getter @Setter
public class Grupo {


    @Id
    @GeneratedValue
    private Long id;

    String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    //private List<Usuario> usuarios = new ArrayList<>(); // Provoca MultipleFetchBagException
    // Esta no es una "buena" solución según algunos autores, porque provoca
    // que se produzca un producto cartesiano que, con pocas filas a mezclar,
    // puede provocar una consulta que afecte a decenas o cientos de miles de filas.
    Set<Usuario> usuarios = new HashSet<>();


}
