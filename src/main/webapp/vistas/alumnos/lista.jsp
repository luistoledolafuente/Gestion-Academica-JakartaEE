<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Alumnos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../common/header.jsp" />

<div class="container mt-4">
    <h2>Módulo de Gestión de Alumnos</h2>
    <hr>

    <div class="my-3">
        <a href="${pageContext.request.contextPath}/alumnos?action=nuevo" class="btn btn-primary">Agregar Nuevo Alumno</a>
    </div>

    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Código</th>
            <th>Nombres</th>
            <th>Apellidos</th>
            <th>Email</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="alumno" items="${listaAlumnos}">
            <tr>
                <td><c:out value="${alumno.idAlumno}" /></td>
                <td><c:out value="${alumno.codigo}" /></td>
                <td><c:out value="${alumno.nombres}" /></td>
                <td><c:out value="${alumno.apellidos}" /></td>
                <td><c:out value="${alumno.email}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/alumnos?action=editar&id=<c:out value='${alumno.idAlumno}' />" class="btn btn-warning btn-sm">Editar</a>
                    &nbsp;
                    <a href="${pageContext.request.contextPath}/alumnos?action=eliminar&id=<c:out value='${alumno.idAlumno}' />" class="btn btn-danger btn-sm" onclick="return confirm('¿Está seguro de que desea eliminar este alumno?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>