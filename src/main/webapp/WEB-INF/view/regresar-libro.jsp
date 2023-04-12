<%@ page import="java.util.List" %>
<%@ page import="mx.edu.utez.biblioteca.biblioteca.model.BeanPrestamoLibro" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: CA2-PC-
  Date: 28/06/2022
  Time: 12:58 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<body style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative; background-color: #222222"><jsp:include page="plantilla-admin.jsp"/>
<div class="container">
  <div>
    <div class="col-12 justify-self-center">
      <div class="row justify-content-center" style="margin-top:150px;">
        <div class="col-3 mb-3 mt-3">
          <form action="regresar-libro" method="get">
            <div class="mb-3">
              <label for="controlid" class="form-label">Matricula o CURP: </label>
              <input type="text" class="form-control" id="controlid" name="id">
            </div>
            <div class="row justify-content-center">
              <div class="col-auto">
                <button type="submit" class="btn btn-success">Buscar</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>


  <div class="row justify-content-center">
    <div class="col-10 ">
      <table class="table table-dark table-bordered table-striped">
        <thead>
        <tr>
          <th>Nombre</th>
          <th>ID de usuario</th>
          <th>Fecha de pedido</th>
          <th>Fecha de entrega</th>
          <th>Adeudo</th>
          <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="prestamo">
        <c:if test="${prestamo.status==1}">
          <tr>
            <td><c:out value="${prestamo.nomejemplar}"/></td>
            <td><c:out value="${prestamo.userid}"/></td>
            <td><c:out value="${prestamo.fechainicial}"/></td>
            <td><c:out value="${prestamo.fechafinal}"/></td>
            <td><c:out value="$${prestamo.dias>5?((prestamo.dias-5)*5):0}"/></td>>
            <td>
              <form method="get" name="regresar" action="entregar-ejemplar">
                <input type="hidden" value="${prestamo.idejemplar}" name="idejemplar">
                <input type="hidden" value="${prestamo.id}" name="idprestamo">
                <input type="hidden" value="${prestamo.descripcion}" name="descripcion">
                <button type="submit" class="btn btn-danger"> Regresar </button>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>