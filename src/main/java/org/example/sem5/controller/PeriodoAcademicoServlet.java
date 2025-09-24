package org.example.sem5.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sem5.dao.PeriodoAcademicoDAO;
import org.example.sem5.dao.impl.PeriodoAcademicoDAOImpl;
import org.example.sem5.model.PeriodoAcademico;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/periodos")
public class PeriodoAcademicoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PeriodoAcademicoDAO periodoDAO;

    public void init() {
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
                    insertarPeriodo(request, response);
                    break;
                case "eliminar":
                    eliminarPeriodo(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "actualizar":
                    actualizarPeriodo(request, response);
                    break;
                default:
                    listarPeriodos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarPeriodos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<PeriodoAcademico> listaPeriodos = periodoDAO.obtenerTodos();
        request.setAttribute("listaPeriodos", listaPeriodos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/periodos/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/periodos/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PeriodoAcademico periodoExistente = periodoDAO.obtenerPorId(id);
        request.setAttribute("periodo", periodoExistente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/periodos/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarPeriodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nombrePeriodo = request.getParameter("nombrePeriodo");
        Date fechaInicio = Date.valueOf(request.getParameter("fechaInicio"));
        Date fechaFin = Date.valueOf(request.getParameter("fechaFin"));
        String estado = request.getParameter("estado");

        PeriodoAcademico nuevoPeriodo = new PeriodoAcademico(0, nombrePeriodo, fechaInicio, fechaFin, estado);
        periodoDAO.crear(nuevoPeriodo);
        response.sendRedirect("periodos?action=listar");
    }

    private void actualizarPeriodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombrePeriodo = request.getParameter("nombrePeriodo");
        Date fechaInicio = Date.valueOf(request.getParameter("fechaInicio"));
        Date fechaFin = Date.valueOf(request.getParameter("fechaFin"));
        String estado = request.getParameter("estado");

        PeriodoAcademico periodo = new PeriodoAcademico(id, nombrePeriodo, fechaInicio, fechaFin, estado);
        periodoDAO.actualizar(periodo);
        response.sendRedirect("periodos?action=listar");
    }

    private void eliminarPeriodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        periodoDAO.eliminar(id);
        response.sendRedirect("periodos?action=listar");
    }
}