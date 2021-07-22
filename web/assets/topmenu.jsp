<%-- 
    Document   : topmenu
    Created on : 24/06/2021, 01:54:20 AM
    Author     : Kokox
--%>
<%@page import="Beans.ProductosBeans"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Beans.UsuariosBeans"%>
<%@page import="java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
        <% 
            UsuariosBeans usr = (UsuariosBeans)session.getAttribute("sesUsername");
            %> 
            
        <nav class="navbar navbar-expand-lg navbar-light">
          <div class="container-fluid">
              <a href="#" class="navbar-brand"><img class="logo-color" src="img/logo-color.png" alt="alt"/></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-contenedor">
              <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item  margin-r20 c-gris">
                  <a class="nav-link active" aria-current="page" href=".">Inicio</a>
                </li>
                <li class="nav-item dropdown  margin-r20 c-gris">
                  <a class="nav-link dropdown-toggle c-gris" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Opciones
                  </a>
                  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                      <%
                        if(usr.getTipo() == 2){
                      %>
                            <li><a class="dropdown-item" href="CRUDServlet?op=addProd">Agregar Producto</a></li>      
                            <li><hr class="dropdown-divider"></li>
                      <%  
                        }  
                          %>
                    <li><a class="dropdown-item" href="CRUDServlet?op=trace">Trazabilidad</a></li>
                  </ul>
                </li>
              </ul>
              <div class="navbar-nav ml-auto">
                <!-- <a href="#" class="nav-item nav-link notifications"><i class="fa fa-bell-o"></i><span class="badge">1</span></a> -->
                <li class="nav-item dropdown">
                    <a href="#" data-bs-toggle="dropdown" class="nav-link dropdown-toggle user-action"><b class="caret c-gris"> <%=usr.getUsername()%> </b></a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a href="#" class="dropdown-item c-gris"><i class="fa fa-user-o c-gris"></i> Perfil</a></li>
                        <li><div class="dropdown-divider"></div></li>
                        <li><a href="CRUDServlet?op=logout" class="dropdown-item c-gris"><i class="material-icons c-gris">&#xE8AC;</i> Salir</a></li>
                    </ul>
                </li>
              </div>                
            </div>
            </div>
          </div>
        </nav>
