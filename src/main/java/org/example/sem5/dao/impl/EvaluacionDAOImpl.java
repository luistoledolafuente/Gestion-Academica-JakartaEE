package org.example.sem5.dao.impl;

import org.example.sem5.dao.ConexionDB;
import org.example.sem5.dao.EvaluacionDAO;
import org.example.sem5.model.Evaluacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluacionDAOImpl implements EvaluacionDAO {

    private static final String INSERT_SQL = "INSERT INTO Evaluacion (idCurso, nombre, peso) VALUES (?, ?, ?);";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM Evaluacion WHERE idEvaluacion = ?;";
    private static final String SELECT_BY_CURSO_SQL = "SELECT * FROM Evaluacion WHERE idCurso = ?;";
    private static final String UPDATE_SQL = "UPDATE Evaluacion SET idCurso = ?, nombre = ?, peso = ? WHERE idEvaluacion = ?;";
    private static final String DELETE_SQL = "DELETE FROM Evaluacion WHERE idEvaluacion = ?;";

    @Override
    public void crear(Evaluacion evaluacion) {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, evaluacion.getIdCurso());
            ps.setString(2, evaluacion.getNombre());
            ps.setBigDecimal(3, evaluacion.getPeso());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Evaluacion obtenerPorId(int id) {
        Evaluacion evaluacion = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                evaluacion = new Evaluacion(rs.getInt("idEvaluacion"), rs.getInt("idCurso"), rs.getString("nombre"), rs.getBigDecimal("peso"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluacion;
    }

    @Override
    public List<Evaluacion> obtenerPorIdCurso(int idCurso) {
        List<Evaluacion> evaluaciones = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_CURSO_SQL)) {
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evaluaciones.add(new Evaluacion(rs.getInt("idEvaluacion"), rs.getInt("idCurso"), rs.getString("nombre"), rs.getBigDecimal("peso")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evaluaciones;
    }

    @Override
    public boolean actualizar(Evaluacion evaluacion) {
        boolean rowUpdated = false;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, evaluacion.getIdCurso());
            ps.setString(2, evaluacion.getNombre());
            ps.setBigDecimal(3, evaluacion.getPeso());
            ps.setInt(4, evaluacion.getIdEvaluacion());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean eliminar(int idEvaluacion) {
        boolean rowDeleted = false;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, idEvaluacion);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}