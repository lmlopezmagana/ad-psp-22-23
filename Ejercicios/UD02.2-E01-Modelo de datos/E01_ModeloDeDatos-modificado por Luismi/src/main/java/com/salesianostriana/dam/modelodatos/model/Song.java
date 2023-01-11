package com.salesianostriana.dam.modelodatos.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Song {

    @Id @GeneratedValue
    private Long id;

    private String title, album;

    @Column(name = "year_of_song")
    private String year;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "artist_id", foreignKey = @ForeignKey(name = "FK_SONG_ARTIST"))
    private Artist artist;

    @OneToMany(mappedBy = "song")
    @Builder.Default
    //private List<AddedTo> added = new ArrayList<>();
    private Set<AddedTo> added = new HashSet<>();

    ////////////////////////////////////////
    /* HELPERS de la asociación con Artist*/
    ////////////////////////////////////////

    public void addToArtist(Artist a) {
        this.artist = a;
        a.getSongs().add(this);
    }

    public void removeFromArtist(Artist a) {
        this.artist = null;
        a.getSongs().remove(this);
    }

}
