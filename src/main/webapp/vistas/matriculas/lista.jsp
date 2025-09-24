<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Matrículas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <h2>Módulo de Gestión de Matrículas</h2>
    <hr>
    <div class="my-3">
        <a href="${pageContext.request.contextPath}/matriculas?action=nuevo" class="btn btn-primary">Registrar Nueva Matrícula</a>
    </div>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Alumno</th>
            <th>Código Alumno</th>
            <th>Periodo</th>
            <th>Fecha de Matrícula</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="m" items="${listaMatriculas}">
            <tr>
                <td><c:out value="${m.idMatricula}" /></td>
                <td><c:out value="${m.nombreCompletoAlumno}" /></td>
                <td><c:out value="${m.codigoAlumno}" /></td>
                <td><c:out value="${m.nombrePeriodo}" /></td>
                <td><c:out value="${m.fechaMatricula}" /></td>
                <td>
                    <c:choose>
                        <c:when test="${m.estadoMatricula == 'activo'}">
                            <span class="badge bg-success">Activo</span>
                        </c:when>
                        <c:when test="${m.estadoMatricula == 'anulado'}">
                            <span class="badge bg-danger">Anulado</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-warning text-dark"><c:out value="${m.estadoMatricula}" /></span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/matriculas?action=verDetalle&id=<c:out value='${m.idMatricula}' />" class="btn btn-info btn-sm">Ver Detalles</a>
                    <a href="${pageContext.request.contextPath}/matriculas?action=anular&id=<c:out value='${m.idMatricula}' />" class="btn btn-danger btn-sm" onclick="return confirm('¿Está seguro de anular esta matrícula?');">Anular</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>