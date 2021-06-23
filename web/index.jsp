<%@page import="Beans.ProductosBeans"%>
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
        
        <jsp:include page="/CRUDServlet?op=listar" />
    
    <body>
        <% 
            UsuariosBeans usr = (UsuariosBeans)session.getAttribute("sesUsername");
            %> 
            
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
          <div class="container-fluid">
            <a href="#" class="navbar-brand"><i class="fa fa-cube"></i>Stock<b>eate</b></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                  <a class="nav-link active" aria-current="page" href=".">Inicio</a>
                </li>
                <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Mantenimiento
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a class="dropdown-item" href="#">Registrar Entrada</a></li>
                    <li><a class="dropdown-item" href="#">Registrar Salida</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#">Trazabilidad</a></li>
                  </ul>
                </li>
              </ul>
              <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Buscar por código" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Buscar</button>
              </form>
              <div class="navbar-nav ml-auto">
                <a href="#" class="nav-item nav-link notifications"><i class="fa fa-bell-o"></i><span class="badge">1</span></a>
                <li class="nav-item dropdown">
                    <a href="#" data-bs-toggle="dropdown" class="nav-link dropdown-toggle user-action"><b class="caret"> <%=usr.getUsername()%> </b></a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a href="#" class="dropdown-item"><i class="fa fa-user-o"></i> Perfil</a></li>
                        <li><a href="#" class="dropdown-item"><i class="fa fa-sliders"></i> Opciones</a></a></li>
                        <li><div class="dropdown-divider"></div></li>
                        <li><a href="CRUDServlet?op=logout" class="dropdown-item"><i class="material-icons">&#xE8AC;</i> Salir</a></li>
                    </ul>
                </li>
              </div>                
            </div>
          </div>
        </nav>
        
        <div class="container">
            <div class="row titulo-index">
                <div class="col-12">
                    <H3>¡Hola <%=usr.getNombre()%>, bienvenido a Stockeate!</H3>                     
                </div>               
            </div>

            <div class="row">
                <div class="col-12 cajaop">
                    <h4>Reseña</h4>
                    <p>Esta es una prueba de la lista productos.</p>
                </div>
            </div>
            <div class="row">
                <div class="col-12 cajaop">
                    <h4>Lista de productos</h4>
            <table class="table">
                <tr bgcolor="skyblue">
                    <th>Código Prod.</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Unidad</th>                    
                </tr>
                
                <%
                    ArrayList<ProductosBeans> lista = (ArrayList < ProductosBeans >)request.getAttribute("listaprod");
                    for (int i = 0; i < lista.size(); i++){
                        ProductosBeans p = lista.get(i);
                        %>
                        <tr>
                            <td><%=p.getId_prod()%></td>
                            <td><%=p.getNombre()%></td>
                            <td><%=p.getDescripcion()%></td>
                            <td>S/<%=p.getPrecio()%></td>
                            <td><%=p.getStock()%></td>
                            <td><%=p.getUnid_med()%></td>                            
                        </tr>
                        <%
                    }
                    %>
                
            </table>                    
                </div>                
            </div>
        </div>
    </body>
</html>
