package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iUserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

public class UserDAO extends User implements iUserDAO  {

    private static UserDAO instance;

    private UserDAO() {}

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private static EntityManager manager;

    @Override
    public User addUser(User user) {
        manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(user);
            if (user == null) {
                return null;
            }
            manager.getTransaction().commit();
            manager.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(User user) {
        manager = ConnectionData.emf.createEntityManager();
        try{
            manager.getTransaction().begin();
            manager.remove(user);
            manager.getTransaction().commit();
            manager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User searchByEmail(String email) {
        manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            User user = manager.createQuery("FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
            manager.getTransaction().commit();
            manager.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User searchByNickname(String nickname) {
       manager = ConnectionData.emf.createEntityManager();
       try {
            manager.getTransaction().begin();
            User user = manager.createQuery("FROM User u WHERE u.nickName = :nickname", User.class)
                    .setParameter("nickname", nickname).getSingleResult();
            manager.getTransaction().commit();
            manager.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<User> searchByName(String filterWord) {
        manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            Set<User> users = new HashSet<>( manager.createQuery("FROM User u WHERE u.nickName LIKE CONCAT ('%',:filterWord,'%') LIMIT 3", User.class)
                    .setParameter("filterWord", filterWord)
                    .getResultList());
            manager.getTransaction().commit();
            manager.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User searchById(int idUser) {
        manager = ConnectionData.emf.createEntityManager();
        try {
            manager.getTransaction().begin();
            User user = manager.find(User.class, idUser);
            manager.getTransaction().commit();
            manager.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
