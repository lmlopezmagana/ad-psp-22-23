package com.salesianostriana.dam.modelodatos;

import com.salesianostriana.dam.modelodatos.model.Playlist;
import com.salesianostriana.dam.modelodatos.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final PlaylistService playlistService;

    @PostConstruct
    public void init() {

        //PARTE 1
        imprimirUnaPlaylist(playlistService.findById(13L).get());

        //PARTE 2
        playlistService.findByArtistName("Metallica").forEach(this::imprimirPlaylistDeUnArtista);
    }

    public void imprimirUnaPlaylist(Playlist playlist) {
        System.out.println("Playlist: " + playlist.getName());
        System.out.println("=================");
        playlist.getAdded().forEach(addedTo -> {
            if(addedTo != null)
                System.out.println("Canción Nº" + addedTo.getOrden() + ": "
                        + addedTo.getSong().getTitle() + " Artista: " + addedTo.getSong().getArtist().getName());
        });
        System.out.println("\n");
    }

    public void imprimirPlaylistDeUnArtista(Playlist playlist) {
        System.out.println("Playlist: " + playlist.getName());
        System.out.println("=================");
        playlist.getAdded().forEach(addedTo -> {
            if(addedTo != null)
                System.out.println("Canción Nº" + addedTo.getOrden() + ": "
                        + addedTo.getSong().getTitle() + " Artista: " + addedTo.getSong().getArtist().getName());
        });
        System.out.println("\n");
    }

}
