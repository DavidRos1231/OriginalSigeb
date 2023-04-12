<%@ page import="java.util.List" %>
<%@ page import="mx.edu.utez.biblioteca.biblioteca.model.BeanSala " %>
<%@ page import="java.util.ArrayList"%><%--
  Created by IntelliJ IDEA.
  User: jdrj4
  Date: 8/3/2022
  Time: 10:47 AM
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

<div class="container">
    <div class="row justify-content-center">
        <div class="col-10" style="margin-top: 150px;">
            <div class="row justify-content-end">
                <div class="col-2 mb-2 mt-2">
                    <c:if test="${role==2}">
                    <a href="agregar-sala" class="btn btn-success">Agregar Sala</a>
                    </c:if>

                </div>
            </div>
        </div>
    </div>
    <c:if test="${!salaprestada && param['result-save']=='error'}">
        <div class="alert alert-danger" role="alert">
            Ya has solicitado una sala
        </div>
    </c:if>
    <c:if test="${!salaprestada && param['result-save']=='ok'}">
        <div class="alert alert-success" role="alert">
            Ya puedes ocupar tu sala
        </div>
    </c:if>
    <c:if test="${!salaprestada && param['result-save']==null}">
        <div class="alert alert-warning" role="alert">
            Ya pediste una sala
        </div>
    </c:if>
    <c:if test="${role==2 && param['result-save']=='error'}">
        <div class="alert alert-warning" role="alert">
            el usuario ya pidio una sala
        </div>
    </c:if>
    <div class="row justify-content-center">
        <div class="col-10 ">
                <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach items="${list}" var="sala">
                        <div class="col">
                            <div class="card">
                                <div class="card-body">
                                    <h2 class="card-title"><c:out value="sala ${sala.id}"/>  </h2>
                                    <h5 class="card-title"><c:out value="${sala.disponibilidad?'Disponible':'Ocupada'}"/>  </h5>
                                    <p class="card-text"><c:out value="Lugares: ${sala.lugares} "/></p>
                                    <p class="card-text"><c:out value="${sala.descripcion}"/></p>
                                    <c:if test="${role!=3}">
                                    <c:if test="${salaprestada && sala.disponibilidad}">
                                    <form action="pedir-sala" method="post">
                                        <c:if test="${role==2}">
                                            <label for="controlid">Matricula o CURP</label>
                                            <input type="text" id="controlid" class="form-control" name="idu" >
                                        </c:if>
                                        <c:if test="${role!=2}">
                                            <input type="hidden" id="controlid" name="idu" value="${name}">
                                        </c:if>
                                        <input type="hidden" value="${sala.id}" name="id">
                                        <select name="tiempo" id="floatinselect" class="form-select">
                                            <option selected>00:00 Horas</option>
                                            <option value="1">00:30 Horas</option>
                                            <option value="2">01:00 Horas</option>
                                            <option value="3">01:30 Horas</option>
                                            <option value="4">02:00 Horas</option>
                                            <option value="5">02:30 Horas</option>
                                            <option value="6">03:00 Horas</option>
                                            <option value="7">03:30 Horas</option>
                                            <option value="8">04:00 Horas</option>
                                            <option value="9">04:30 Horas</option>
                                            <option value="10">05:00 Horas</option>
                                        </select>
                                        <button type="submit" class="btn btn-success" style="margin-top: 5px ">Solicitar</button>
                                    </form>
                                    </c:if>
                                    <c:if test="${!salaprestada || !sala.disponibilidad}">
                                        <form action="pedir-sala" method="post">
                                            <select name="tiempo" disabled class="form-select">
                                                <option selected>00:00 Horas</option>
                                            </select>
                                            <button type="button" class="btn btn-success" disabled style="margin-top: 5px ">Solicitar</button>
                                        </form>
                                    </c:if>
                                    </c:if>
                                    <c:if test="${role==2}">
                                        <c:if test="${!sala.disponibilidad}">


                                        </c:if>
                                        <c:if test="${sala.disponibilidad}">

                                        </c:if>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                </c:forEach>
            </div>
        </div>
    </div>


</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
</body>
</html>