package org.example.sem5.dao.impl;

import org.example.sem5.dao.ConexionDB;
import org.example.sem5.dao.MatriculaDAO;
import org.example.sem5.model.Matricula;
import org.example.sem5.model.MatriculaInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAOImpl implements MatriculaDAO {

    private static final String INSERT_SQL =
            "INSERT INTO Matricula (idAlumno, idPeriodo, fechaMatricula, estado) VALUES (?, ?, ?, ?);";
    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM Matricula WHERE idMatricula = ?;";
    private static final String SELECT_BY_ALUMNO_ID_SQL =
            "SELECT * FROM Matricula WHERE idAlumno = ? ORDER BY idPeriodo DESC;";
    private static final String UPDATE_SQL =
            "UPDATE Matricula SET idAlumno = ?, idPeriodo = ?, fechaMatricula = ?, estado = ? WHERE idMatricula = ?;";
    private static final String SELECT_ALL_INFO_SQL =
            "SELECT m.idMatricula, m.fechaMatricula, m.estado, p.idPeriodo, " +  // <-- añadido p.idPeriodo
                    "CONCAT(a.nombres, ' ', a.apellidos) AS nombreCompleto, a.codigo, p.nombrePeriodo " +
                    "FROM Matricula m " +
                    "JOIN Alumno a ON m.idAlumno = a.idAlumno " +
                    "JOIN PeriodoAcademico p ON m.idPeriodo = p.idPeriodo " +
                    "ORDER BY m.fechaMatricula DESC;";

    @Override
    public void crear(Matricula matricula) {
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setInt(1, matricula.getIdAlumno());
            ps.setInt(2, matricula.getIdPeriodo());
            ps.setTimestamp(3, matricula.getFechaMatricula());
            ps.setString(4, matricula.getEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Matricula obtenerPorId(int id) {
        Matricula matricula = null;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                matricula = new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getInt("idAlumno"),
                        rs.getInt("idPeriodo"),
                        rs.getTimestamp("fechaMatricula"),
                        rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matricula;
    }

    @Override
    public List<Matricula> obtenerPorIdAlumno(int idAlumno) {
        List<Matricula> matriculas = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ALUMNO_ID_SQL)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                matriculas.add(new Matricula(
                        rs.getInt("idMatricula"),
                        rs.getInt("idAlumno"),
                        rs.getInt("idPeriodo"),
                        rs.getTimestamp("fechaMatricula"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matriculas;
    }

    @Override
    public boolean actualizar(Matricula matricula) {
        boolean rowUpdated = false;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, matricula.getIdAlumno());
            ps.setInt(2, matricula.getIdPeriodo());
            ps.setTimestamp(3, matricula.getFechaMatricula());
            ps.setString(4, matricula.getEstado());
            ps.setInt(5, matricula.getIdMatricula());
            rowUpdated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public List<MatriculaInfo> obtenerTodaLaInfo() {
        List<MatriculaInfo> matriculasInfo = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_INFO_SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                matriculasInfo.add(new MatriculaInfo(
                        rs.getInt("idMatricula"),
                        rs.getTimestamp("fechaMatricula"),
                        rs.getString("estado"),
                        rs.getString("nombreCompleto"),
                        rs.getString("codigo"),
                        rs.getString("nombrePeriodo"),
                        rs.getInt("idPeriodo")   // <-- ahora también se llena
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matriculasInfo;
    }
}
