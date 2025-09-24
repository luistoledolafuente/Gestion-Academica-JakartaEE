// Paquete: org.example.sem5.dao.impl
package org.example.sem5.dao.impl;

import org.example.sem5.dao.AlumnoDAO;
import org.example.sem5.dao.ConexionDB;
import org.example.sem5.model.Alumno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {

    private static final String INSERT_ALUMNO_SQL = "INSERT INTO Alumno (codigo, nombres, apellidos, email) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALUMNO_BY_ID = "SELECT * FROM Alumno WHERE idAlumno = ?;";
    private static final String SELECT_ALL_ALUMNOS = "SELECT * FROM Alumno;";
    private static final String UPDATE_ALUMNO_SQL = "UPDATE Alumno SET codigo = ?, nombres = ?, apellidos = ?, email = ? WHERE idAlumno = ?;";
    private static final String DELETE_ALUMNO_SQL = "DELETE FROM Alumno WHERE idAlumno = ?;";

    @Override
    public void crear(Alumno alumno) {
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_ALUMNO_SQL)) {
            ps.setString(1, alumno.getCodigo());
            ps.setString(2, alumno.getNombres());
            ps.setString(3, alumno.getApellidos());
            ps.setString(4, alumno.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Alumno obtenerPorId(int id) {
        Alumno alumno = null;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALUMNO_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alumno = new Alumno(
                        rs.getInt("idAlumno"),
                        rs.getString("codigo"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getTimestamp("fechaRegistro")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumno;
    }

    @Override
    public List<Alumno> obtenerTodos() {
        List<Alumno> alumnos = new ArrayList<>();
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ALUMNOS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                alumnos.add(new Alumno(
                        rs.getInt("idAlumno"),
                        rs.getString("codigo"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getTimestamp("fechaRegistro")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public boolean actualizar(Alumno alumno) {
        boolean rowUpdated = false;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_ALUMNO_SQL)) {
            ps.setString(1, alumno.getCodigo());
            ps.setString(2, alumno.getNombres());
            ps.setString(3, alumno.getApellidos());
            ps.setString(4, alumno.getEmail());
            ps.setInt(5, alumno.getIdAlumno());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean eliminar(int id) {
        boolean rowDeleted = false;
        try (Connection connection = ConexionDB.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_ALUMNO_SQL)) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}