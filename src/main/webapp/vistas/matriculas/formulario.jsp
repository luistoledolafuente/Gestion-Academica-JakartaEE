<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formulario de Matrícula</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <div class="card">
        <div class="card-header"><h3>Registrar Nueva Matrícula</h3></div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/matriculas?action=insertar" method="post">
                <div class="mb-3">
                    <label class="form-label">Seleccionar Alumno:</label>
                    <select name="idAlumno" class="form-select" required>
                        <option value="">-- Elija un alumno --</option>
                        <c:forEach var="alumno" items="${listaAlumnos}">
                            <option value="${alumno.idAlumno}"><c:out value="${alumno.apellidos}, ${alumno.nombres}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Seleccionar Periodo:</label>
                    <select name="idPeriodo" class="form-select" required>
                        <option value="">-- Elija un periodo --</option>
                        <c:forEach var="periodo" items="${listaPeriodos}">
                            <option value="${periodo.idPeriodo}"><c:out value="${periodo.nombrePeriodo}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Estado de la Matrícula:</label>
                    <select name="estado" class="form-select">
                        <option value="activo">Activo</option>
                        <option value="retirado">Retirado</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-success">Registrar Matrícula</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>