package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ARTIST")
public class Artist extends User{
    private static final long serialVersionUID = 1L;
    @MapsId
    @Column(name = "ID_ARTIST")
    int id_artist;
    @Column(name = "NACIONALITY")
    String nacionality;
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<Album> albums;

    public Artist(int id, String nickName, String name, String lastName, String picture, String email, String password, int id_artist, String nacionality, int id_user, List<Album> albums) {
        super(id, nickName, name, lastName, picture, email, password);
        this.albums = albums;
        this.id_artist = super.id;
        this.nacionality = nacionality;

    }

    public Artist(String nickName, String name, String lastName, String picture, String email, String password, int id_artist, String nacionality, int id_user, List<Album> albums) {
        super(nickName, name, lastName, picture, email, password);
        this.id_artist = id_artist;
        this.nacionality = nacionality;
        this.albums = albums;
    }

    public Artist() {
    }

    public Artist(int id, String nickName, String name, String lastName, String picture, String email, int id_artist, String nacionality, int id_user, List<Album> albums) {
        super(id, nickName, name, lastName, picture, email);
        this.id_artist = id_artist;
        this.nacionality = nacionality;
        this.albums = albums;
    }

    public int getId_artist() {
        return id_artist;
    }

    public void setId_artist(int id_artist) {
        this.id_artist = id_artist;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return id_artist == artist.id_artist;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_artist);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id_artist=" + id_artist +
                ", nacionality='" + nacionality + '\'' +
                ", id=" + id +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture='" + picture + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}