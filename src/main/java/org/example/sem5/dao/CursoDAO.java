// Paquete: org.example.sem5.dao
package org.example.sem5.dao;

import org.example.sem5.model.Curso;
import java.util.List;

public interface CursoDAO {
    void crear(Curso curso);
    Curso obtenerPorId(int id);
    List<Curso> obtenerTodos();
    boolean actualizar(Curso curso);
    boolean eliminar(int id);
}