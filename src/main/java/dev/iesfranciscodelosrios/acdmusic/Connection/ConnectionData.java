package dev.iesfranciscodelosrios.acdmusic.Connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionData {
    //Es opcional porque podria interesarnos cerrar la conexion
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("aplicacion");

    public ConnectionData(){ }
}