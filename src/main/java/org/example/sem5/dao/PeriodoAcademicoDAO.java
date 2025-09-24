package org.example.sem5.dao;

import org.example.sem5.model.PeriodoAcademico;
import java.util.List;

public interface PeriodoAcademicoDAO {
    void crear(PeriodoAcademico periodo);
    PeriodoAcademico obtenerPorId(int id);
    List<PeriodoAcademico> obtenerTodos();
    boolean actualizar(PeriodoAcademico periodo);
    boolean eliminar(int id);
}