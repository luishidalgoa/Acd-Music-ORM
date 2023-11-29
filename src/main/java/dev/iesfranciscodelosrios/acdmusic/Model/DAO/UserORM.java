package dev.iesfranciscodelosrios.acdmusic.Model.DAO;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iUserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class UserORM extends User implements iUserDAO  {

    private static UserORM instance;

    private static EntityManager managerU;
    private static EntityManagerFactory emfU;

    @Override
    public UserDTO addUser(User user) {
        emfU = Persistence.createEntityManagerFactory("user");
        managerU = emfU.createEntityManager();




        return null;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User searchByEmail(String email) {
        return null;
    }

    @Override
    public User searchByNickname(String nickname) {
        return null;
    }

    @Override
    public Set<User> searchByName(String filterWord) {
        return null;
    }

    @Override
    public User searchById(int idUser) {
        return null;
    }
}
