<%@ page import="java.util.List" %>
<%@ page import="mx.edu.utez.biblioteca.biblioteca.model.BeanLibro " %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: CA2-PC-
  Date: 28/06/2022
  Time: 12:58 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body background="https://www.guillermocinta.com/wp-content/uploads/2021/04/La%20Cr%C3%B3nica%20de%20Morelos-prepara-la-utez-clases-presenciales-con-aforo-de-50-de-alumnos-en-una-primera-etapa.jpg" style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative;">
<c:if test="${role!=2}">
    <!--Barra de navegación-->
    <nav class="navbar bg-light fixed-top">
        <div class="container-fluid">
            <!--Boton de despliegue de menú-->
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="col text-center">
                <a class="navbar-brand mb-0 h1" href="menu" style="color: #002e60; font-size: x-large;"><img src="ServletImages?id_producto=72" class=" img-thumbnail" style="height:40px;width: 120px" alt="...">                <br>
                Sistema de gestión bibliotecario
            </div>
            <img src="https://upload.wikimedia.org/wikipedia/commons/5/54/Logo-utez.png" style="width: 2.6cm; height: 50px;">
            <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                <!--Título del menú desplegable-->
                <div class="offcanvas-header">
                    <h4 class="offcanvas-title" id="offcanvasNavbarLabel">Menú</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <!--Cuerpo del menú desplegable-->
                <div class="offcanvas-body">
                    <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
                        <!--Menu-->
                        <li class="nav-item">
                            <a class="nav-link" href="menu">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6zm5-.793V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
                                    <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
                                </svg> &nbsp; &nbsp;Inicio
                            </a>
                        </li>
                        <!--Usuario-->
                        <li class="nav-item">
                            <a class="nav-link" href="perfil">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
                                    <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                                </svg> &nbsp; &nbsp;Perfil
                            </a>
                        </li>
                        <!--Libros-->
                        <li class="nav-item">
                            <a class="nav-link" href="list-libros">
                                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-book" viewBox="0 0 16 16">
                                    <path d="M1 2.828c.885-.37 2.154-.769 3.388-.893 1.33-.134 2.458.063 3.112.752v9.746c-.935-.53-2.12-.603-3.213-.493-1.18.12-2.37.461-3.287.811V2.828zm7.5-.141c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                                </svg> &nbsp; &nbsp; Libros
                            </a>
                            <!--Salas-->
                        <li class="nav-item">
                            <a class="nav-link" href="list-salas">
                                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-door-closed" viewBox="0 0 16 16">
                                    <path d="M3 2a1 1 0 0 1 1-1h8a1 1 0 0 1 1 1v13h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V2zm1 13h8V2H4v13z"/>
                                    <path d="M9 9a1 1 0 1 0 2 0 1 1 0 0 0-2 0z"/>
                                </svg> &nbsp; &nbsp; Salas
                            </a>
                        </li>
                        <!--Cerrar sesión-->
                        <li><hr class="divider"></li>
                        <li class="nav-item">
                            <a class="nav-link" href="logout">
                                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-box-arrow-left" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
                                    <path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
                                </svg> &nbsp; &nbsp; Cerrar sesión
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</c:if>
<c:if test="${role==2}">
    <!--Barra de navegación-->
    <nav class="navbar bg-light fixed-top">
        <div class="container-fluid">
            <!--Boton de despliegue de menú-->
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="col text-center">
                <a class="navbar-brand mb-0 h1" href="menu" style="color: #002e60; font-size: x-large;"><img src="ServletImages?id_producto=72" class=" img-thumbnail" style="height:40px;width: 120px" alt="...">
                <br>
                Sistema de gestión bibliotecario
            </div>
            <img src="https://upload.wikimedia.org/wikipedia/commons/5/54/Logo-utez.png" style="width: 2.6cm; height: 50px;">
            <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                <!--Título del menú desplegable-->
                <div class="offcanvas-header">
                    <h4 class="offcanvas-title" id="offcanvasNavbarLabel">Menú</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <!--Cuerpo del menú desplegable-->
                <div class="offcanvas-body">
                    <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
                        <!--Menu-->
                        <li class="nav-item">
                            <a class="nav-link" href="menu">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6zm5-.793V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
                                    <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
                                </svg> &nbsp; &nbsp;Inicio
                            </a>
                        </li>
                        <!--Opciones de usuario-->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person" viewBox="0 0 16 16">
                                    <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                                </svg> &nbsp; &nbsp;Usuarios
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="list-usuarios">Consultar</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="crear-usuario-alumno">Agregar alumno</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="crear-usuario-docente">Agregar docente</a></li>
                            </ul>
                        </li>
                        <!--Opciones de libros-->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-book" viewBox="0 0 16 16">
                                    <path d="M1 2.828c.885-.37 2.154-.769 3.388-.893 1.33-.134 2.458.063 3.112.752v9.746c-.935-.53-2.12-.603-3.213-.493-1.18.12-2.37.461-3.287.811V2.828zm7.5-.141c.654-.689 1.782-.886 3.112-.752 1.234.124 2.503.523 3.388.893v9.923c-.918-.35-2.107-.692-3.287-.81-1.094-.111-2.278-.039-3.213.492V2.687zM8 1.783C7.015.936 5.587.81 4.287.94c-1.514.153-3.042.672-3.994 1.105A.5.5 0 0 0 0 2.5v11a.5.5 0 0 0 .707.455c.882-.4 2.303-.881 3.68-1.02 1.409-.142 2.59.087 3.223.877a.5.5 0 0 0 .78 0c.633-.79 1.814-1.019 3.222-.877 1.378.139 2.8.62 3.681 1.02A.5.5 0 0 0 16 13.5v-11a.5.5 0 0 0-.293-.455c-.952-.433-2.48-.952-3.994-1.105C10.413.809 8.985.936 8 1.783z"/>
                                </svg> &nbsp; &nbsp; Libros
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="list-libros">Consultar</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="agregar-libro">Agregar</a></li>
                            </ul>
                        </li>
                        <!--Opciones de salas-->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-door-closed" viewBox="0 0 16 16">
                                    <path d="M3 2a1 1 0 0 1 1-1h8a1 1 0 0 1 1 1v13h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V2zm1 13h8V2H4v13z"/>
                                    <path d="M9 9a1 1 0 1 0 2 0 1 1 0 0 0-2 0z"/>
                                </svg> &nbsp; &nbsp; Salas
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="list-salas">Consultar</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="agregar-sala">Agregar</a></li>
                            </ul>
                        </li>

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-calendar-range" viewBox="0 0 16 16">
                                    <path d="M9 7a1 1 0 0 1 1-1h5v2h-5a1 1 0 0 1-1-1zM1 9h4a1 1 0 0 1 0 2H1V9z"/>
                                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
                                </svg> &nbsp; &nbsp; Préstamos
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="regresar-libro">Devolver préstamo</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="list-pedidos?status=0">Préstamos pendientes</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="list-pedidos?status=1">Préstamos en curso</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="list-pedidos?status=2">Préstamos rechazados</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="list-pedidos?status=3">Historial</a></li>
                            </ul>
                        </li>
                        <li><hr class="divider"></li>
                        <li class="nav-item">
                            <a class="nav-link" href="logout">
                                <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" class="bi bi-box-arrow-left" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
                                    <path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
                                </svg> &nbsp; &nbsp; Cerrar sesión
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</c:if>
<div class="container">
                <<div class="row " style="margin-top: 100px;">
                    <div class="col-12 mb-12 mt-12 ">
                        <form action="list-libros" method="get">
                            <div class="row justify-content-center">
                                <div class="col col-1">
                                </div>
                                <div class="col col-3">
                                    <input type="text" class="form-control" placeholder="Nombre de libro" aria-label="First name" name="nombre">
                                </div>
                                <div class="col col-3">
                                    <input type="text" class="form-control" placeholder="Autor" aria-label="Last name" name="autor">
                                </div>
                                <div class="col col-3">
                                    <input type="text" class="form-control" placeholder="Categoria" aria-label="Last name" name="categoria">
                                </div>
                                <div class="col col-1">
                                    <button type="submit"  class="btn btn-success ">Buscar</button>
                                </div>
                                <div class="col col-1">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
    <c:if test="${param['solicitud-save']=='error'}">
        <div class="alert alert-danger" role="alert">
            Ya has pedido este libro
        </div>
    </c:if>





    <div class="row justify-content-center" style="margin-top: 50px";>
        <div class="col-10 ">
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach items="${list}" var="libro">
                <div class="col">
                    <div class="card" style="height:100%">
                        <img src="ServletImages?id_producto=${libro.id}" class=" img-thumbnail" style="height:350px;width: auto" alt="...">
                        <div class="card-body h-fluid">
                            <h2 class="card-title"><c:out value="${libro.name}"/>  </h2>
                            <h5 class="card-text"><c:out value="${libro.stock} en stock"/>  </h5>
                            <p class="card-text"><c:out value="Autor: ${libro.autor} "/></p>
                            <p class="card-text"><c:out value="Categoria: ${libro.categoria}"/></p>
                            <c:if test="${role!=2}">
                                <c:if test="${libro.stock>1}">
                                    <a href="list-ejemplar?id=${libro.id}" class="btn btn-success"> <i class="bi bi-search">
                                    </i>Solicitar libro</a>
                                </c:if>
                                <c:if test="${libro.stock<2}">
                                    <button type="submit" class="btn btn-success" disabled>Solicitar libro</button>
                                </c:if>
                            </c:if>
                            <c:if test="${role==2}">
                                <div class="row">
                                    <div class="col-auto">
                                        <form method="get" name="agregar" action="agregar-ejemplar">
                                            <input type="hidden" value="${libro.id}" name="id"/>
                                            <input type="hidden" value="${libro.name}" name="name">
                                            <button type="submit"  class="btn btn-success btn-sm">Añadir ejemplar</button>
                                        </form>
                                    </div>
                                    <div class="col-auto">
                                        <form method="get" name="editar" action="editar-libro">
                                            <input type="hidden" value="${libro.id}" name="id"/>
                                            <button type="submit"  class="btn btn-success btn-sm">Editar</button>
                                        </form>
                                    </div>
                                    <div class="col-auto">
                                        <form method="post" name="eliminar" action="eliminar-libro">
                                            <input type="hidden" value="${libro.id}" name="id"/>
                                            <button type="submit"  class="btn btn-danger btn-sm"> Inhabilitar </button>
                                        </form>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="card-footer">
                    </div>
                    </div>
                </div>
                </c:forEach>
        </div>
    </div>
</div>
</div>
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>