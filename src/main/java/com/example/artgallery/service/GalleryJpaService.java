package com.example.artgallery.service;


import com.example.artgallery.model.Artist;
import com.example.artgallery.model.Gallery;
import com.example.artgallery.repository.ArtistJpaRepository;
import com.example.artgallery.repository.GalleryJpaRepository;
import com.example.artgallery.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryJpaService implements GalleryRepository {
    @Autowired
    private GalleryJpaRepository galleryJpaRepository;
    @Autowired
    private ArtistJpaRepository artistJpaRepository;
    @Override
    public ArrayList<Gallery> getGalleries() {
        List<Gallery> galleryList = galleryJpaRepository.findAll();
        ArrayList<Gallery> galleries = new ArrayList<>(galleryList);
        return galleries;
    }

    @Override
    public Gallery getGalleryById(int galleryId) {
        try {
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            return gallery;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Gallery addGallery(Gallery gallery) {
        List<Integer> artistIds = new ArrayList<>();
        for (Artist artist : gallery.getArtists()){
            artistIds.add(artist.getArtistId());
        }
        try{
            List<Artist> completeArtists = artistJpaRepository.findAllById(artistIds);
            if (artistIds.size() != completeArtists.size()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "one or more artistid are invalid");
            }
            gallery.setArtists(completeArtists);
            return galleryJpaRepository.save(gallery);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Gallery updateGallery(int galleryId, Gallery gallery) {
        try{
            Gallery newGallery = galleryJpaRepository.findById(galleryId).get();
            if (gallery.getGalleryName() != null){
                newGallery.setGalleryName(gallery.getGalleryName());
            }
            if (gallery.getLocation() != null){
                newGallery.setLocation(gallery.getLocation());
            }
            if (gallery.getArtists() != null){
                newGallery.setArtists(gallery.getArtists());
            }
            galleryJpaRepository.save(newGallery);
            return newGallery;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteGallery(int galleryId) {
        try{
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            List <Artist> artists = gallery.getArtists();
            for (Artist artist : artists){
                artist.getGalleries().remove(gallery);
            }
            artistJpaRepository.saveAll(artists);

            galleryJpaRepository.deleteById(galleryId);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Artist> getGalleryArtist(int galleryId) {
        try{
            Gallery gallery = galleryJpaRepository.findById(galleryId).get();
            return  gallery.getArtists();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bad Request");
        }
    }
}
