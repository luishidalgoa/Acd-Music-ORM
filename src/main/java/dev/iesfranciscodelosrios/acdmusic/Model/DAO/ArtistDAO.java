package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iArtistDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Artist;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

public class ArtistDAO extends Artist implements iArtistDAO {
    private static ArtistDAO instance;

    private ArtistDAO() {}

    public static ArtistDAO getInstance() {
        if (instance == null) {
            instance = new ArtistDAO();
        }
        return instance;
    }

    private static EntityManager manager;

    @Override
    public Artist addArtist(Artist artist) {
        manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(artist);
            if (artist == null) {
                return null;
            }
            manager.getTransaction().commit();
            manager.close();
            return artist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeArtist(Artist artist) {
        manager = ConnectionData.emf.createEntityManager();
        try{
            manager.getTransaction().begin();
            manager.remove(artist);
            manager.getTransaction().commit();
            manager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Artist searchArtistByIdArtist(int idUser) {
        manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Artist artist = manager.find(Artist.class, idUser);
            manager.getTransaction().commit();
            manager.close();
            return artist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Artist> searchArtistByName(String filterWord) {
        manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Set<Artist> artists = new HashSet<>( manager.createQuery("FROM Artist a WHERE a.nickName LIKE CONCAT ('%',:filterWord,'%') LIMIT 3", Artist.class)
                    .setParameter("filterWord", filterWord)
                    .getResultList());
            manager.getTransaction().commit();
            manager.close();
            return artists;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Artist searchArtistByIdAlbum(int idAlbum) {
        return null;
    }
}
