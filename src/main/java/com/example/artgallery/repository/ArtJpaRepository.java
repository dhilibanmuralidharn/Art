package com.example.artgallery.repository;

import com.example.artgallery.model.Art;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.artgallery.model.Artist;
import java.util.List;

@Repository
public interface ArtJpaRepository extends JpaRepository<Art, Integer> {
    List<Art> findByArtist(Artist artist);
}
