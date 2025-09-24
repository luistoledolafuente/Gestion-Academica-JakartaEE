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
import java.math.BigDecimal;
import java.math.RoundingMode; // para redondear promedios
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
                case "verDetalle":
                    verDetalleMatricula(request, response);
                    break;
                case "agregarCurso":
                    agregarCursoAMatricula(request, response);
                    break;
                case "quitarCurso":
                    quitarCursoDeMatricula(request, response);
                    break;

                // ---- NUEVAS ACCIONES PARA NOTAS ----
                case "mostrarFormularioNotas":
                    mostrarFormularioNotas(request, response);
                    break;
                case "guardarNotas":
                    guardarNotas(request, response);
                    break;

                default:
                    listarMatriculas(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // ==================== MÉTODOS EXISTENTES ====================

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

    // ==================== MÉTODO MEJORADO ====================

    private void verDetalleMatricula(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idMatricula = Integer.parseInt(request.getParameter("id"));

        MatriculaInfo matriculaInfo = matriculaDAO.obtenerTodaLaInfo().stream()
                .filter(m -> m.getIdMatricula() == idMatricula)
                .findFirst().orElse(null);

        List<DetalleMatriculaInfo> detalles = detalleMatriculaDAO.obtenerInfoPorIdMatricula(idMatricula);
        List<Curso> listaCursos = cursoDAO.obtenerTodos();

        Map<Integer, List<NotaInfo>> notasPorDetalle = new HashMap<>();
        Map<Integer, BigDecimal> promediosPorDetalle = new HashMap<>(); // mapa de promedios

        for (DetalleMatriculaInfo detalle : detalles) {
            List<NotaInfo> notas = notaDAO.obtenerInfoPorIdDetalle(detalle.getIdDetalle());
            notasPorDetalle.put(detalle.getIdDetalle(), notas);

            if (!notas.isEmpty()) {
                BigDecimal sumaPonderada = BigDecimal.ZERO;
                BigDecimal sumaPesos = BigDecimal.ZERO;

                for (NotaInfo nota : notas) {
                    BigDecimal notaPonderada = nota.getNotaObtenida().multiply(nota.getPesoEvaluacion());
                    sumaPonderada = sumaPonderada.add(notaPonderada);
                    sumaPesos = sumaPesos.add(nota.getPesoEvaluacion());
                }

                if (sumaPesos.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal promedioFinal = sumaPonderada.divide(sumaPesos, 2, RoundingMode.HALF_UP);
                    promediosPorDetalle.put(detalle.getIdDetalle(), promedioFinal);
                }
            }
        }

        // --- atributos para la vista ---
        request.setAttribute("matricula", matriculaInfo);
        request.setAttribute("detalles", detalles);
        request.setAttribute("notasPorDetalle", notasPorDetalle);
        request.setAttribute("listaCursos", listaCursos);
        request.setAttribute("promediosPorDetalle", promediosPorDetalle);

        // --- enviar también el idPeriodo ---
        int idPeriodo = periodoDAO.obtenerTodos().stream()
                .filter(p -> p.getNombrePeriodo().equals(matriculaInfo.getNombrePeriodo()))
                .findFirst()
                .get()
                .getIdPeriodo();
        request.setAttribute("idPeriodo", idPeriodo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/matriculas/detalle.jsp");
        dispatcher.forward(request, response);
    }


    private void agregarCursoAMatricula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idMatricula = Integer.parseInt(request.getParameter("idMatricula"));
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));

        DetalleMatricula nuevoDetalle = new DetalleMatricula();
        nuevoDetalle.setIdMatricula(idMatricula);
        nuevoDetalle.setIdCurso(idCurso);
        nuevoDetalle.setEstado("matriculado");

        detalleMatriculaDAO.crear(nuevoDetalle);

        response.sendRedirect("matriculas?action=verDetalle&id=" + idMatricula);
    }

    private void quitarCursoDeMatricula(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idDetalle = Integer.parseInt(request.getParameter("idDetalle"));
        int idMatricula = Integer.parseInt(request.getParameter("idMatricula"));

        detalleMatriculaDAO.eliminar(idDetalle);

        response.sendRedirect("matriculas?action=verDetalle&id=" + idMatricula);
    }

    // ==================== NUEVOS MÉTODOS PARA NOTAS ====================

    private void mostrarFormularioNotas(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int idDetalle = Integer.parseInt(request.getParameter("idDetalle"));
        int idMatricula = Integer.parseInt(request.getParameter("idMatricula"));

        List<DetalleMatriculaInfo> detalles = detalleMatriculaDAO.obtenerInfoPorIdMatricula(idMatricula);
        DetalleMatriculaInfo detalleInfo = detalles.stream()
                .filter(d -> d.getIdDetalle() == idDetalle)
                .findFirst().orElse(null);

        if (detalleInfo == null) {
            response.sendRedirect("matriculas?action=verDetalle&id=" + idMatricula);
            return;
        }

        int idCurso = cursoDAO.obtenerTodos().stream()
                .filter(c -> c.getNombreCurso().equals(detalleInfo.getNombreCurso()))
                .findFirst().get().getIdCurso();

        List<Evaluacion> evaluaciones = new EvaluacionDAOImpl().obtenerPorIdCurso(idCurso);
        List<NotaInfo> notasActuales = notaDAO.obtenerInfoPorIdDetalle(idDetalle);

        request.setAttribute("detalleInfo", detalleInfo);
        request.setAttribute("evaluaciones", evaluaciones);
        request.setAttribute("notasActuales", notasActuales);
        request.setAttribute("idDetalle", idDetalle);
        request.setAttribute("idMatricula", idMatricula);

        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/notas/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void guardarNotas(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int idDetalle = Integer.parseInt(request.getParameter("idDetalle"));
        int idMatricula = Integer.parseInt(request.getParameter("idMatricula"));

        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("nota_")) {
                int idEvaluacion = Integer.parseInt(paramName.substring(5));
                String valorNota = request.getParameter(paramName);

                if (valorNota != null && !valorNota.trim().isEmpty()) {
                    Nota nota = new Nota();
                    nota.setIdDetalle(idDetalle);
                    nota.setIdEvaluacion(idEvaluacion);
                    nota.setNota(new BigDecimal(valorNota));

                    notaDAO.crearOActualizar(nota);
                }
            }
        }

        response.sendRedirect("matriculas?action=verDetalle&id=" + idMatricula);
    }
}
