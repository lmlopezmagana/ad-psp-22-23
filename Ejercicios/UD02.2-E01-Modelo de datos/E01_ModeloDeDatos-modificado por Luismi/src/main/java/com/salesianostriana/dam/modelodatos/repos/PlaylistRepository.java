package com.salesianostriana.dam.modelodatos.repos;

import com.salesianostriana.dam.modelodatos.model.Playlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    //@EntityGraph(value = "playlist-with-songs")
    @EntityGraph(value = "playlist-with-songs",
            type = EntityGraph.EntityGraphType.LOAD)
    Optional<Playlist> findById(Long id);

    @Query("""
            select distinct p from Playlist p
            left join fetch p.added a
            left join fetch a.song s
            where s.artist.name = ?1
            """)
    List<Playlist> findByArtistName(String name);
}
