package org.example.sem5.dao;

import org.example.sem5.model.DetalleMatricula;
import org.example.sem5.model.DetalleMatriculaInfo;
import java.util.List;

public interface DetalleMatriculaDAO {
    void crear(DetalleMatricula detalle);

    List<DetalleMatricula> obtenerPorIdMatricula(int idMatricula);

    boolean actualizarEstado(int idDetalle, String nuevoEstado);

    // ✅ Método que retorna DTOs con información extendida del curso
    List<DetalleMatriculaInfo> obtenerInfoPorIdMatricula(int idMatricula);

    boolean eliminar(int idDetalle);
}
