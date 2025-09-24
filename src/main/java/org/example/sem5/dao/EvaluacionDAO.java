package org.example.sem5.dao;

import org.example.sem5.model.Evaluacion;
import java.util.List;

public interface EvaluacionDAO {
    void crear(Evaluacion evaluacion);
    Evaluacion obtenerPorId(int id);
    List<Evaluacion> obtenerPorIdCurso(int idCurso);
    boolean actualizar(Evaluacion evaluacion);
    boolean eliminar(int idEvaluacion);
}