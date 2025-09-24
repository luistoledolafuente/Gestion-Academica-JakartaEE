package org.example.sem5.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sem5.dao.AlumnoDAO;
import org.example.sem5.dao.impl.AlumnoDAOImpl;
import org.example.sem5.model.Alumno;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// La anotación @WebServlet define la URL base para este servlet
@WebServlet("/alumnos")
public class AlumnoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Instancia del DAO que usaremos. Se crea una sola vez.
    private AlumnoDAO alumnoDAO;

    // El método init() se ejecuta una sola vez cuando el servlet se carga por primera vez.
    public void init() {
        alumnoDAO = new AlumnoDAOImpl();
    }

    // doPost maneja las peticiones POST (ej: enviar un formulario de creación/edición)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Redirigimos al doGet para centralizar la lógica
    }

    // doGet maneja las peticiones GET (ej: hacer clic en un enlace)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtenemos el parámetro 'action' de la URL
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar"; // Acción por defecto
        }

        try {
            // Un switch para decidir qué método ejecutar según la acción
            switch (action) {
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "insertar":
                    insertarAlumno(request, response);
                    break;
                case "eliminar":
                    eliminarAlumno(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "actualizar":
                    actualizarAlumno(request, response);
                    break;
                default:
                    listarAlumnos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarAlumnos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        // 1. Obtenemos la lista de alumnos desde el DAO
        List<Alumno> listaAlumnos = alumnoDAO.obtenerTodos();
        // 2. Guardamos la lista en el objeto 'request' para que el JSP pueda acceder a ella
        request.setAttribute("listaAlumnos", listaAlumnos);
        // 3. Enviamos la petición al archivo JSP para que renderice la vista
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/alumnos/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/alumnos/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Alumno alumnoExistente = alumnoDAO.obtenerPorId(id);
        request.setAttribute("alumno", alumnoExistente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/alumnos/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insertarAlumno(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String codigo = request.getParameter("codigo");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");

        Alumno nuevoAlumno = new Alumno();
        nuevoAlumno.setCodigo(codigo);
        nuevoAlumno.setNombres(nombres);
        nuevoAlumno.setApellidos(apellidos);
        nuevoAlumno.setEmail(email);

        alumnoDAO.crear(nuevoAlumno);
        // Redirigimos al listado para ver el nuevo alumno
        response.sendRedirect("alumnos?action=listar");
    }

    private void actualizarAlumno(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String codigo = request.getParameter("codigo");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");

        Alumno alumno = new Alumno();
        alumno.setIdAlumno(id);
        alumno.setCodigo(codigo);
        alumno.setNombres(nombres);
        alumno.setApellidos(apellidos);
        alumno.setEmail(email);

        alumnoDAO.actualizar(alumno);
        response.sendRedirect("alumnos?action=listar");
    }

    private void eliminarAlumno(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        alumnoDAO.eliminar(id);
        response.sendRedirect("alumnos?action=listar");
    }
}