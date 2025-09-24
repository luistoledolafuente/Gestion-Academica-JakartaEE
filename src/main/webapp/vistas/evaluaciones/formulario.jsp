<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formulario de Evaluación</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <h3>Crear Evaluación para: <span class="text-primary"><c:out value="${curso.nombreCurso}"/></span></h3>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/evaluaciones" method="post">
                <input type="hidden" name="action" value="insertar" />
                <input type="hidden" name="idCurso" value="${curso.idCurso}" />

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre de la Evaluación:</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ej: Examen Final" required>
                </div>
                <div class="mb-3">
                    <label for="peso" class="form-label">Peso (% sobre la nota final):</label>
                    <input type="number" step="0.01" class="form-control" id="peso" name="peso" placeholder="Ej: 25.50" required>
                </div>
                <button type="submit" class="btn btn-success">Guardar Evaluación</button>
                <a href="${pageContext.request.contextPath}/evaluaciones?action=listar&idCurso=${curso.idCurso}" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>