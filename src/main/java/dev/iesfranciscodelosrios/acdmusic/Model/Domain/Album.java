package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "ALBUM")
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_ALBUM")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ARTIST")
    private User user;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DATE")
    private String date;
    @Column(name = "PICTURE")
    private String picture;
    @Column(name = "REPRODUCTIONS")
    private Integer reproductions;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Song> songs;


    public Album(int id, User user, String name, String date, String picture, Integer reproductions, List<Song> songs) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.date = date;
        this.picture = picture;
        this.reproductions = reproductions;
        this.songs = songs;
    }

    public Album(User user, String name, String date, String picture, Integer reproductions, List<Song> songs) {
        this.user = user;
        this.name = name;
        this.date = date;
        this.picture = picture;
        this.reproductions = reproductions;
        this.songs = songs;
    }

    public Album() {
        this.songs = new ArrayList<>();
    }

    public int getIdAlbum() {
        return id;
    }

    public void setIdAlbum(int idAlbum) {
        this.id = idAlbum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User artist) {
        this.user = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getReproductions() {
        return reproductions;
    }

    public void setReproductions(Integer reproductions) {
        this.reproductions = reproductions;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Album{" +
                "idAlbum=" + id +
                ", artist=" + user +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", picture='" + picture + '\'' +
                ", reproductions=" + reproductions +
                ", songs=" + songs +
                '}';
    }

}
