package com.salesianostriana.dam.modelodatos.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@NamedEntityGraph(name="artist-with-songs",
    attributeNodes = {
      @NamedAttributeNode(value = "songs",
        subgraph = "added")
    }, subgraphs ={
            @NamedSubgraph(
              name = "added",
              attributeNodes = {
                      @NamedAttributeNode("added")
              })
    })
public class Artist {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "artist")
    @Builder.Default
    private List<Song> songs = new ArrayList<>();

}
