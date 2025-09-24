package org.example.sem5.dao.impl;

import org.example.sem5.dao.ConexionDB;
import org.example.sem5.dao.DetalleMatriculaDAO;
import org.example.sem5.model.DetalleMatricula;
import org.example.sem5.model.DetalleMatriculaInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleMatriculaDAOImpl implements DetalleMatriculaDAO {

    private static final String INSERT_SQL =
            "INSERT INTO DetalleMatricula (idMatricula, idCurso, estado) VALUES (?, ?, ?);";
    private static final String SELECT_BY_MATRICULA_SQL =
            "SELECT * FROM DetalleMatricula WHERE idMatricula = ?;";
    private static final String UPDATE_ESTADO_SQL =
            "UPDATE DetalleMatricula SET estado = ? WHERE idDetalle = ?;";
    private static final String DELETE_SQL =
            "DELETE FROM DetalleMatricula WHERE idDetalle = ?;";

    @Override
    public void crear(DetalleMatricula detalle) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, detalle.getIdMatricula());
            ps.setInt(2, detalle.getIdCurso());
            ps.setString(3, detalle.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DetalleMatricula> obtenerPorIdMatricula(int idMatricula) {
        List<DetalleMatricula> detalles = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_MATRICULA_SQL)) {
            ps.setInt(1, idMatricula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                detalles.add(new DetalleMatricula(
                        rs.getInt("idDetalle"),
                        rs.getInt("idMatricula"),
                        rs.getInt("idCurso"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    @Override
    public List<DetalleMatriculaInfo> obtenerInfoPorIdMatricula(int idMatricula) {
        List<DetalleMatriculaInfo> detalles = new ArrayList<>();

        String sql = "SELECT dm.idDetalle, c.idCurso, c.nombreCurso, c.creditos, dm.estado " +
                "FROM DetalleMatricula dm " +
                "JOIN Curso c ON dm.idCurso = c.idCurso " +
                "WHERE dm.idMatricula = ?;";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                detalles.add(new DetalleMatriculaInfo(
                        rs.getInt("idDetalle"),
                        rs.getInt("idCurso"),          // ✅ agregado
                        rs.getString("nombreCurso"),
                        rs.getInt("creditos"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    @Override
    public boolean actualizarEstado(int idDetalle, String nuevoEstado) {
        boolean rowUpdated = false;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_ESTADO_SQL)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idDetalle);
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean eliminar(int idDetalle) {
        boolean rowDeleted = false;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, idDetalle);
            rowDeleted = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
