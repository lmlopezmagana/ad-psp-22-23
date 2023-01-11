package com.salesianostriana.dam.modelodatos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class AddedTo {

    @EmbeddedId
    @Builder.Default
    private AddedToPK id = new AddedToPK();

    @ManyToOne
    @MapsId("song_id")
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @MapsId("playlist_id")
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    private LocalDateTime dateTime;

    private int orden;

    //////////////////////////////////////
    /* HELPERS de la asociación con Song*/
    //////////////////////////////////////

    public void addSong(Song s) {
        this.song = s;
        s.getAdded().add(this);
    }

    public void removeSong(Song s) {
        this.song = null;
        s.getAdded().remove(this);
    }

    //////////////////////////////////////////
    /* HELPERS de la asociación con Playlist*/
    //////////////////////////////////////////

    public void addToPlaylist(Playlist p) {
        this.playlist = p;
        p.getAdded().add(this);
    }

    public void removeFromPlaylist(Playlist p) {
        this.playlist = null;
        p.getAdded().remove(this);
    }

}
