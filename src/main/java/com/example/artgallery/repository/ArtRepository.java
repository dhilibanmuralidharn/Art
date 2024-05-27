package com.example.artgallery.repository;

import com.example.artgallery.model.Art;
import com.example.artgallery.model.Artist;

import java.util.*;

public interface ArtRepository {
    ArrayList<Art> getArts();

    Art getArtById(int artId);

    Art addArt(Art art);

    Art updateArt(int artId, Art art);

    void deleteArt(int artId);

    Artist getArtArtist(int artId);
}
