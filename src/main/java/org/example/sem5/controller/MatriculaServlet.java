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
import java.util.Map;
import java.util.HashMap;

@WebServlet("/matriculas")
public class MatriculaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MatriculaDAO matriculaDAO;
    private AlumnoDAO alumnoDAO;
    private PeriodoAcademicoDAO periodoDAO;
    private DetalleMatriculaDAO detalleMatriculaDAO;
    private NotaDAO notaDAO;
    private CursoDAO cursoDAO;

    public void init() {
        matriculaDAO = new MatriculaDAOImpl();
        alumnoDAO = new AlumnoDAOImpl();
        periodoDAO = new PeriodoAcademicoDAOImpl();
        detalleMatriculaDAO = new DetalleMatriculaDAOImpl();
        notaDAO = new NotaDAOImpl();
        cursoDAO = new CursoDAOImpl();
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
                    insertarMatricula(request, response);
                    break;
                case "anular":
                    anularMatricula(request, response);
                    break;
                case "verDetalle": // NUEVO CASE
                    verDetalleMatricula(request, response);
                    break;
                case "agregarCurso":
                    agregarCursoAMatricula(request, response);
                    break;
                case "quitarCurso":
                    quitarCursoDeMatricula(request, response);
                    break;
                default:
                    listarMatriculas(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listarMatriculas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<MatriculaInfo> listaMatriculas = matriculaDAO.obtenerTodaLaInfo();
        request.setAttribute("listaMatriculas", listaMatriculas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/matriculas/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Alumno> listaAlumnos = alumnoDAO.obtenerTodos();
        List<PeriodoAcademico> listaPeriodos = periodoDAO.obtenerTodos();

        request.setAttribute("listaAlumnos", listaAlumnos);
        request.setAttribute("listaPeriodos", listaPeriodos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/matriculas/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarMatricula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idAlumno = Integer.parseInt(request.getParameter("idAlumno"));
        int idPeriodo = Integer.parseInt(request.getParameter("idPeriodo"));
        String estado = request.getParameter("estado");

        Matricula nuevaMatricula = new Matricula();
        nuevaMatricula.setIdAlumno(idAlumno);
        nuevaMatricula.setIdPeriodo(idPeriodo);
        nuevaMatricula.setEstado(estado);
        nuevaMatricula.setFechaMatricula(new Timestamp(System.currentTimeMillis()));

        matriculaDAO.crear(nuevaMatricula);
        response.sendRedirect("matriculas?action=listar");
    }

    private void anularMatricula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Matricula matricula = matriculaDAO.obtenerPorId(id);
        if (matricula != null) {
            matricula.setEstado("anulado");
            matriculaDAO.actualizar(matricula);
        }
        response.sendRedirect("matriculas?action=listar");
    }

    private void verDetalleMatricula(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idMatricula = Integer.parseInt(request.getParameter("id"));

        // 1. Obtener la información general de la matrícula
        MatriculaInfo matriculaInfo = matriculaDAO.obtenerTodaLaInfo().stream()
                .filter(m -> m.getIdMatricula() == idMatricula)
                .findFirst().orElse(null);

        // 2. Obtener los cursos de esa matrícula (DetalleMatricula)
        List<DetalleMatriculaInfo> detalles = detalleMatriculaDAO.obtenerInfoPorIdMatricula(idMatricula);

        // 3. Para cada curso, obtener sus notas
        Map<Integer, List<NotaInfo>> notasPorDetalle = new HashMap<>();
        for (DetalleMatriculaInfo detalle : detalles) {
            List<NotaInfo> notas = notaDAO.obtenerInfoPorIdDetalle(detalle.getIdDetalle());
            notasPorDetalle.put(detalle.getIdDetalle(), notas);
        }

        List<Curso> listaCursos = cursoDAO.obtenerTodos();

        // 4. Enviar todos los datos a la vista
        request.setAttribute("matricula", matriculaInfo);
        request.setAttribute("detalles", detalles);
        request.setAttribute("notasPorDetalle", notasPorDetalle);
        request.setAttribute("listaCursos", listaCursos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/matriculas/detalle.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarCursoAMatricula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idMatricula = Integer.parseInt(request.getParameter("idMatricula"));
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));

        DetalleMatricula nuevoDetalle = new DetalleMatricula();
        nuevoDetalle.setIdMatricula(idMatricula);
        nuevoDetalle.setIdCurso(idCurso);
        nuevoDetalle.setEstado("matriculado"); // Estado por defecto

        detalleMatriculaDAO.crear(nuevoDetalle);

        // Redirigimos de vuelta a la misma página de detalle
        response.sendRedirect("matriculas?action=verDetalle&id=" + idMatricula);
    }

    private void quitarCursoDeMatricula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idDetalle = Integer.parseInt(request.getParameter("idDetalle"));
        int idMatricula = Integer.parseInt(request.getParameter("idMatricula")); // Lo necesitamos para redirigir

        detalleMatriculaDAO.eliminar(idDetalle);

        response.sendRedirect("matriculas?action=verDetalle&id=" + idMatricula);
    }
}