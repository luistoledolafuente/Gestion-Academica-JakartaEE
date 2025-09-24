<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de Alumno</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../common/header.jsp" />

<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <c:if test="${alumno != null}">
                <h3>Editar Alumno</h3>
            </c:if>
            <c:if test="${alumno == null}">
                <h3>Registrar Nuevo Alumno</h3>
            </c:if>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/alumnos" method="post">

                <c:if test="${alumno != null}">
                    <input type="hidden" name="action" value="actualizar" />
                    <input type="hidden" name="id" value="<c:out value='${alumno.idAlumno}' />" />
                </c:if>
                <c:if test="${alumno == null}">
                    <input type="hidden" name="action" value="insertar" />
                </c:if>

                <div class="mb-3">
                    <label for="codigo" class="form-label">Código:</label>
                    <input type="text" class="form-control" id="codigo" name="codigo" value="<c:out value='${alumno.codigo}' />" required>
                </div>

                <div class="mb-3">
                    <label for="nombres" class="form-label">Nombres:</label>
                    <input type="text" class="form-control" id="nombres" name="nombres" value="<c:out value='${alumno.nombres}' />" required>
                </div>

                <div class="mb-3">
                    <label for="apellidos" class="form-label">Apellidos:</label>
                    <input type="text" class="form-control" id="apellidos" name="apellidos" value="<c:out value='${alumno.apellidos}' />" required>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" class="form-control" id="email" name="email" value="<c:out value='${alumno.email}' />" required>
                </div>

                <button type="submit" class="btn btn-success">Guardar Cambios</button>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>