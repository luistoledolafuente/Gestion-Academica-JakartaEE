<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de Curso</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<jsp:include page="../common/header.jsp" />

<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <c:if test="${curso != null}"><h3>Editar Curso</h3></c:if>
            <c:if test="${curso == null}"><h3>Registrar Nuevo Curso</h3></c:if>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/cursos" method="post">
                <c:if test="${curso != null}">
                    <input type="hidden" name="action" value="actualizar" />
                    <input type="hidden" name="id" value="<c:out value='${curso.idCurso}' />" />
                </c:if>
                <c:if test="${curso == null}">
                    <input type="hidden" name="action" value="insertar" />
                </c:if>

                <div class="mb-3">
                    <label for="codigoCurso" class="form-label">Código del Curso:</label>
                    <input type="text" class="form-control" id="codigoCurso" name="codigoCurso" value="<c:out value='${curso.codigoCurso}' />" required>
                </div>
                <div class="mb-3">
                    <label for="nombreCurso" class="form-label">Nombre del Curso:</label>
                    <input type="text" class="form-control" id="nombreCurso" name="nombreCurso" value="<c:out value='${curso.nombreCurso}' />" required>
                </div>
                <div class="mb-3">
                    <label for="creditos" class="form-label">Créditos:</label>
                    <input type="number" class="form-control" id="creditos" name="creditos" value="<c:out value='${curso.creditos}' />" required>
                </div>
                <button type="submit" class="btn btn-success">Guardar</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>