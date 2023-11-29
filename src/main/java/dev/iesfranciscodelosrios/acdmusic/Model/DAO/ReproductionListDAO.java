package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iReproductionListDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.DTO.UserDTO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Comment;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.ReproductionList;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;
import dev.iesfranciscodelosrios.acdmusic.Services.Login;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ReproductionListDAO implements iReproductionListDAO {
    private static ReproductionListDAO instance;
    String addQuery = "INSERT INTO rythm.reproductionlist (name,description,id_user) VALUES (?,?,?);";
    String removeQuery = "DELETE FROM rythm.reproductionlist WHERE id_reproductionList=?";
    String searchByIdQuery = "SELECT r.id_reproductionList, r.id_user, r.name, r.description FROM rythm.reproductionlist r WHERE r.id_reproductionList=?";
    String SubcribeQuery = "INSERT INTO rythm.usersubscriptionlist (id_user, id_reproductionList) VALUES (?,?);";
    String getUserSubcriptionQuery = "SELECT id_user,id_reproductionList FROM rythm.usersubscriptionlist WHERE id_user=?;";
    String getSubcribeToListByUserQuery = "SELECT id_user,id_reproductionList FROM rythm.usersubscriptionlist WHERE id_user=? AND id_reproductionList=?;";
    String addSongQuery = "INSERT INTO rythm.reproductionsonglist (id_song, id_reproductionList) VALUES (?,?);";
    String searchSonsByIdQuery = "select s.id_song, s.id_album, s.name, s.url, s.lenght, s.genre, s.reproductions from reproductionsonglist rsl JOIN song s on rsl.id_song = s.id_song where rsl.id_reproductionList = ?;";
    String unsubscribeQuery = "DELETE FROM rythm.usersubscriptionlist WHERE id_user=? AND id_reproductionList=?;";
    String searchAllCommentsQuery = "SELECT c.id_comment FROM reproductionlist JOIN commentlistusers c on reproductionlist.id_reproductionList = c.id_reproductionList WHERE c.id_reproductionList LIKE ?";
    String removeSongQuery = "DELETE FROM rythm.reproductionsonglist WHERE id_song=? AND id_reproductionList=?;";
    String searchSongOnList = "SELECT id_song,id_reproductionList FROM rythm.reproductionsonglist WHERE id_song=? AND id_reproductionList=?;";
    //Hazme un Like que devuelva varias Listas con un nombre parecido
    String searchByNameQuery = "SELECT id_reproductionList FROM rythm.reproductionlist WHERE name LIKE CONCAT('%',?,'%') LIMIT 6";
    private ReproductionListDAO() {
    }

    @Override
    public ReproductionList add(ReproductionList reproductionList) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(reproductionList);
        manager.getTransaction().commit(); // Hacemos commit para que se guarde en la base de datos
        manager.close();
        return reproductionList;
    }

    @Override
    public boolean removeReproductionList(int id) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        ReproductionList reproductionList = manager.find(ReproductionList.class, id);
        if (reproductionList != null) {
            manager.remove(reproductionList);
            manager.getTransaction().commit(); // Hacemos commit para que se guarde los cambios en la base de datos
            manager.close();
            return true;
        }
        manager.close();
        return false;
    }

    @Override
    public ReproductionList searchReproductionListById(int id) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        ReproductionList reproductionList = manager.find(ReproductionList.class, id);
        manager.close();
        return reproductionList;
    }

    @Override
    public boolean Subcribe(int idUser, int idList) {
        //con hibernate
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        ReproductionList reproductionList = manager.find(ReproductionList.class, idList);
        if (reproductionList != null) {
            reproductionList.getSubscribedUsers().add(UserDTO.toUser(manager.find(UserDTO.class, idUser)));
            manager.getTransaction().commit(); // Hacemos commit para que se guarde los cambios en la base de datos
            manager.close();
            return true;
        }
        manager.close();
        return false;
    }

    @Override
    public Set<ReproductionList> getUserSubcriptions(int idUser) {
        //CON HIBERNATE
        EntityManager manager = ConnectionData.emf.createEntityManager();
        User user = manager.find(User.class, idUser);
        if (user != null) {
            Set<ReproductionList> result = user.getSubscribedLists();
            manager.close();
            return result;
        }
        manager.close();
        return null;
    }

    @Override
    public boolean getSubcribeToListByUser(int idUser, int idList) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        User user = manager.find(User.class, idUser);
        if (user != null) {
            Set<ReproductionList> result = user.getSubscribedLists();
            manager.close();
            return result.contains(searchReproductionListById(idList));
        }
        manager.close();
        return false;
    }

    @Override
    public boolean unSubcribe(int idUser, ReproductionList rl) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        ReproductionList reproductionList = manager.find(ReproductionList.class, rl.getId());
        if (reproductionList != null) {
            reproductionList.getSubscribedUsers().remove(UserDTO.toUser(manager.find(UserDTO.class, idUser)));
            manager.getTransaction().commit(); // Hacemos commit para que se guarde los cambios en la base de datos
            manager.close();
            return true;
        }
        manager.close();
        return false;
    }

    @Override
    public Set<Comment> getAllComments(int idList) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        ReproductionList reproductionList = manager.find(ReproductionList.class, idList);
        if (reproductionList != null) {
            Set<Comment> result = reproductionList.getComments();
            manager.close();
            return result;
        }
        manager.close();
        return null;
    }

    @Override
    public boolean addSong(int idSong, int idReproductionList) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        ReproductionList reproductionList = manager.find(ReproductionList.class, idReproductionList);
        if (reproductionList != null) {
            reproductionList.getSongs().add(manager.find(Song.class, idSong));
            manager.getTransaction().commit(); // Hacemos commit para que se guarde los cambios en la base de datos
            manager.close();
            return true;
        }
        manager.close();
        return false;
    }

    @Override
    public Set<Song> searchSongsById(int idReproductionList) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        ReproductionList reproductionList = manager.find(ReproductionList.class, idReproductionList);
        if (reproductionList != null) {
            Set<Song> result = reproductionList.getSongs();
            manager.close();
            return result;
        }
        manager.close();
        return null;
    }
    @Override
    public boolean existSongOnList(int idList, int idSong) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        ReproductionList reproductionList = manager.find(ReproductionList.class, idList);
        if (reproductionList != null) {
            Set<Song> result = reproductionList.getSongs();
            manager.close();
            return result.contains(SongDAO.getInstance().searchById(idSong));
        }
        manager.close();
        return false;
    }

    @Override
    public boolean removeSong(int idSong, int idReproductionList, UserDTO user) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        ReproductionList reproductionList = manager.find(ReproductionList.class, idReproductionList);
        if (reproductionList != null) {
            reproductionList.getSongs().remove(manager.find(Song.class, idSong));
            manager.getTransaction().commit(); // Hacemos commit para que se guarde los cambios en la base de datos
            manager.close();
            return true;
        }
        manager.close();
        return false;
    }

    @Override
    public Set<ReproductionList> searchByName(String filter){
        EntityManager manager = ConnectionData.emf.createEntityManager();
        Set<ReproductionList> result = new HashSet<>();
        for (ReproductionList reproductionList : manager.createQuery("SELECT r FROM ReproductionList r WHERE r.name LIKE CONCAT('%',?1,'%')", ReproductionList.class).setParameter(1, filter).getResultList()) {
            result.add(reproductionList);
        }
        manager.close();
        return result;
    }
    public int getAllSubcriptions(int idList){
        EntityManager manager = ConnectionData.emf.createEntityManager();
        ReproductionList reproductionList = manager.find(ReproductionList.class, idList);
        if (reproductionList != null) {
            int result = reproductionList.getSubscribedUsers().size();
            manager.close();
            return result;
        }
        manager.close();
        return 0;
    }
    public static ReproductionListDAO getInstance() {
        if (instance == null) {
            instance = new ReproductionListDAO();
        }
        return instance;
    }
}
