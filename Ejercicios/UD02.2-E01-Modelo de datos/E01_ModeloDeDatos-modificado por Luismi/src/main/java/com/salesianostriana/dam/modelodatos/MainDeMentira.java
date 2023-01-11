package com.salesianostriana.dam.modelodatos;

import com.salesianostriana.dam.modelodatos.model.Artist;
import com.salesianostriana.dam.modelodatos.model.Playlist;
import com.salesianostriana.dam.modelodatos.model.Song;
import com.salesianostriana.dam.modelodatos.repos.ArtistRepository;
import com.salesianostriana.dam.modelodatos.repos.SongRepository;
import com.salesianostriana.dam.modelodatos.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MainDeMentira {

    private final PlaylistService playlistService;

    private final SongRepository songRepository;

    private final ArtistRepository artistRepository;

    @PostConstruct
    public void init() {

        //PARTE 1
        //imprimirUnaPlaylist(playlistService.findById(13L).get());

        //PARTE 2
        //playlistService.findByArtistName("Metallica").forEach(this::imprimirPlaylistDeUnArtista);


        //songRepository.findById(4L);

        Optional<Artist> result = artistRepository.findFirstByName("Metallica");

        Artist metallica = result.get();

        //.flatMap(List::stream)
        metallica.getSongs()
                .stream()
                .map(Song::getAdded)
                .flatMap(Set::stream)
                .forEach(System.out::println);




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
