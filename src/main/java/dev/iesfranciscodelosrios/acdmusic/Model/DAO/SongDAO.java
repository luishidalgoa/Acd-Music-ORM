package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iSongDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.Song;
import dev.iesfranciscodelosrios.acdmusic.Model.Enum.Genre;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

public class SongDAO implements iSongDAO {
    private static SongDAO instance;

    private SongDAO() {
    }

    public static SongDAO getInstance() {
        if (instance == null) {
            instance = new SongDAO();
        }
        return instance;
    }

    @Override
    public Song addSong(Song song) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(song);
            manager.getTransaction().commit();
            return song;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public boolean removeSong(int idSong) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Song song = manager.find(Song.class, idSong);
            manager.remove(song);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            manager.close();
        }
    }

    @Override
    public Set<Song> searchByGenre(Genre genre) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Set<Song> songs = new HashSet<>();
            songs.addAll(manager.createQuery("FROM Song WHERE genre = :genre", Song.class).setParameter("genre", genre).getResultList());
            manager.getTransaction().commit();
            return songs;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace(); // Handle or log the exception as needed
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public Set<Song> searchByAlbumId(int idAlbum) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Set<Song> songs = new HashSet<>();
            songs.addAll(manager.createQuery("FROM Song WHERE album.idAlbum = :idAlbum", Song.class)
                    .setParameter("idAlbum", idAlbum)
                    .getResultList());
            manager.getTransaction().commit();
            return songs;
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace(); // Handle or log the exception as needed
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public Set<Song> searchTopSongs() {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Set<Song> songs = new HashSet<>();
        songs.addAll(manager.createQuery("FROM Song ORDER BY reproductions DESC", Song.class).setMaxResults(4).getResultList());
        if (songs.isEmpty()) {
            return null;
        }
        manager.getTransaction().commit();
        manager.close();
        return songs;
    }

    @Override
    public Set<Song> searchRecientSongs() {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Set<Song> songs = new HashSet<>();
        songs.addAll(manager.createQuery("FROM Song ORDER BY id_song DESC", Song.class).setMaxResults(4).getResultList());
        if (songs.isEmpty()) {
            return null;
        }
        manager.getTransaction().commit();
        manager.close();
        return songs;
    }

    @Override
    public Set<Song> searchByNombre(String filterWord) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Set<Song> songs = new HashSet<>();
        songs.addAll(manager.createQuery("FROM Song WHERE name LIKE :filterWord", Song.class).setParameter("filterWord", "%" + filterWord + "%").setMaxResults(3).getResultList());
        if (songs.isEmpty()) {
            return null;
        }
        manager.getTransaction().commit();
        manager.close();
        return songs;
    }

    @Override
    public boolean updateReproductions(int idSong) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Song song = manager.find(Song.class, idSong);
        song.setReproductions(song.getReproductions() + 1);
        if (song.getReproductions() == 0) {
            return false;
        }
        manager.getTransaction().commit();
        manager.close();
        return true;
    }

    @Override
    public Song searchById(int idSong) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        Song song = manager.find(Song.class, idSong);
        if (song == null) {
            return null;
        }
        manager.getTransaction().commit();
        manager.close();
        return song;
    }
}