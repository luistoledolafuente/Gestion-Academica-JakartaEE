package org.example.sem5.dao.impl;

import org.example.sem5.dao.ConexionDB;
import org.example.sem5.dao.PeriodoAcademicoDAO;
import org.example.sem5.model.PeriodoAcademico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeriodoAcademicoDAOImpl implements PeriodoAcademicoDAO {

    private static final String INSERT_SQL = "INSERT INTO PeriodoAcademico (nombrePeriodo, fechaInicio, fechaFin, estado) VALUES (?, ?, ?, ?);";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM PeriodoAcademico WHERE idPeriodo = ?;";
    private static final String SELECT_ALL_SQL = "SELECT * FROM PeriodoAcademico ORDER BY fechaInicio DESC;";
    private static final String UPDATE_SQL = "UPDATE PeriodoAcademico SET nombrePeriodo = ?, fechaInicio = ?, fechaFin = ?, estado = ? WHERE idPeriodo = ?;";
    private static final String DELETE_SQL = "DELETE FROM PeriodoAcademico WHERE idPeriodo = ?;";

    @Override
    public void crear(PeriodoAcademico periodo) {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, periodo.getNombrePeriodo());
            ps.setDate(2, periodo.getFechaInicio());
            ps.setDate(3, periodo.getFechaFin());
            ps.setString(4, periodo.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PeriodoAcademico obtenerPorId(int id) {
        PeriodoAcademico periodo = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                periodo = new PeriodoAcademico(
                        rs.getInt("idPeriodo"),
                        rs.getString("nombrePeriodo"),
                        rs.getDate("fechaInicio"),
                        rs.getDate("fechaFin"),
                        rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return periodo;
    }

    @Override
    public List<PeriodoAcademico> obtenerTodos() {
        List<PeriodoAcademico> periodos = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                periodos.add(new PeriodoAcademico(
                        rs.getInt("idPeriodo"),
                        rs.getString("nombrePeriodo"),
                        rs.getDate("fechaInicio"),
                        rs.getDate("fechaFin"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return periodos;
    }

    @Override
    public boolean actualizar(PeriodoAcademico periodo) {
        boolean rowUpdated = false;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, periodo.getNombrePeriodo());
            ps.setDate(2, periodo.getFechaInicio());
            ps.setDate(3, periodo.getFechaFin());
            ps.setString(4, periodo.getEstado());
            ps.setInt(5, periodo.getIdPeriodo());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean eliminar(int id) {
        boolean rowDeleted = false;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}