package com.example.artgallery.service;

import com.example.artgallery.model.Art;
import com.example.artgallery.model.Artist;
import com.example.artgallery.model.Gallery;
import com.example.artgallery.repository.ArtJpaRepository;
import com.example.artgallery.repository.ArtistJpaRepository;
import com.example.artgallery.repository.ArtistRepository;
import com.example.artgallery.repository.GalleryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistJpaService implements ArtistRepository {
    @Autowired
    private ArtistJpaRepository artistJpaRepository;
    @Autowired
    private GalleryJpaRepository galleryJpaRepository;
    @Autowired
    private ArtJpaRepository artJpaRepository;

    @Override
    public ArrayList<Artist> getArtist() {
        List<Artist> artistList = artistJpaRepository.findAll();
        ArrayList<Artist> artists = new ArrayList<>(artistList);
        return artists;
    }

    @Override
    public Artist getArtistById(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist addArtist(Artist artist) {
        List<Integer> galleryIds = new ArrayList<>();
        for (Gallery gallery : artist.getGalleries()) {
            galleryIds.add(gallery.getGalleryId());
        }
        try {
            List<Gallery> completegallery = galleryJpaRepository.findAllById(galleryIds);
            if (galleryIds.size() != completegallery.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more gallery ids are invaild");
            }
            artist.setGalleries(completegallery);

            return artistJpaRepository.save(artist);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Artist updateArtist(int artistId, Artist artist) {
        try {
            Artist newArtist = artistJpaRepository.findById(artistId).get();
            if (artist.getArtistName() != null) {
                newArtist.setArtistName(artist.getArtistName());
            }
            if (artist.getGenre() != null) {
                newArtist.setGenre(artist.getGenre());
            }
            if (artist.getGalleries() != null) {
                newArtist.setGalleries(artist.getGalleries());
            }
            artistJpaRepository.save(newArtist);
            return newArtist;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArtist(int artistId) {
        try {
            artistJpaRepository.deleteById(artistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Gallery> getArtistGallery(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            return artist.getGalleries();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bad Request");
        }
    }

    @Override
    public List<Art> getArtistArt(int artistId) {
        try {
            Artist artist = artistJpaRepository.findById(artistId).get();
            List<Art> arts = artJpaRepository.findByArtist(artist);
            return arts;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bad Request");
        }
    }
}
