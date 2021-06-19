<%-- 
    Document   : insertar
    Created on : 11 jun. 2021, 21:39:05
    Author     : emeda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Beans.EmpleadoBeans" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>

        <title>JSP Page</title>
    </head>
    <body>
          <nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">CRUD JSP</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="index.html">Home</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Mantenimiento
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="CRUDServlet?op=listar">Listar</a></li>
            <li><a class="dropdown-item" href="insertar.jsp">Insertar</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#">Consultar</a></li>
          </ul>
        </li>
      </ul>
      <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
    </div>
  </div>
</nav>
        <div class="container">
            <h2 align="center">Nuevos Empleados</h2>
            <form action="CRUDServlet">
                
                <input type="text" name="txtCod" placeholder="Ingresar codigo" class="form-control">
                <input type="text" name="txtNom" placeholder="Ingresar nombre" class="form-control">
                <input type="number" min="18" max="50" value="18" name="txtEdad" class="form-control">
                <input type="text" name="txtSexo" placeholder="Ingresar Sexo" class="form-control">
                <input type="text" name="txtPas" placeholder="Ingresar Pas" class="form-control">
                
                <input type="hidden" name="op" value="insertar">
                <input type="submit" class="btn btn-primary" value="Registrar Empleado">

            </form>
        </div>
    </body>
</html>
