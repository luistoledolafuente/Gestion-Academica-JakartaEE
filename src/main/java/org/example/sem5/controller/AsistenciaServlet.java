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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/asistencia")
public class AsistenciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AsistenciaDAO asistenciaDAO;
    private DetalleMatriculaDAO detalleMatriculaDAO;
    private SesionClaseDAO sesionClaseDAO;

    public void init() {
        asistenciaDAO = new AsistenciaDAOImpl();
        detalleMatriculaDAO = new DetalleMatriculaDAOImpl();
        sesionClaseDAO = new SesionClaseDAOImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "mostrarFormulario";
        }
        try {
            switch (action) {
                case "guardar":
                    guardarAsistencia(request, response);
                    break;
                default:
                    mostrarFormulario(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idSesion = Integer.parseInt(request.getParameter("idSesion"));
        SesionClase sesion = sesionClaseDAO.obtenerPorId(idSesion);

        // Obtenemos la lista de alumnos matriculados en ese curso y periodo
        List<DetalleMatriculaInfo> alumnosMatriculados = detalleMatriculaDAO.obtenerInfoPorIdMatricula(Integer.parseInt(request.getParameter("idMatricula")));

        // Obtenemos las asistencias ya registradas para esta sesión
        List<Asistencia> asistenciasActuales = asistenciaDAO.obtenerPorIdSesion(idSesion);
        Map<Integer, String> mapaAsistencias = asistenciasActuales.stream()
                .collect(Collectors.toMap(Asistencia::getIdDetalle, Asistencia::getEstado));

        request.setAttribute("sesion", sesion);
        request.setAttribute("alumnosMatriculados", alumnosMatriculados);
        request.setAttribute("mapaAsistencias", mapaAsistencias);
        request.setAttribute("idMatricula", request.getParameter("idMatricula"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/asistencia/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarAsistencia(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idSesion = Integer.parseInt(request.getParameter("idSesion"));
        int idMatricula = Integer.parseInt(request.getParameter("idMatricula"));

        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("estado_")) {
                int idDetalle = Integer.parseInt(paramName.substring(7)); // Extrae el ID de "estado_123"
                String estado = request.getParameter(paramName);

                Asistencia asistencia = new Asistencia();
                asistencia.setIdSesion(idSesion);
                asistencia.setIdDetalle(idDetalle);
                asistencia.setEstado(estado);

                asistenciaDAO.crearOActualizar(asistencia);
            }
        }

        // Redirigimos de vuelta a la página de detalle de la matrícula
        response.sendRedirect("matriculas?action=verDetalle&id=" + idMatricula);
    }
}