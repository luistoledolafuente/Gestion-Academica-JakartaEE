package org.example.sem5.dao;
import org.example.sem5.model.Asistencia;
import java.util.List;

public interface AsistenciaDAO {
    void crearOActualizar(Asistencia asistencia);
    List<Asistencia> obtenerPorIdSesion(int idSesion);
}