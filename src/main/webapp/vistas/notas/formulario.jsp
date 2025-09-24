<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Notas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <h2>Registro de Notas</h2>
    <h4>Curso: <span class="text-primary"><c:out value="${detalleInfo.nombreCurso}" /></span></h4>
    <hr>

    <div class="card">
        <div class="card-header">
            Calificaciones del Alumno
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/matriculas" method="post">
                <input type="hidden" name="action" value="guardarNotas" />
                <input type="hidden" name="idDetalle" value="${idDetalle}" />
                <input type="hidden" name="idMatricula" value="${idMatricula}" />

                <c:forEach var="eval" items="${evaluaciones}">
                    <div class="row mb-3 align-items-center">
                        <div class="col-md-6">
                            <label for="nota_${eval.idEvaluacion}" class="form-label">
                                <strong><c:out value="${eval.nombre}" /></strong>
                                (<c:out value="${eval.peso}" />%)
                            </label>
                        </div>
                        <div class="col-md-6">
                                <%-- Buscamos si ya existe una nota para esta evaluación --%>
                            <c:set var="notaActual" value=""/>
                            <c:forEach var="nota" items="${notasActuales}">
                                <c:if test="${nota.nombreEvaluacion == eval.nombre}">
                                    <c:set var="notaActual" value="${nota.notaObtenida}"/>
                                </c:if>
                            </c:forEach>
                            <input type="number" step="0.01" min="0" max="20"
                                   class="form-control"
                                   id="nota_${eval.idEvaluacion}"
                                   name="nota_${eval.idEvaluacion}"
                                   value="${notaActual}"
                                   placeholder="Ingrese nota">
                        </div>
                    </div>
                </c:forEach>

                <hr>
                <button type="submit" class="btn btn-success">Guardar Todas las Notas</button>
                <a href="${pageContext.request.contextPath}/matriculas?action=verDetalle&id=${idMatricula}" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>