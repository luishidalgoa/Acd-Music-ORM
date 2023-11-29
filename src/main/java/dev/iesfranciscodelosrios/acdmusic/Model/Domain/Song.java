package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;
@Entity
@Table(name = "SONG")
public class Song implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_SONG")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALBUM")
    private Album album;
    @Column(name = "NAME")
    private String name;
    @Column(name = "URL")
    private String url;
    @Column(name = "LOCALTIME")
    private LocalTime length;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Column(name = "REPRODUCTIONS")
    int reproductions;



    public Song(int id, Album album, String name, String url, LocalTime length, Genre genre, int reproductions) {
        this.id = id;
        this.album = album;
        this.name = name;
        this.url = url;
        this.length = length;
        this.genre = genre;
        this.reproductions = reproductions;
    }

    public Song(Album album, String name, String url, LocalTime length, Genre genre, int reproductions) {
        this.album = album;
        this.name = name;
        this.url = url;
        this.length = length;
        this.genre = genre;
        this.reproductions = reproductions;
    }

    public Song(int id) {
        this.id = id;
    }

    public Song() {
    }


    public int getId_song() {
        return id;
    }

    public void setId_song(int id_song) {
        this.id = id_song;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalTime getTime() {
        return length;
    }

    public void setTime(LocalTime length) {
        this.length = length;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public LocalTime getLength() {
        return length;
    }

    public void setLength(LocalTime length) {
        this.length = length;
    }

    public int getReproductions() {
        return reproductions;
    }

    public void setReproductions(int reproductions) {
        this.reproductions = reproductions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id_song=" + id +
                ", album=" + album +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", length=" + length +
                ", genre=" + genre +
                ", reproductions=" + reproductions +
                '}';
    }

}
