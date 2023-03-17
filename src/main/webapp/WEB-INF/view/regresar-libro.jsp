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
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body background="https://www.guillermocinta.com/wp-content/uploads/2021/04/La%20Cr%C3%B3nica%20de%20Morelos-prepara-la-utez-clases-presenciales-con-aforo-de-50-de-alumnos-en-una-primera-etapa.jpg" style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative;">
<jsp:include page="plantilla-admin.jsp"/>
<div class="container">
  <div>
    <div class="col-12 justify-self-center">
      <div class="row justify-content-center" style="margin-top: 100px;">
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
      <table class="table table-light table-bordered table-striped">
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