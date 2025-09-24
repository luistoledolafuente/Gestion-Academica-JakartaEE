<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Gestión Académica</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <c:if test="${not empty sessionScope.usuario}">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/alumnos?action=listar">Alumnos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/cursos?action=listar">Cursos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/periodos?action=listar">Periodos</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/matriculas?action=listar">Matrículas</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#">Reportes</a>
                    </li>
                </ul>
            </c:if>

            <ul class="navbar-nav ms-auto">
                <c:choose>
                    <%-- CASO 1: El usuario SÍ ha iniciado sesión --%>
                    <c:when test="${not empty sessionScope.usuario}">
                        <li class="nav-item">
                            <a class="nav-link disabled" href="#">
                                Bienvenido, <c:out value="${sessionScope.usuario.username}" />
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
                        </li>
                    </c:when>

                    <%-- CASO 2: El usuario NO ha iniciado sesión --%>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">Iniciar Sesión</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>