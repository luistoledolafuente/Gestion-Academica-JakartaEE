<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Cursos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../common/header.jsp" />

<div class="container mt-4">
    <h2>Módulo de Gestión de Cursos</h2>
    <hr>

    <div class="my-3">
        <a href="${pageContext.request.contextPath}/cursos?action=nuevo" class="btn btn-primary">Agregar Nuevo Curso</a>
    </div>

    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Código del Curso</th>
            <th>Nombre del Curso</th>
            <th>Créditos</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="curso" items="${listaCursos}">
            <tr>
                <td><c:out value="${curso.idCurso}" /></td>
                <td><c:out value="${curso.codigoCurso}" /></td>
                <td><c:out value="${curso.nombreCurso}" /></td>
                <td><c:out value="${curso.creditos}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/cursos?action=editar&id=<c:out value='${curso.idCurso}' />" class="btn btn-warning btn-sm">Editar</a>
                    &nbsp;
                    <a href="${pageContext.request.contextPath}/evaluaciones?action=listar&idCurso=<c:out value='${curso.idCurso}' />" class="btn btn-info btn-sm">Evaluaciones</a>
                    &nbsp;
                    <a href="${pageContext.request.contextPath}/cursos?action=eliminar&id=<c:out value='${curso.idCurso}' />" class="btn btn-danger btn-sm" onclick="return confirm('¿Está seguro?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>