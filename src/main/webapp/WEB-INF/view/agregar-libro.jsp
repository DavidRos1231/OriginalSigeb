
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

<body background="https://www.guillermocinta.com/wp-content/uploads/2021/04/La%20Cr%C3%B3nica%20de%20Morelos-prepara-la-utez-clases-presenciales-con-aforo-de-50-de-alumnos-en-una-primera-etapa.jpg" style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover;">
<jsp:include page="plantilla-admin.jsp" />
<div class="container mt-3">
    <div style="width: 25%; max-width: 300px; height: 5%; max-height: 55px; margin-top: 110px; margin-left: 490px; border-radius: 15px; border: 1px solid black;
        background-color: white; opacity: 90%;">
        <h1 class="text-center">Agregar Libro</h1>
    </div>

    <div class="row justify-content-center" style="width: 75%; max-width: 1000px; height: 505px; margin-top: 50px; margin-left: 150px; border-radius: 15px;
        border: 1px solid black; background-color: white; padding-top: 10px; opacity: 90%;">
        <div class="col-4">

            <form action="guardar-libro" method="post" enctype="multipart/form-data">

                <div class="mb-3">
                    <label for="controlName" class="form-label">Nombre: </label>
                    <input type="text" class="form-control" id="controlName" name="name">
                </div>
                <div class="mb-3">
                    <label for="controlAutor" class="form-label">Autor: </label>
                    <input type="text" class="form-control" id="controlAutor" name="autor">
                 </div>
                <div class="mb-3">
                    <label for="controlCategoria" class="form-label">Categoria: </label>
                    <input type="text" class="form-control" id="controlCategoria" name="categoria">
                 </div>
                <div class="mb-3">
                    <label for="controlFile" class="form-label">Foto del libro: </label>
                    <input type="file" class="form-control" id="controlFile" name="photo">
                </div>
                <br>
                <button type="submit" class="btn btn-success">Registrar</button>
                <button type="reset" class="btn btn-danger">Cancelar</button>
            </form>


        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>