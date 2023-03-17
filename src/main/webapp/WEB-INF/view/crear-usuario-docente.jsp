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

<div class="container mt-3">
    <div class="bg-light sticky-top" style="width: 100%; left: 0; top: 0; position: fixed;">
        <div class="container">
            <div class="row text-center">
                <div class="col-4">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/5/54/Logo-utez.png" style="max-height: 90px;">
                </div>
                <div class="col-4 ms-auto">
                    <div class="row text-center">
                        <div class="col" style="font-size: 40px;"><span class="titulo">SIGEB</span>
                        </div>
                    </div>
                    <div class="row text-center" style="font-size: 20px;"><div class="col"><span class="subtitulo">Sistema de Gestion Bibliotecario</span></div></div>
                </div>
                <div class="col-4 ms-auto">
                    <img src="data:image/png;base64,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" style="max-height: 90px;">
                </div>
            </div>

        </div>
    </div>

    <div style="width: 30%; max-width: 410px; height: 20%; margin-top: 110px; margin-left: 440px; border-radius: 15px; border: 1px solid black;
    background-color: white; opacity: 90%;">
        <h1 class="text-center">Registro de docente</h1>
    </div>

    <div class="row justify-content-center" style="width: 75%; max-width: 1000px; height: 350px; margin-top: 50px; margin-left: 150px; border-radius: 15px;
    border: 1px solid black; background-color: white; padding-top: 15px; opacity: 90%;">

        <div class="col-4">
            <form action="guardar-usuario" method="post">

                <div class="mb-3">
                    <label for="controlId" class="form-label">CURP: </label>
                    <input type="text" class="form-control" id="controlId" name="matricula" required>
                </div>


                <div class="mb-3">
                    <label for="controlMidname" class="form-label">Apellido paterno: </label>
                    <input type="text" class="form-control" id="controlMidname" name="midname" required>
                </div>
                <div class="mb-3">
                    <label for="controlCorreo" class="form-label">Correo: </label>
                    <input type="email" class="form-control" id="controlCorreo" name="correo" required placeholder="name@example.com">
                </div>
        </div>
        <div class="col-4">
        <div class="mb-3">
            <label for="controlName" class="form-label">Nombre: </label>
            <input type="text" class="form-control" id="controlName" name="name" required>
        </div>
        <div class="mb-3">
            <label for="controlLastname" class="form-label">Apellido Materno: </label>
            <input type="text" class="form-control" id="controlLastname" name="lastname" required>
        </div>
        <div class="mb-3">
            <label for="controlPassword" class="form-label">Contraseña: </label>
            <input type="password" class="form-control" id="controlPassword" name="password" required>
            <input type="hidden" name="tipo" value="1">
        </div></div>
        <div class="row justify-content-center">
            <div class="col-auto">
                <button type="submit" class="btn btn-success">Registrar</button>
                <a href="menu" class="btn btn-danger">Cancelar</a>
            </div>
        </div>

        </form>





</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>