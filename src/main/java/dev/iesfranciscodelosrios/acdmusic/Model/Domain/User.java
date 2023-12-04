package dev.iesfranciscodelosrios.acdmusic.Model.Domain;

import javax.persistence.*;
import java.util.Set;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table
public class User {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    protected int id;
    @Column(name = "NICKNAME")
    protected String nickName;
    @Column(name = "NAME")
    protected String name;
    @Column(name = "LASTNAME")
    protected String lastName;
    @Column(name = "PICTURE")
    protected String picture;
    @Column(name = "EMAIL")
    protected String email;
    @Column(name = "PASSWORD")
    private String password;
    @ManyToMany(mappedBy = "subscribedUsers")
    private Set<ReproductionList> subscribedLists;

    public User(int id, String nickName, String name, String lastName, String picture, String email, String password) {
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.picture = picture;
        this.email = email;
        this.password = password;
    }
    public User(String nickName, String name, String lastName, String picture, String email, String password) {
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.picture = picture;
        this.email = email;
        this.password = password;
    }
    public User() {
        this.id = -1;
        this.nickName = "";
        this.name = "";
        this.lastName = "";
        this.picture = "";
        this.email = "";
        this.password = "";
    }
    public User(int id, String nickName, String name, String lastName, String picture, String email){
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.picture = picture;
        this.email = email;
    }

    public Set<ReproductionList> getSubscribedLists() {
        return subscribedLists;
    }
    public void setSubscribedLists(Set<ReproductionList> subscribedLists) {
        this.subscribedLists = subscribedLists;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}