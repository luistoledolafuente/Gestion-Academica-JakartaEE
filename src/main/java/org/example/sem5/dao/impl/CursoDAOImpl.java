package org.example.sem5.dao.impl;

import org.example.sem5.dao.ConexionDB;
import org.example.sem5.dao.CursoDAO;
import org.example.sem5.model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAOImpl implements CursoDAO {

    // Constantes con las sentencias SQL para evitar errores de tipeo y facilitar el mantenimiento
    private static final String INSERT_CURSO_SQL = "INSERT INTO Curso (codigoCurso, nombreCurso, creditos) VALUES (?, ?, ?);";
    private static final String SELECT_CURSO_BY_ID = "SELECT idCurso, codigoCurso, nombreCurso, creditos FROM Curso WHERE idCurso = ?;";
    private static final String SELECT_ALL_CURSOS = "SELECT * FROM Curso;";
    private static final String UPDATE_CURSO_SQL = "UPDATE Curso SET codigoCurso = ?, nombreCurso = ?, creditos = ? WHERE idCurso = ?;";
    private static final String DELETE_CURSO_SQL = "DELETE FROM Curso WHERE idCurso = ?;";

    @Override
    public void crear(Curso curso) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_CURSO_SQL)) {
            ps.setString(1, curso.getCodigoCurso());
            ps.setString(2, curso.getNombreCurso());
            ps.setInt(3, curso.getCreditos());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Curso obtenerPorId(int id) {
        Curso curso = null;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CURSO_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String codigoCurso = rs.getString("codigoCurso");
                String nombreCurso = rs.getString("nombreCurso");
                int creditos = rs.getInt("creditos");
                curso = new Curso(id, codigoCurso, nombreCurso, creditos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return curso;
    }

    @Override
    public List<Curso> obtenerTodos() {
        List<Curso> cursos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection();
             // Usamos PreparedStatement por consistencia, aunque no tenga parámetros
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_CURSOS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idCurso");
                String codigoCurso = rs.getString("codigoCurso");
                String nombreCurso = rs.getString("nombreCurso");
                int creditos = rs.getInt("creditos");
                cursos.add(new Curso(id, codigoCurso, nombreCurso, creditos));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    @Override
    public boolean actualizar(Curso curso) {
        boolean rowUpdated = false;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_CURSO_SQL)) {
            ps.setString(1, curso.getCodigoCurso());
            ps.setString(2, curso.getNombreCurso());
            ps.setInt(3, curso.getCreditos());
            ps.setInt(4, curso.getIdCurso());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean eliminar(int id) {
        boolean rowDeleted = false;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_CURSO_SQL)) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}