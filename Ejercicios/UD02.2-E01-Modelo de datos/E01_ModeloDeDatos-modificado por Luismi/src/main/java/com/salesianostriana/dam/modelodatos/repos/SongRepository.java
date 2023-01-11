package com.salesianostriana.dam.modelodatos.repos;

import com.salesianostriana.dam.modelodatos.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
