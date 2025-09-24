package org.example.sem5.dao.impl;

import org.example.sem5.dao.ConexionDB;
import org.example.sem5.dao.SesionClaseDAO;
import org.example.sem5.model.SesionClase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionClaseDAOImpl implements SesionClaseDAO {

    private static final String INSERT_SQL = "INSERT INTO SesionClase (idCurso, idPeriodo, fecha, tema) VALUES (?, ?, ?, ?);";
    private static final String SELECT_BY_CURSO_PERIODO_SQL = "SELECT * FROM SesionClase WHERE idCurso = ? AND idPeriodo = ? ORDER BY fecha;";
    private static final String DELETE_SQL = "DELETE FROM SesionClase WHERE idSesion = ?;";

    @Override
    public void crear(SesionClase sesion) {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, sesion.getIdCurso());
            ps.setInt(2, sesion.getIdPeriodo());
            ps.setTimestamp(3, sesion.getFecha());
            ps.setString(4, sesion.getTema());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SesionClase> obtenerPorCursoYPeriodo(int idCurso, int idPeriodo) {
        List<SesionClase> sesiones = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_CURSO_PERIODO_SQL)) {
            ps.setInt(1, idCurso);
            ps.setInt(2, idPeriodo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sesiones.add(new SesionClase(
                        rs.getInt("idSesion"),
                        rs.getInt("idCurso"),
                        rs.getInt("idPeriodo"),
                        rs.getTimestamp("fecha"),
                        rs.getString("tema")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sesiones;
    }

    @Override
    public boolean eliminar(int idSesion) {
        boolean rowDeleted = false;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, idSesion);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}