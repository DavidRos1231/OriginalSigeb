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
    <link href="https://bootswatch.com/5/darkly/bootstrap.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <link href="https://bootswatch.com/5/darkly/bootstrap.min.css" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_variables.scss" rel="stylesheet">
    <link href="https://bootswatch.com/5/darkly/_bootswatch.scss" rel="stylesheet"></head>
<body style="background-repeat: no-repeat; background-attachment: fixed; background-size: cover; position: relative; background-color: #222222"><c:if test="${role==2}">
    <jsp:include page="plantilla-admin.jsp" />
</c:if>
<c:if test="${role!=2}">
    <jsp:include page="plantilla-user.jsp" />
</c:if>

<div class="container" >
    <c:if test="${param['solicitud-save']=='error'}">
        <div class="alert alert-danger" role="alert">
            Ya has pedido este libro
        </div>
    </c:if>
    <div class="row justify-content-center" style="margin-top: 150px";>
        <div class="col-10 ">
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach items="${list}" var="libro">
                <div class="col">
                    <div class="card" style="height:100%">
                        <img src="ServletImages?id_producto=${libro.id}"  style="height:350px;width: auto" alt="...">
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