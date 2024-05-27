package com.example.artgallery.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import com.example.artgallery.model.Artist;

import java.util.List;

@Entity
@Table(name = "gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int galleryId;
    @Column(name = "name")
    private String galleryName;
    @Column(name = "location")
    private String location;

    @ManyToMany(mappedBy = "galleries")
    @JsonIgnoreProperties("galleries")
    private List<Artist> artists;

    public Gallery() {
    }

    public Gallery(int galleryId, String galleryName, String location, List<Artist> artists) {
        this.galleryId = galleryId;
        this.galleryName = galleryName;
        this.location = location;
        this.artists = artists;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
