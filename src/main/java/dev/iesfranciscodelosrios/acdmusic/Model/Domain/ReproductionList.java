package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;

import java.util.*;
import javax.persistence.*;

@Entity
@Table
public class ReproductionList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reproductionList")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "reproductionsonglist",
            joinColumns = @JoinColumn(name = "id_lista"),
            inverseJoinColumns = @JoinColumn(name = "id_cancion")
    )
    private Set<Song> songs;

    @OneToMany(mappedBy = "reproductionList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "usersubscriptionlist",
            joinColumns = @JoinColumn(name = "id_reproductionlist"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Set<User> subscribedUsers;




    public ReproductionList(int id, String name, String description, UserDTO owner, Set<Song> songs, Set<Comment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = UserDTO.toUser(owner);
        this.songs = songs;
        this.comments = comments;
    }

    public ReproductionList(String name, String description, UserDTO owner, Set<Song> songs, Set<Comment> comments) {
        this.name = name;
        this.description = description;
        this.owner = UserDTO.toUser(owner);
        this.songs = songs;
        this.comments = comments;
    }

    public ReproductionList() {
        this.id = -1;
        this.name = "";
        this.description = "";
        this.owner = null;
        this.songs = null;
        this.comments = null;
    }

    @Override
    public String toString() {
        return "ReproductionList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", Songs=" + songs +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReproductionList that = (ReproductionList) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDTO getOwner() {
        return new UserDTO(owner);
    }

    public void setOwner(UserDTO owner) {
        this.owner = UserDTO.toUser(owner);
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(Set<User> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }
}
