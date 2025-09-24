
package org.example.sem5.dao;

import org.example.sem5.model.Alumno;
import java.util.List;

public interface AlumnoDAO {
    void crear(Alumno alumno);
    Alumno obtenerPorId(int id);
    List<Alumno> obtenerTodos();
    boolean actualizar(Alumno alumno);
    boolean eliminar(int id);
}