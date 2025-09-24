<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sesiones de Clase</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <h2>Gestión de Sesiones de Clase</h2>
    <h4>Curso: <span class="text-primary"><c:out value="${curso.nombreCurso}" /></span></h4>
    <h5>Periodo: <span class="text-secondary"><c:out value="${periodo.nombrePeriodo}" /></span></h5>
    <hr>
    <a href="${pageContext.request.contextPath}/sesiones?action=nuevo&idCurso=${curso.idCurso}&idPeriodo=${periodo.idPeriodo}" class="btn btn-primary my-3">
        Crear Nueva Sesión
    </a>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>Fecha y Hora</th>
            <th>Tema de la Sesión</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="sesion" items="${listaSesiones}">
            <tr>
                <td>
                    <fmt:formatDate value="${sesion.fecha}" pattern="EEEE, dd 'de' MMMM 'de' yyyy, hh:mm a" />
                </td>
                <td><c:out value="${sesion.tema}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/asistencia?action=mostrarFormulario&idSesion=${sesion.idSesion}&idMatricula=${idMatricula}"
                       class="btn btn-success btn-sm">Pasar Asistencia</a>
                    <a href="${pageContext.request.contextPath}/sesiones?action=eliminar&idSesion=${sesion.idSesion}&idCurso=${curso.idCurso}&idPeriodo=${periodo.idPeriodo}"
                       class="btn btn-danger btn-sm" onclick="return confirm('¿Está seguro?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/matriculas?action=listar" class="btn btn-secondary">Volver a Matrículas</a>
</div>
</body>
</html>