<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Matrícula</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <h2>Detalle de Matrícula</h2>
    <hr>

    <!-- Información General -->
    <div class="card mb-4">
        <div class="card-header">Información General</div>
        <div class="card-body">
            <p><strong>Alumno:</strong> <c:out value="${matricula.nombreCompletoAlumno}" /></p>
            <p><strong>Código:</strong> <c:out value="${matricula.codigoAlumno}" /></p>
            <p><strong>Periodo:</strong> <c:out value="${matricula.nombrePeriodo}" /></p>
            <p><strong>Estado:</strong>
                <span class="badge bg-success"><c:out value="${matricula.estadoMatricula}" /></span>
            </p>
        </div>
    </div>

    <!-- Agregar Curso -->
    <div class="card mb-4">
        <div class="card-header">Agregar Curso a la Matrícula</div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/matriculas" method="post">
                <input type="hidden" name="action" value="agregarCurso" />
                <input type="hidden" name="idMatricula" value="${matricula.idMatricula}" />
                <div class="row">
                    <div class="col-md-8">
                        <label for="idCurso" class="form-label">Seleccione un curso:</label>
                        <select name="idCurso" id="idCurso" class="form-select" required>
                            <option value="">-- Cursos Disponibles --</option>
                            <c:forEach var="curso" items="${listaCursos}">
                                <option value="${curso.idCurso}">
                                    <c:out value="${curso.nombreCurso}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary w-100">Agregar Curso</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Cursos Matriculados y Notas -->
    <h3>Cursos Matriculados y Notas</h3>
    <c:forEach var="detalle" items="${detalles}">
        <div class="card mb-3">
            <div class="card-header bg-light d-flex justify-content-between align-items-center">
                <h4>
                    <c:out value="${detalle.nombreCurso}" />
                    (<c:out value="${detalle.creditosCurso}" /> créditos)
                </h4>
                <a href="${pageContext.request.contextPath}/matriculas?action=quitarCurso&idDetalle=${detalle.idDetalle}&idMatricula=${matricula.idMatricula}"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('¿Está seguro de quitar este curso de la matrícula?');">
                    Quitar Curso
                </a>
            </div>
            <div class="card-body">
                <h5>Notas Registradas:</h5>
                <c:set var="notas" value="${notasPorDetalle[detalle.idDetalle]}" />

                <c:if test="${empty notas}">
                    <p class="text-muted">Aún no hay notas registradas para este curso.</p>
                </c:if>

                <c:if test="${not empty notas}">
                    <table class="table table-sm table-striped">
                        <thead>
                        <tr>
                            <th>Evaluación</th>
                            <th>Peso (%)</th>
                            <th>Nota</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="nota" items="${notas}">
                            <tr>
                                <td><c:out value="${nota.nombreEvaluacion}" /></td>
                                <td><c:out value="${nota.pesoEvaluacion}" />%</td>
                                <td><c:out value="${nota.notaObtenida}" /></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <!-- Mostrar Promedio Ponderado -->
                    <c:set var="promedio" value="${promediosPorDetalle[detalle.idDetalle]}" />
                    <c:if test="${promedio != null}">
                        <div class="alert alert-success mt-3" role="alert">
                            <strong>Promedio Ponderado Final: </strong>
                            <span class="fs-5 fw-bold">
                                <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${promedio}" />
                            </span>
                        </div>
                    </c:if>
                </c:if>

                <!-- Botones de acción -->
                <a href="${pageContext.request.contextPath}/matriculas?action=mostrarFormularioNotas&idDetalle=${detalle.idDetalle}&idMatricula=${matricula.idMatricula}"
                   class="btn btn-primary btn-sm mt-2">Registrar/Editar Nota</a>

                <!-- Aquí ya fusionado correctamente con idCurso e idPeriodo -->
                <a href="${pageContext.request.contextPath}/sesiones?action=listar&idCurso=${detalle.idCurso}&idPeriodo=${matricula.idPeriodo}&idMatricula=${matricula.idMatricula}"
                   class="btn btn-outline-secondary btn-sm mt-2">Gestionar Sesiones de Clase</a>

            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
