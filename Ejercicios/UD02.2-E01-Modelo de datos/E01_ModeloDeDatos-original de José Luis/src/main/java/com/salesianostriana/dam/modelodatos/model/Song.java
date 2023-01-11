package com.salesianostriana.dam.modelodatos.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "artist_id", foreignKey = @ForeignKey(name = "FK_SONG_ARTIST"))
    private Artist artist;

    @OneToMany(mappedBy = "song")
    @Builder.Default
    private List<AddedTo> added = new ArrayList<>();

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
