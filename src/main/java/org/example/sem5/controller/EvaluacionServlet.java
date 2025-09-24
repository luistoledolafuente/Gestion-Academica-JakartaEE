package org.example.sem5.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sem5.dao.CursoDAO;
import org.example.sem5.dao.EvaluacionDAO;
import org.example.sem5.dao.impl.CursoDAOImpl;
import org.example.sem5.dao.impl.EvaluacionDAOImpl;
import org.example.sem5.model.Curso;
import org.example.sem5.model.Evaluacion;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/evaluaciones")
public class EvaluacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EvaluacionDAO evaluacionDAO;
    private CursoDAO cursoDAO;

    public void init() {
        evaluacionDAO = new EvaluacionDAOImpl();
        cursoDAO = new CursoDAOImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar"; // Por defecto, listamos las evaluaciones de un curso
        }
        try {
            switch (action) {
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "insertar":
                    insertarEvaluacion(request, response);
                    break;
                case "eliminar":
                    eliminarEvaluacion(request, response);
                    break;
                default:
                    listarEvaluaciones(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarEvaluaciones(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        Curso curso = cursoDAO.obtenerPorId(idCurso);
        List<Evaluacion> listaEvaluaciones = evaluacionDAO.obtenerPorIdCurso(idCurso);

        request.setAttribute("curso", curso);
        request.setAttribute("listaEvaluaciones", listaEvaluaciones);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/evaluaciones/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        Curso curso = cursoDAO.obtenerPorId(idCurso);
        request.setAttribute("curso", curso);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/evaluaciones/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarEvaluacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        String nombre = request.getParameter("nombre");
        BigDecimal peso = new BigDecimal(request.getParameter("peso"));

        Evaluacion nuevaEvaluacion = new Evaluacion();
        nuevaEvaluacion.setIdCurso(idCurso);
        nuevaEvaluacion.setNombre(nombre);
        nuevaEvaluacion.setPeso(peso);
        evaluacionDAO.crear(nuevaEvaluacion);

        response.sendRedirect("evaluaciones?action=listar&idCurso=" + idCurso);
    }

    private void eliminarEvaluacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idEvaluacion = Integer.parseInt(request.getParameter("idEvaluacion"));
        int idCurso = Integer.parseInt(request.getParameter("idCurso")); // Para redirigir
        evaluacionDAO.eliminar(idEvaluacion);
        response.sendRedirect("evaluaciones?action=listar&idCurso=" + idCurso);
    }
}