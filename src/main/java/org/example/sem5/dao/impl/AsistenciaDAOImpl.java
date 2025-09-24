package org.example.sem5.dao.impl;

import org.example.sem5.dao.AsistenciaDAO;
import org.example.sem5.dao.ConexionDB;
import org.example.sem5.model.Asistencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAOImpl implements AsistenciaDAO {

    // Esta consulta SQL intenta insertar un registro. Si ya existe uno para esa sesión y ese alumno
    // (gracias a la clave UNIQUE en la BD), en lugar de dar error, actualiza el estado.
    private static final String INSERT_OR_UPDATE_SQL = "INSERT INTO Asistencia (idSesion, idDetalle, estado) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE estado = VALUES(estado);";
    private static final String SELECT_BY_SESION_SQL = "SELECT * FROM Asistencia WHERE idSesion = ?;";

    @Override
    public void crearOActualizar(Asistencia asistencia) {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_OR_UPDATE_SQL)) {
            ps.setInt(1, asistencia.getIdSesion());
            ps.setInt(2, asistencia.getIdDetalle());
            ps.setString(3, asistencia.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Asistencia> obtenerPorIdSesion(int idSesion) {
        List<Asistencia> asistencias = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_SESION_SQL)) {
            ps.setInt(1, idSesion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                asistencias.add(new Asistencia(
                        rs.getInt("idAsistencia"),
                        rs.getInt("idSesion"),
                        rs.getInt("idDetalle"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asistencias;
    }
}