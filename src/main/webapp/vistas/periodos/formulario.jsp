<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Formulario de Periodo Académico</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <c:if test="${periodo != null}"><h3>Editar Periodo Académico</h3></c:if>
            <c:if test="${periodo == null}"><h3>Registrar Nuevo Periodo Académico</h3></c:if>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/periodos" method="post">

                <c:if test="${periodo != null}">
                    <input type="hidden" name="action" value="actualizar" />
                    <input type="hidden" name="id" value="<c:out value='${periodo.idPeriodo}' />" />
                </c:if>
                <c:if test="${periodo == null}">
                    <input type="hidden" name="action" value="insertar" />
                </c:if>

                <div class="mb-3">
                    <label for="nombrePeriodo" class="form-label">Nombre del Periodo:</label>
                    <input type="text" class="form-control" id="nombrePeriodo" name="nombrePeriodo" value="<c:out value='${periodo.nombrePeriodo}' />" placeholder="Ej: 2025-II" required>
                </div>
                <div class="mb-3">
                    <label for="fechaInicio" class="form-label">Fecha de Inicio:</label>
                    <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" value="${periodo.fechaInicio}" required>
                </div>
                <div class="mb-3">
                    <label for="fechaFin" class="form-label">Fecha de Fin:</label>
                    <input type="date" class="form-control" id="fechaFin" name="fechaFin" value="${periodo.fechaFin}" required>
                </div>
                <div class="mb-3">
                    <label for="estado" class="form-label">Estado:</label>
                    <select id="estado" name="estado" class="form-select">
                        <option value="activo" ${periodo.estado == 'activo' ? 'selected' : ''}>Activo</option>
                        <option value="inactivo" ${periodo.estado == 'inactivo' ? 'selected' : ''}>Inactivo</option>
                        <option value="cerrado" ${periodo.estado == 'cerrado' ? 'selected' : ''}>Cerrado</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-success">Guardar Periodo</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>