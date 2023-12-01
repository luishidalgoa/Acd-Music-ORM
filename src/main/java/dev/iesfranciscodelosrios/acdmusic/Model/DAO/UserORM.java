package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Connection.ConnectionData;
import dev.iesfranciscodelosrios.acdmusic.Interfaces.iUserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Set;

public class UserORM extends User implements iUserDAO  {

    private static EntityManager managerU;
    private static EntityManagerFactory emfU;

    @Override
    public UserDTO addUser(User user) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        manager.persist(user);
        if (user == null) {
            return null;
        }
        manager.getTransaction().commit();
        manager.close();
        return null;
    }

    @Override
    public boolean delete(User user) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        manager.remove(user);
        manager.getTransaction().commit();
        manager.close();
        return true;
    }

    @Override
    public UserDTO searchByEmail(String email) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        User user = manager.createQuery("FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email).getSingleResult();
        manager.getTransaction().commit();
        manager.close();

        UserDTO dto = new UserDTO(user);

        return dto;
    }

    @Override
    public UserDTO searchByNickname(String nickname) {
        return null;
    }

    @Override
    public Set<UserDTO> searchByName(String filterWord) {
        return null;
    }

    @Override
    public UserDTO searchById(int idUser) {
        EntityManager manager = ConnectionData.emf.createEntityManager();
        manager.getTransaction().begin();
        User user = manager.find(User.class, idUser);
        manager.getTransaction().commit();
        manager.close();

        UserDTO dto = new UserDTO(user);

        return dto;
    }
}
