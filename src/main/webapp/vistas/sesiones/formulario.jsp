<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formulario de Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <h3>Crear Nueva Sesión de Clase</h3>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/sesiones" method="post">
                <input type="hidden" name="action" value="insertar" />
                <input type="hidden" name="idCurso" value="${idCurso}" />
                <input type="hidden" name="idPeriodo" value="${idPeriodo}" />

                <div class="mb-3">
                    <label for="fecha" class="form-label">Fecha y Hora de la Sesión:</label>
                    <input type="datetime-local" class="form-control" id="fecha" name="fecha" required>
                </div>
                <div class="mb-3">
                    <label for="tema" class="form-label">Tema a tratar en la sesión:</label>
                    <input type="text" class="form-control" id="tema" name="tema" placeholder="Ej: Introducción a JDBC" required>
                </div>
                <button type="submit" class="btn btn-success">Guardar Sesión</button>
                <a href="${pageContext.request.contextPath}/sesiones?action=listar&idCurso=${idCurso}&idPeriodo=${idPeriodo}" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>