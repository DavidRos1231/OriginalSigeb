<%@ page import="java.util.List" %>
<%@ page import="mx.edu.utez.biblioteca.biblioteca.model.BeanUsuario" %>
<%@ page import="java.util.ArrayList" %>
<%--
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
<jsp:include page="plantilla-admin.jsp" />
    <div class="container" style="margin-top: 6rem;">
        <div class="row justify-content-center">
            <div class="col-10 " style="margin-top: 25px;">
                <div class="row justify-content-center">

                    <div class="container-fluid">
                        <form action="list-usuarios" method="get" class="d-flex" role="search">
                            <input class="form-control me-2" id="Controlmat" name="mat" type="text" placeholder="Matricula o CURP" aria-label="Search">
                            <button class="btn btn-success" type="submit">Buscar</button>
                        </form>
                        <br>
                    </div>
                </div>
                </div>
            </div>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <c:forEach items="${list}" var="usuario">
            <div class="col">
                <div class="card text-center">
                    <div class="card-header">
                        <c:out value="${usuario.tipo==1 ? 'Docente':usuario.tipo==0 ? 'Alumno':'Admin'}"/>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title"><c:out value="${usuario.name} ${usuario.midname} ${usuario.lastname}"/></h5>
                        <p class="card-text"><c:out value="${usuario.id}"/></p>
                        <p class="card-text"><c:out value="${usuario.correo}"/></p>
                    </div>
                    <div class="card-footer text-center">

                        <form method="post" action="eliminar-usuario" >
                            <a href="editar-usuario?id=${usuario.id}" class="btn btn-success">Editar</a>
                            <input type="hidden" value="${usuario.id}" name="id"/>
                            <button type="submit" class="btn btn-outline-danger">Eliminar</button>
                        </form>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>


</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
