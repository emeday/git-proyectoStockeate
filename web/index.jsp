<%@page import="java.util.ArrayList"%>
<%@page import="Beans.UsuariosBeans"%>
<%@page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <%@include  file="assets/head.html" %>
    
    <%
        if (session.getAttribute("sesUsername") == null){
            //si no existe una sesión, llamamos a "login" en el servlet
            request.getRequestDispatcher("CRUDServlet?op=login").forward(request, response);
            
        }
        
        %>
    
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
                    <li><a class="dropdown-item" href="CRUDServlet?op=logout">Salir</a></li>
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
            <% 
                UsuariosBeans usr = (UsuariosBeans)session.getAttribute("sesUsername");
                %>
            
            <H4>¡Hola <%=usr.getNombre()%>, bienvenido a Stockeate!</H4>
        </div>
    </body>
</html>
