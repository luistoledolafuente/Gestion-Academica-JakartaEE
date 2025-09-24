<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Asistencia</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <h2>Registro de Asistencia</h2>
    <h4>Sesión del:
        <span class="text-primary">
                <fmt:formatDate value="${sesion.fecha}" pattern="EEEE, dd 'de' MMMM 'de' yyyy, hh:mm a" />
            </span>
    </h4>
    <h5>Tema: <span class="text-secondary"><c:out value="${sesion.tema}" /></span></h5>
    <hr>

    <form action="${pageContext.request.contextPath}/asistencia?action=guardar" method="post">
        <input type="hidden" name="idSesion" value="${sesion.idSesion}">
        <input type="hidden" name="idMatricula" value="${idMatricula}">

        <table class="table table-striped table-hover">
            <thead class="table-light">
            <tr>
                <th>Alumno</th>
                <th class="text-center">Asistió</th>
                <th class="text-center">Tardanza</th>
                <th class="text-center">Falta</th>
                <th class="text-center">Justificada</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="detalle" items="${alumnosMatriculados}">
                <tr>
                    <td><c:out value="${detalle.nombreCurso}"/></td>
                    <c:set var="estadoActual" value="${mapaAsistencias[detalle.idDetalle]}"/>

                    <td class="text-center">
                        <input class="form-check-input" type="radio" name="estado_${detalle.idDetalle}" value="asistio" ${estadoActual == 'asistio' ? 'checked' : ''} required>
                    </td>
                    <td class="text-center">
                        <input class="form-check-input" type="radio" name="estado_${detalle.idDetalle}" value="tardanza" ${estadoActual == 'tardanza' ? 'checked' : ''}>
                    </td>
                    <td class="text-center">
                        <input class="form-check-input" type="radio" name="estado_${detalle.idDetalle}" value="falta" ${estadoActual == 'falta' ? 'checked' : ''}>
                    </td>
                    <td class="text-center">
                        <input class="form-check-input" type="radio" name="estado_${detalle.idDetalle}" value="justificada" ${estadoActual == 'justificada' ? 'checked' : ''}>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <hr>
        <button type="submit" class="btn btn-success">Guardar Asistencia</button>
        <a href="${pageContext.request.contextPath}/matriculas?action=verDetalle&id=${idMatricula}" class="btn btn-secondary">Volver al Detalle</a>
    </form>
</div>
</body>
</html>