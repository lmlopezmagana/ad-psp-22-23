package com.salesianostriana.dam.modelodatos.repos;

import com.salesianostriana.dam.modelodatos.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("""
            select distinct a from Artist a
            left join fetch a.songs
            """)
    List<Artist> findArtistOfASong();

}
