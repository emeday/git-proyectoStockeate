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
        
        <!-- llama a la opción listar en el CRUD justo al cargar index.jsp -->
        <jsp:include page="/CRUDServlet?op=listar" />
    
    <body>
        <!-- llama al archivo topmenu.jsp que contiene la llamada a la sesión y el menú superior -->
        <%@include  file="assets/topmenu.jsp" %>
        
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
                            <th>Opciones</th>
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
                                    <td><a href="#"><i class="fa fa-trash"></i></a>&nbsp&nbsp<a href="#"><i class="fa fa-pencil"></i></a>&nbsp&nbsp<a href="#"><i class="fa fa-arrow-down"></i></a>&nbsp&nbsp<a href="#"><i class="fa fa-arrow-up"></i></a></td>
                                </tr>
                                <%
                            }
                            %>

                    </table>                    
                </div>                
            </div>
        </div>
        <script src="js/script.js"></script>
    </body>
</html>
