<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Gestión de Periodos Académicos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <h2>Módulo de Periodos Académicos</h2>
    <hr>
    <a href="${pageContext.request.contextPath}/periodos?action=nuevo" class="btn btn-primary my-3">Crear Nuevo Periodo</a>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nombre (Ej: 2025-I)</th>
            <th>Fecha Inicio</th>
            <th>Fecha Fin</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="periodo" items="${listaPeriodos}">
            <tr>
                <td><c:out value="${periodo.idPeriodo}" /></td>
                <td><c:out value="${periodo.nombrePeriodo}" /></td>
                <td><c:out value="${periodo.fechaInicio}" /></td>
                <td><c:out value="${periodo.fechaFin}" /></td>
                <td><span class="badge bg-info text-dark"><c:out value="${periodo.estado}" /></span></td>
                <td>
                    <a href="${pageContext.request.contextPath}/periodos?action=editar&id=<c:out value='${periodo.idPeriodo}' />" class="btn btn-warning btn-sm">Editar</a>
                    &nbsp;
                    <a href="${pageContext.request.contextPath}/periodos?action=eliminar&id=<c:out value='${periodo.idPeriodo}' />" class="btn btn-danger btn-sm" onclick="return confirm('¿Está seguro de eliminar este periodo?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>