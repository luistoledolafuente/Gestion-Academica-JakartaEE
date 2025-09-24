package org.example.sem5.dao;
import org.example.sem5.model.SesionClase;
import java.util.List;

public interface SesionClaseDAO {
    void crear(SesionClase sesion);
    SesionClase obtenerPorId(int id);
    List<SesionClase> obtenerPorCursoYPeriodo(int idCurso, int idPeriodo);
    boolean actualizar(SesionClase sesion);
    boolean eliminar(int idSesion);
}