package org.example.sem5.dao.impl;

import org.example.sem5.dao.ConexionDB;
import org.example.sem5.dao.NotaDAO;
import org.example.sem5.model.Nota;
import org.example.sem5.model.NotaInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAOImpl implements NotaDAO {

    private static final String INSERT_SQL = "INSERT INTO Nota (idDetalle, idEvaluacion, nota) VALUES (?, ?, ?);";
    private static final String SELECT_BY_DETALLE_EVAL_SQL = "SELECT * FROM Nota WHERE idDetalle = ? AND idEvaluacion = ?;";
    private static final String SELECT_BY_DETALLE_SQL = "SELECT * FROM Nota WHERE idDetalle = ?;";
    private static final String UPDATE_SQL = "UPDATE Nota SET nota = ? WHERE idNota = ?;";

    @Override
    public void crear(Nota nota) {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, nota.getIdDetalle());
            ps.setInt(2, nota.getIdEvaluacion());
            ps.setBigDecimal(3, nota.getNota());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Nota obtenerPorIdDetalleYIdEvaluacion(int idDetalle, int idEvaluacion) {
        Nota nota = null;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_DETALLE_EVAL_SQL)) {
            ps.setInt(1, idDetalle);
            ps.setInt(2, idEvaluacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nota = new Nota(
                        rs.getInt("idNota"),
                        rs.getInt("idDetalle"),
                        rs.getInt("idEvaluacion"),
                        rs.getBigDecimal("nota")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nota;
    }

    @Override
    public List<Nota> obtenerPorIdDetalle(int idDetalle) {
        List<Nota> notas = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_DETALLE_SQL)) {
            ps.setInt(1, idDetalle);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                notas.add(new Nota(
                        rs.getInt("idNota"),
                        rs.getInt("idDetalle"),
                        rs.getInt("idEvaluacion"),
                        rs.getBigDecimal("nota")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }

    @Override
    public boolean actualizar(Nota nota) {
        boolean rowUpdated = false;
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setBigDecimal(1, nota.getNota());
            ps.setInt(2, nota.getIdNota());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public List<NotaInfo> obtenerInfoPorIdDetalle(int idDetalle) {
        List<NotaInfo> notas = new ArrayList<>();
        String sql = "SELECT e.nombre, e.peso, n.nota " +
                "FROM Nota n JOIN Evaluacion e ON n.idEvaluacion = e.idEvaluacion " +
                "WHERE n.idDetalle = ?;";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDetalle);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                notas.add(new NotaInfo(
                        rs.getString("nombre"),
                        rs.getBigDecimal("peso"),
                        rs.getBigDecimal("nota")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }
}