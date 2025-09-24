package org.example.sem5.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sem5.dao.*;
import org.example.sem5.dao.impl.*;
import org.example.sem5.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/sesiones")
public class SesionClaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SesionClaseDAO sesionClaseDAO;
    private CursoDAO cursoDAO;
    private PeriodoAcademicoDAO periodoDAO;

    public void init() {
        sesionClaseDAO = new SesionClaseDAOImpl();
        cursoDAO = new CursoDAOImpl();
        periodoDAO = new PeriodoAcademicoDAOImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    insertarSesion(request, response);
                    break;
                case "eliminar":
                    eliminarSesion(request, response);
                    break;
                default:
                    listarSesiones(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarSesiones(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        // ---- 1. CAPTURAMOS el idMatricula que viene de la página anterior ----
        String idMatricula = request.getParameter("idMatricula");

        Curso curso = cursoDAO.obtenerPorId(idCurso);
        PeriodoAcademico periodo = periodoDAO.obtenerPorId(idPeriodo);
        List<SesionClase> listaSesiones = sesionClaseDAO.obtenerPorCursoYPeriodo(idCurso, idPeriodo);

        request.setAttribute("curso", curso);
        request.setAttribute("periodo", periodo);
        request.setAttribute("listaSesiones", listaSesiones);
        // ---- 2. LO ENVIAMOS a la siguiente página (lista.jsp) ----
        request.setAttribute("idMatricula", idMatricula);

        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/sesiones/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        request.setAttribute("idCurso", idCurso);
        request.setAttribute("idPeriodo", idPeriodo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/sesiones/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarSesion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        String fechaHoraLocal = request.getParameter("fecha"); // Formato 'YYYY-MM-DDThh:mm'
        String tema = request.getParameter("tema");

        // Convertir el String de 'datetime-local' a Timestamp de SQL
        Timestamp fechaTimestamp = Timestamp.valueOf(fechaHoraLocal.replace("T", " ") + ":00");

        SesionClase nuevaSesion = new SesionClase();
        nuevaSesion.setIdCurso(idCurso);
        nuevaSesion.setIdPeriodo(idPeriodo);
        nuevaSesion.setFecha(fechaTimestamp);
        nuevaSesion.setTema(tema);
        sesionClaseDAO.crear(nuevaSesion);

        response.sendRedirect("sesiones?action=listar&idCurso=" + idCurso + "&idPeriodo=" + idPeriodo);
    }

    private void eliminarSesion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idSesion = Integer.parseInt(request.getParameter("idSesion"));
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        sesionClaseDAO.eliminar(idSesion);
        response.sendRedirect("sesiones?action=listar&idCurso=" + idCurso + "&idPeriodo=" + idPeriodo);
    }
}