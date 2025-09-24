package org.example.sem5.dao;

import org.example.sem5.model.Matricula;
import org.example.sem5.model.MatriculaInfo;
import java.util.List;

public interface MatriculaDAO {
    void crear(Matricula matricula);
    Matricula obtenerPorId(int id);
    List<Matricula> obtenerPorIdAlumno(int idAlumno);
    boolean actualizar(Matricula matricula);

    // El método clave para la lista
    List<MatriculaInfo> obtenerTodaLaInfo();
}