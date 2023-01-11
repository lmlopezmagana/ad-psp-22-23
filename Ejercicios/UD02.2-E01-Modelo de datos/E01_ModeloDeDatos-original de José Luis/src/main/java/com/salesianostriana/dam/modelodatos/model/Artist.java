package com.salesianostriana.dam.modelodatos.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Artist {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "artist")
    @Builder.Default
    private List<Song> songs = new ArrayList<>();

}
