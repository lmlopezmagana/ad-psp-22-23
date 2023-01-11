package com.salesianostriana.dam.modelodatos.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@NamedEntityGraph(name = "playlist-with-songs",
        attributeNodes = {
                @NamedAttributeNode(value = "added",
                        subgraph = "added-with-songs")
        }, subgraphs = {
        @NamedSubgraph(name = "added-with-songs",
                attributeNodes = {
                        @NamedAttributeNode(value = "song")
                })
})
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String name, description;

    @OneToMany(mappedBy = "playlist")
    @Builder.Default
    @OrderColumn(name = "orden")
    private List<AddedTo> added = new ArrayList<>();

}
