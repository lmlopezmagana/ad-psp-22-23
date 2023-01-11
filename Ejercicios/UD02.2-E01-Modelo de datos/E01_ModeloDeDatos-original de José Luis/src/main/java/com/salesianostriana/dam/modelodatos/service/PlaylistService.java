package com.salesianostriana.dam.modelodatos.service;

import com.salesianostriana.dam.modelodatos.model.Playlist;
import com.salesianostriana.dam.modelodatos.repos.ArtistRepository;
import com.salesianostriana.dam.modelodatos.repos.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final ArtistRepository artistRepository;
    @Transactional
    public Optional<Playlist> findById(Long id) {
       Optional<Playlist> result = playlistRepository.findById(id);
       if(!result.isEmpty())
           artistRepository.findArtistOfASong();
       return result;
    }

    @Transactional
    public List<Playlist> findByArtistName(String name) {
        List<Playlist> result = playlistRepository.findByArtistName(name);
        if(!result.isEmpty())
            artistRepository.findArtistOfASong();
        return result;
    }

}
