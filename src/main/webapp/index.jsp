<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>Login</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/sign-in/">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">



    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }

        .b-example-divider {
            height: 3rem;
            background-color: rgba(0, 0, 0, .1);
            border: solid rgba(0, 0, 0, .15);
            border-width: 1px 0;
            box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
        }

        .b-example-vr {
            flex-shrink: 0;
            width: 1.5rem;
            height: 100vh;
        }

        .bi {
            vertical-align: -.125em;
            fill: currentColor;
        }

        .nav-scroller {
            position: relative;
            z-index: 2;
            height: 2.75rem;
            overflow-y: hidden;
        }

        .nav-scroller .nav {
            display: flex;
            flex-wrap: nowrap;
            padding-bottom: 1rem;
            margin-top: -1px;
            overflow-x: auto;
            text-align: center;
            white-space: nowrap;
            -webkit-overflow-scrolling: touch;
        }
    </style>


    <!-- Custom styles for this template -->
    <link href="https://getbootstrap.com/docs/5.2/examples/sign-in/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
<c:if test="${sessionScope.containsKey('name')}">
    <c:redirect url="menu" />
</c:if>

<main class="form-signin w-100 m-auto">
    <form method="post" action="login">
        <img src="ServletImages?id_producto=72" class=" img-thumbnail" style="height:100px;width: 400px" alt="...">
        <h1 class="h3 mb-3 fw-normal">Inicio de sesión</h1>

        <c:if test="${param['message']!=null}">
            <c:if test="${param['message']=='error'}">
                <div class="alert alert-warning" role="alert">
                    Usuario y/o contraseña incorrectas
                </div>
            </c:if>
        </c:if>

        <div class="form-floating">
            <input type="text" name="id" class="form-control" id="floatingInput" placeholder="id"
                   required>
            <label for="floatingInput">Matricula o CURP</label>
        </div>
        <div class="form-floating">
            <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password"
                   required>
            <label for="floatingPassword">Contraseña</label>
        </div>

        <button class="w-100 btn btn-lg btn-success" type="submit">Iniciar Sesión</button>
        <div> <p>¿No estas registrado?</p>
            <p>Registrate aqui</p></div>
        <div class="btn-group">
            <button type="button" class="btn btn-success dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                Registrar usuario
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="crear-usuario-docente">Docente</a></li>
                <li><a class="dropdown-item" href="crear-usuario-alumno">Alumno</a></li>
            </ul>
        </div>
    </form>
</main>


</body>
</html>
