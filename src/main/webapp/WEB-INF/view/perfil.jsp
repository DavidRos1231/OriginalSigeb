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
    <link href="https://bootswatch.com/5/darkly/bootstrap.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="https://bootswatch.com/5/darkly/bootstrap.min.css" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_variables.scss" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_bootswatch.scss" rel="stylesheet"></head>
<body style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative; background-color: #222222"><jsp:include page="plantilla-user.jsp"/>

<!--Cuerpo-->
<div class="container" style="margin-top: 150px;">
    <div class="row">
        <div class="col-4">
            <div class="list-group" id="list-tab" role="tablist">
                <a class="list-group-item list-group-item-action active" id="list-home-list" data-bs-toggle="list" href="#list-home" role="tab" aria-controls="list-home">Información personal</a>
                <a class="list-group-item list-group-item-action" id="list-profile-list" data-bs-toggle="list" href="#list-profile" role="tab" aria-controls="list-profile">Préstamos vigentes</a>
                <a class="list-group-item list-group-item-action" id="list-messages-list" data-bs-toggle="list" href="#list-messages" role="tab" aria-controls="list-messages">Préstamos pendientes</a>
            </div>
        </div>
        <div class="col-8">
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="list-home" role="tabpanel" aria-labelledby="list-home-list">
                    <table class="table table-dark table-bordered table-striped">
                        <thead>
                        <tr class="text-center">
                            <th><c:out value="${usuario.tipo==0?'Matricula':'CURP'}"/></th>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Tipo de usuario</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${usuario.id}"/></td>
                            <td><c:out value="${usuario.name} ${usuario.midname} ${usuario.lastname}"/></td>
                            <td><c:out value="${usuario.correo}"/></td>
                            <td><c:out value="${usuario.tipo==0?'Estudiante':'Docente'}"/></td>
                            <td>
                                    <a href="editar-usuario?id=${usuario.id}" class="btn btn-success">Editar</a>

                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="list-profile" role="tabpanel" aria-labelledby="list-profile-list">
                    <table class="table table-light table-bordered table-striped">
                        <thead>
                        <tr class="text-center">
                            <th>Nombre del libro </th>
                            <th>Fecha inicial</th>
                            <th>Fecha de entrega</th>
                            <th>Deuda acumulada</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="prestamo">
                            <c:if test="${prestamo.status==1}">
                                <tr>
                                    <td><c:out value="${prestamo.nomejemplar}"/></td>
                                    <td><c:out value="${prestamo.fechainicial}"/></td>
                                    <td><c:out value="${prestamo.fechafinal}"/></td>
                                    <td><c:out value="$${prestamo.dias>5?((prestamo.dias-5)*5):0}"/></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="list-messages" role="tabpanel" aria-labelledby="list-messages-list">
                    <table class="table table-light table-bordered table-striped">
                        <thead>
                        <tr class="text-center">
                            <th>Nombre del libro </th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list}" var="prestamo">
                            <c:if test="${prestamo.status==0}">
                                <tr>
                                    <td><c:out value="${prestamo.nomejemplar}"/></td>
                                    <td><c:out value="${prestamo.status==0?'Pendiente':'Error'}"/></td>
                                    <td>
                                        <form method="post" name="rechazar" action="rechazar-prestamo">
                                            <input type="hidden" value="${prestamo.id}" name="id"/>
                                            <button type="submit" class="btn btn-danger"> Cancelar </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
