
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
    <link href="https://bootswatch.com/5/darkly/bootstrap.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="https://bootswatch.com/5/darkly/bootstrap.min.css" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_variables.scss" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_bootswatch.scss" rel="stylesheet">
</head>

<body style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative; background-color: #222222">
<jsp:include page="plantilla-admin.jsp" />
<div class="container mt-3">
    <div class="col-12">
        <h1 class="text-center">Agregar Ejemplar</h1>
        <h2 class="text-center"><c:out value="${name}"/></h2>
    </div>

    <div class="row justify-content-center" style="width: 75%; max-width: 1000px; height: 505px; margin-top: 150px; margin-left: 150px; border-radius: 15px;
        border: 1px solid black; background-color: #434343; padding-top: 10px; opacity: 90%;">
        <div class="col-4">

            <form action="devolucion" method="post">

                <div class="mb-3">
                    <input type="hidden" value="${idejemplar}" name="idejemplar">
                    <input type="hidden" value="${idprestamo}" name="idprestamo">
                    <label for="controlDescripcion" class="form-label">Estado actual del libro: </label>
                    <textarea class="form-control" id="controlDescripcion" rows="3" name="desc">${descripcion}</textarea>
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