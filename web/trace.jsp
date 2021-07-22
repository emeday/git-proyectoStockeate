<%@page import="Beans.ProductosBeans"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Beans.UsuariosBeans"%>
<%@page import="java.io.PrintWriter"%>
<%-- 
    Document   : trace
    Created on : 21/07/2021, 11:24:12 PM
    Author     : Kokox
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        if (session.getAttribute("sesUsername") == null){
            //si no existe una sesión, llamamos a "login" en el servlet
            request.getRequestDispatcher("CRUDServlet?op=login").forward(request, response);
        }
        %>
        
    <%@include  file="assets/head.html" %>
        
        <!-- llama a la opción listar en el CRUD justo al cargar index.jsp -->

    
    <body>
        <!-- llama al archivo topmenu.jsp que contiene la llamada a la sesión y el menú superior -->
        <%@include  file="assets/topmenu.jsp" %>
        
        <div class="container">
            <div class="row titulo-index">
                <div class="col-12">
                    <H3>Hola <%=usr.getNombre()%></H3> 
                    <h2 class="c-violet">Bienvenido a Stockeate</h2>
                </div>               
            </div>

            <div class="row borde-s borde-i">
                <div class="col-12 ">
                    <div class="row height d-flex justify-content-center align-items-center">
                        <div class="col-md-3">
                            <select id="selectTipoBusqueda" name="selectTipoBusqueda" class="form-select tipoBusqueda">
                                <option value="idMov">ID de Movimiento</option>
                                <option value="tipoMov">Tipo de Movimiento</option>
                                <option value="nomMov">Nombre de Producto</option>
                                <option value="fechaMov">Fecha de Movimiento</option>
                                <option value="userMov">Nombre de Usuario</option>
                            </select>                              
                        </div>
                        <div class="col-md-8">                          
                            <div class="search"> 
                                <i class="fa fa-search"></i> 
                                <input type="text" id="buscador-mov" name="movimientoSearch" class="form-control" placeholder="Ingresa el término de búsqueda"> 
                                <button class="btn btn-primary">Buscar</button> 
                            </div>
                        </div>
                    </div>                  
                </div>
            </div>
                    
            <div class="row borde-i">                
                <div class="col-12">
                    <h4>Trazabilidad de Movimientos</h4>
                    <p><b>[ATENCIÓN]</b> - La búsqueda por fecha se debe hacer en el siguiente formato: <b>AÑO-MES-DÍA HORA:MINUTO:SEGUNDO</b></p>
                    <div class="render-listaMov tblListaMov" id="render-listaMov">
                        
                    </div>
                </div> 
                <script id="plantilla-listaMov" type="text/x-handlebars-template">
                    <table class="table">
                        <thead>
                            <tr class="table-titulos" bgcolor="#8691FA">
                                <th>ID Mov.</th>
                                <th>Tipo de Mov.</th>
                                <th>Nombre de Prod.</th>
                                <th>Cantidad</th>
                                <th>Fecha de Mov.</th>
                                <th>Usuario</th>
                            </tr>
                        </thead>
                        <tbody>
                        {{#each this}}
                        <tr>
                            <td>{{0}}</td>
                            <td>{{1}}</td>
                            <td>{{2}}</td>
                            <td>{{3}}</td>
                            <td>{{4}}</td>
                            <td>{{5}}</td>                       
                        </tr>
                        {{/each}}
                        </tbody>
                    </table>                                         
                </script>                 
                <div class="cargando">
                    <img src="./img/cargando.gif" alt="cargando"/>
                </div>                 
            </div>
        </div>                     
        <script src="js/script.js"></script>
    </body>
</html>
