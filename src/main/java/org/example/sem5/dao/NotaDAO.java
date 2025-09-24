package org.example.sem5.dao;
import org.example.sem5.model.Nota;
import org.example.sem5.model.NotaInfo;
import java.util.List;

public interface NotaDAO {
    void crear(Nota nota);
    Nota obtenerPorIdDetalleYIdEvaluacion(int idDetalle, int idEvaluacion);
    List<Nota> obtenerPorIdDetalle(int idDetalle);
    boolean actualizar(Nota nota);
    List<NotaInfo> obtenerInfoPorIdDetalle(int idDetalle);
    void crearOActualizar(Nota nota);
}