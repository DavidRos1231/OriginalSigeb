<%@ page import="java.util.List" %>
<%@ page import="mx.edu.utez.biblioteca.biblioteca.model.BeanCategoria " %>
<%@ page import="java.util.ArrayList"%>
<%--
  Created by IntelliJ IDEA.
  User: jdrj4
  Date: 8/4/2022
  Time: 6:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrar persona</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body background="https://www.guillermocinta.com/wp-content/uploads/2021/04/La%20Cr%C3%B3nica%20de%20Morelos-prepara-la-utez-clases-presenciales-con-aforo-de-50-de-alumnos-en-una-primera-etapa.jpg" style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative;">
<jsp:include page="plantilla-admin.jsp" />
<div class="container mt-3">
    <div style="width: 30%; max-width: 410px; height: 20%; margin-top: 110px; margin-left: 440px; border-radius: 15px; border: 1px solid black;
    background-color: white; opacity: 90%;">
        <h1 class="text-center">Actualizar datos</h1>
    </div>

    <div class="row justify-content-center" style="width: 75%; max-width: 1000px; height: 350px; margin-top: 50px; margin-left: 150px; border-radius: 15px;
    border: 1px solid black; background-color: white; padding-top: 15px; opacity: 90%;">

        <div class="col-4">
            <form action="actualizar-usuario" method="post">
                <input type="hidden" class="form-control" id="controlId" name="matricula" value="${usuario.id}">
                <input type="hidden" class="form-control" id="controlTipoc" name="tipo" value="${usuario.tipo}">
                <div class="mb-3">
                    <label for="controlName" class="form-label">Nombre: </label>
                    <input type="text" class="form-control" id="controlName" name="name" value="${usuario.name}">
                </div>
                <div class="mb-3">
                    <label for="controlMidname" class="form-label">Apellido paterno: </label>
                    <input type="text" class="form-control" id="controlMidname" name="midname" value="${usuario.midname}">
                </div>
                <div class="mb-3">
                    <label for="controlCorreo" class="form-label">Correo: </label>
                    <input type="email" class="form-control" id="controlCorreo" name="correo" value="${usuario.correo}">
                </div>
        </div><div class="col-4">
        <div class="mb-3">
            <label for="controlLastname" class="form-label">Apellido Materno: </label>
            <input type="text" class="form-control" id="controlLastname" name="lastname" value="${usuario.lastname}">
        </div>


        <div class="mb-3">
            <label for="controlPassword" class="form-label">Contraseña: </label>
            <input type="text" class="form-control" id="controlPassword" name="password" value="${usuario.password}">
        </div>

    </div>
        <div class="row justify-content-center">
            <div class="col-auto">
                <button type="submit" class="btn btn-success">Actualizar</button>
                <button type="reset" class="btn btn-danger">Restablecer cambios</button>
            </div>
        </div>

        </form>


    </div>

</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>
