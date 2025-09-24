<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Gestión Académica</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
    </style>
</head>
<body>

<jsp:include page="vistas/common/header.jsp" />

<main class="container mt-5">
    <div class="p-5 mb-4 bg-light rounded-3">
        <div class="container-fluid py-5">
            <h1 class="display-5 fw-bold">Bienvenido al Sistema de Gestión Académica</h1>
            <p class="col-md-8 fs-4">Utilice la barra de navegación o las tarjetas de acceso rápido a continuación para administrar los diferentes módulos del sistema.</p>
        </div>
    </div>

    <div class="row align-items-md-stretch">
        <div class="col-md-6 mb-4">
            <div class="h-100 p-5 text-white bg-primary rounded-3">
                <h2>Gestión de Alumnos</h2>
                <p>Administrar altas, bajas y modificaciones de los datos de los alumnos.</p>
                <a href="${pageContext.request.contextPath}/alumnos?action=listar" class="btn btn-outline-light" type="button">Ir a Alumnos</a>
            </div>
        </div>
        <div class="col-md-6 mb-4">
            <div class="h-100 p-5 bg-secondary text-white rounded-3">
                <h2>Gestión de Cursos</h2>
                <p>Crear, editar y eliminar los cursos que se ofrecen en la institución.</p>
                <a href="${pageContext.request.contextPath}/cursos?action=listar" class="btn btn-outline-light" type="button">Ir a Cursos</a>
            </div>
        </div>
        <div class="col-md-6 mb-4">
            <div class="h-100 p-5 bg-success text-white rounded-3">
                <h2>Gestión de Periodos</h2>
                <p>Administrar los ciclos o periodos académicos, como "2025-I" o "2025-II".</p>
                <a href="${pageContext.request.contextPath}/periodos?action=listar" class="btn btn-outline-light" type="button">Ir a Periodos</a>
            </div>
        </div>
        <div class="col-md-6 mb-4">
            <div class="h-100 p-5 bg-info text-white rounded-3">
                <h2>Proceso de Matrícula</h2>
                <p>Gestionar la inscripción de alumnos en los cursos de cada periodo académico.</p>
                <a href="${pageContext.request.contextPath}/matriculas?action=listar" class="btn btn-outline-light" type="button">Ir a Matrículas</a>
            </div>
        </div>
    </div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>