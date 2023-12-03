package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iAlbumDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Album;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

public class AlbumDAO implements iAlbumDAO {
    private static AlbumDAO instance;

    private AlbumDAO() {
    }

    public static AlbumDAO getInstance() {
        if (instance == null) {
            instance = new AlbumDAO();
        }
        return instance;
    }

    @Override
    public boolean addAlbum(Album album) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(album);
        if (album == null) {
            return false;
        }
        manager.getTransaction().commit();
        manager.close();
        return true;
    }

    @Override
    public Set<Album> searchAlbumByName(String filterWord) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Set<Album> albums = new HashSet<>(manager
                    .createQuery("FROM Album a WHERE a.name LIKE CONCAT('%', :filterWord, '%')", Album.class)
                    .setParameter("filterWord", filterWord)
                    .getResultList());
            manager.getTransaction().commit();
            return albums;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
            return new HashSet<>();
        } finally {
            manager.close();
        }
    }

    @Override
    public Set<Album> searchAllAlbumsByArtist(ArtistDTO artist) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Set<Album> albums = new HashSet<>(manager.createQuery("FROM Album a WHERE a.artist = :artist", Album.class)
                .setParameter("artist", artist)
                .getResultList());
        manager.getTransaction().commit();
        manager.close();
        return albums;
    }

    @Override
    public Set<Album> searchMoreRecent() {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Set<Album> albums = new HashSet<>();
        albums.addAll(manager.createQuery("FROM Album ORDER BY date DESC", Album.class).setMaxResults(3).getResultList());
        manager.getTransaction().commit();
        manager.close();
        return albums;
    }

    @Override
    public Album updateAlbum(int id, String newName) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Album album = manager.find(Album.class, id);
        album.setName(newName);
        manager.getTransaction().commit();
        manager.close();
        return album;
    }

    public Album getAlbumById(int id) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Album album = manager.find(Album.class, id);
        manager.getTransaction().commit();
        manager.close();
        return album;
    }

    /**
     * Buscaremos un álbum a partir del ID de una de sus canciones.
     * @param idSong ID de la canción
     * @return Álbum que contiene la canción o null si no se encuentra.
     */
    public Album searchAlbumByIdSong(int idSong) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Album album = manager.createQuery("SELECT s.album FROM Song s WHERE s.id_song = :idSong", Album.class)
                    .setParameter("idSong", idSong)
                    .getSingleResult();
            manager.getTransaction().commit();
            return album;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } finally {
            manager.close();
        }
    }

}
