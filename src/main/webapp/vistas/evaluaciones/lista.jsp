<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Evaluaciones del Curso</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <h2>Gestión de Evaluaciones para el Curso:</h2>
    <h3 class="text-primary"><c:out value="${curso.nombreCurso}" /></h3>
    <hr>
    <a href="${pageContext.request.contextPath}/evaluaciones?action=nuevo&idCurso=${curso.idCurso}" class="btn btn-primary my-3">
        Crear Nueva Evaluación
    </a>
    <table class="table table-bordered table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nombre de la Evaluación</th>
            <th>Peso (%)</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="eval" items="${listaEvaluaciones}">
            <tr>
                <td><c:out value="${eval.idEvaluacion}" /></td>
                <td><c:out value="${eval.nombre}" /></td>
                <td><c:out value="${eval.peso}" />%</td>
                <td>
                    <a href="${pageContext.request.contextPath}/evaluaciones?action=eliminar&idEvaluacion=${eval.idEvaluacion}&idCurso=${curso.idCurso}"
                       class="btn btn-danger btn-sm" onclick="return confirm('¿Está seguro?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/cursos?action=listar" class="btn btn-secondary">Volver a la Lista de Cursos</a>
</div>
</body>
</html>