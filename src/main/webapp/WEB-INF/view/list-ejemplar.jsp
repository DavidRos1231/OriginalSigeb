<%@ page import="java.util.List" %>
<%@ page import="mx.edu.utez.biblioteca.biblioteca.model.BeanEjemplar " %>
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
    <script language="JavaScript">
        function confirmar_envio(){
            if (confirm("Estas seguro que quieres inhabilitar este libro?"))
            {
                document.eliminar.submit()
            }
        }
    </script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://bootswatch.com/5/darkly/bootstrap.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="https://bootswatch.com/5/darkly/bootstrap.min.css" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_variables.scss" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_bootswatch.scss" rel="stylesheet">
</head>
<body style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative; background-color: #222222"><c:if test="${role==2}">
    <jsp:include page="plantilla-admin.jsp" />
</c:if>
<c:if test="${role!=2}">
    <jsp:include page="plantilla-user.jsp" />
</c:if>

<!--Botones de sepalv-->
<div class="container">
    <c:if  test="${role==2}">
        <div>
            <div class="col-10 ">
                <div class="row justify-content-end" style="margin-top: 150px;">
                    <div class="col-2 mb-2 mt-2">
                        <a href="agregar-ejemplar" class="btn btn-success">Agregar Libro</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${role!=2}">
        <div>
            <div class="col-10 ">
                <div class="row justify-content-end" style="margin-top: 150px;">
                    <div class="col-2 mb-2 mt-2">
                        <a href="perfil" class="btn btn-success">ver prestamos</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="row justify-content-center">
        <div class="col-10 ">
            <table class="table table-dark table-bordered table-striped">
                <thead>
                <tr>
                    <th>id</th>
                    <th>Nombre</th>
                    <th>Estado</th>
                    <th>Editorial</th>
                    <th>Descripcion</th>
                    <th>Acciones</th>
                    <c:if test="${role==2}"><th>    </th></c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="ejemplar">
                    <tr>
                        <td><c:out value="${ejemplar.id}"/> </td>
                        <td><c:out value="${ejemplar.name}"/></td>
                        <td><c:out value="${ejemplar.estado? 'Disponible':'Prestado'}"/></td>
                        <td><c:out value="${ejemplar.editorial}"/></td>
                        <td><c:out value="${ejemplar.descripcion}"/></td>
                        <td>
                            <c:if test="${ejemplar.estado && noprestamos<control}">
                                <form action="solicitar-ejemplar" method="post">
                                    <input type="hidden" value="${ejemplar.id}" name="id">
                                    <input type="hidden" value="${name}" name="name">
                                    <button type="submit" class="btn btn-success">Solicitar ejemplar</button>
                                </form>
                            </c:if>
                            <c:if test="${!ejemplar.estado || noprestamos>control-1}">
                                <form action="solicitar-ejemplar" method="post">
                                    <button type="submit" disabled class="btn btn-success">Solicitar ejemplar</button>
                                </form>
                            </c:if>
                        </td>
                        <c:if test="${role==2}">
                            <td>
                                <form method="post" name="eliminar" action="eliminar-ejemplar">
                                    <input type="hidden" value="${ejemplar.id}" name="id"/>
                                    <button type="button" onclick="confirmar_envio()" class="btn btn-danger"> Inhabilitar </button>
                                </form>

                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>