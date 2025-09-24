package org.example.sem5.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // ---- VERIFICA ESTAS 3 LÍNEAS ----
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_academica_db";
    private static final String USER = "root"; // <-- ¿Es este tu usuario de MySQL?
    private static final String PASSWORD = "Lmtlf1620*"; // <-- ¡DEBES CAMBIAR ESTO!

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver de MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}