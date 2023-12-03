package dev.iesfranciscodelosrios.acdmusic.Services;

import dev.iesfranciscodelosrios.acdmusic.Interfaces.iLogin;
import dev.iesfranciscodelosrios.acdmusic.Model.DAO.UserDAO;
import dev.iesfranciscodelosrios.acdmusic.Model.Domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login implements iLogin {

    private static Login instance;

    private Login() {
    }

    private static User currentUser;

    private UserDAO udao = UserDAO.getInstance();

    /**
     * Metodo para autentificar que el usuario se loguea con nickname y password correctos, además
     * seteará un currentUser en caso de ser satisfactoria la autentificación.
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return UserDTO en caso de que la autenticación sea satisfactoria y null en cualquier otro caso
     */
    @Override
    public User Auth(User user) {
        if (user == null) {
            return null;
        } else {
            User BDUser = udao.searchByNickname(user.getNickName());
            if(BDUser!=null && BDUser.getPassword().equals(encryptPassword(user.getPassword()))){
                User result = UserDAO.getInstance().searchById(BDUser.getId());
                setCurrentUser(result);
                return result;
            }
        }
        return null;
    }

    /**
     * Metodo para encriptar la contraseña usando SHA-256
     * @param password contraseña sin encriptar
     * @return contraseña encriptada en hexademila
     */
    @Override
    public String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for(byte b : passwordBytes){
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
                return hexString.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * Metodo para registrar un usuario
     * @param user objeto usuario con los datos del usuario y la contraseña sin encriptar
     * @return User provisto en formato DTO o null en caso de que user este vacio
     */
    @Override
    public User Register(User user) {
        if (user == null) {
            return null;
        } else {
            user.setPassword(encryptPassword(user.getPassword()));
            udao.addUser(user);
            setCurrentUser(user);
            return user;
        }
    }

    /**
     * Metodo para cerrar sesión
     * @return true en caso de que current user se establezca a null, false en cualquier otro caso
     */
    @Override
    public boolean Logout() {
        User current = getCurrentUser();
        current = null;
        setCurrentUser(current);
        if (current == null) return true;
        return false;
    }


    /**
     * Metodo para obtener el usuario actual
     * @return UserDTO del user activo en la app
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Metodo para setear el usuario actual en la app
     * @param currentUser UserDTO del usaurio actual en la app
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static Login getInstance() {
        if (instance == null) instance = new Login() {
        };
        return instance;
    }

}