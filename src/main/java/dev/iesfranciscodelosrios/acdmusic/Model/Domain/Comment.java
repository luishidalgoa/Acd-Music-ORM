package dev.iesfranciscodelosrios.acdmusic.Model.Domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentlistusers")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comment")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_reproductionlist")
    private ReproductionList reproductionList;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "description")
    private String description;

    public Comment() {
    }

    public Comment(User user, ReproductionList reproductionList, LocalDateTime date, String description) {
        this.user = user;
        this.reproductionList = reproductionList;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReproductionList getReproductionListId() {
        return reproductionList;
    }

    public void setReproductionListId(ReproductionList reproductionList) {
        this.reproductionList = reproductionList;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}