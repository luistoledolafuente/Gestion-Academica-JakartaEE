package org.example.sem5.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sem5.dao.CursoDAO;
import org.example.sem5.dao.impl.CursoDAOImpl;
import org.example.sem5.model.Curso;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cursos")
public class CursoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CursoDAO cursoDAO;

    public void init() {
        cursoDAO = new CursoDAOImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        try {
            switch (action) {
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "insertar":
                    insertarCurso(request, response);
                    break;
                case "eliminar":
                    eliminarCurso(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "actualizar":
                    actualizarCurso(request, response);
                    break;
                default:
                    listarCursos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarCursos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Curso> listaCursos = cursoDAO.obtenerTodos();
        request.setAttribute("listaCursos", listaCursos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/cursos/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/cursos/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Curso cursoExistente = cursoDAO.obtenerPorId(id);
        request.setAttribute("curso", cursoExistente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/cursos/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarCurso(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        // ESTAS LÍNEAS DEBEN COINCIDIR EXACTAMENTE CON LOS 'name' DEL FORMULARIO
        String codigoCurso = request.getParameter("codigoCurso");
        String nombreCurso = request.getParameter("nombreCurso");
        int creditos = Integer.parseInt(request.getParameter("creditos")); // <-- Un error aquí puede causar una excepción

        Curso nuevoCurso = new Curso();
        nuevoCurso.setCodigoCurso(codigoCurso);
        nuevoCurso.setNombreCurso(nombreCurso);
        nuevoCurso.setCreditos(creditos);

        cursoDAO.crear(nuevoCurso);
        response.sendRedirect("cursos?action=listar");
    }

    private void actualizarCurso(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String codigoCurso = request.getParameter("codigoCurso");
        String nombreCurso = request.getParameter("nombreCurso");
        int creditos = Integer.parseInt(request.getParameter("creditos"));

        Curso curso = new Curso();
        curso.setIdCurso(id);
        curso.setCodigoCurso(codigoCurso);
        curso.setNombreCurso(nombreCurso);
        curso.setCreditos(creditos);

        cursoDAO.actualizar(curso);
        response.sendRedirect("cursos?action=listar");
    }

    private void eliminarCurso(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        cursoDAO.eliminar(id);
        response.sendRedirect("cursos?action=listar");
    }
}