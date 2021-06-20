<%-- 
    Document   : listar
    Created on : 11 jun. 2021, 20:15:50
    Author     : emeda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="Beans.ProductosBeans" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
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
            <h2 align="center">Lista de Productos</h2>
            <table class="table">
                <tr bgcolor="skyeblue">
                    <th>Id_Producto</th>
                    <th>Descripción</th>
                    <th>Unidad de Medida</th>
                    <th>Stock</th>
                    <th>Precio</th>
                    <th>Accion</th>
                </tr>
                <%
                    ArrayList<ProductosBeans> lista= (ArrayList<ProductosBeans>)request.getAttribute("listita");
                    for (int i = 0; i < lista.size(); i++) {
                            ProductosBeans e=lista.get(i);
                            %> <div class="modalBody">
                            <tr>
                                <td><%=e.getId_prod()%></td>
                                <td><%=e.getDescripcion()%></td>
                                <td><%=e.getUnidad_med()%></td>
                                <td><%=e.getStock()%></td>
                                <td><%=e.getPrecio()%></td>
                                <td><a href="vender.jsp?cod=<%=e.getId_prod()%>&desc=<%=e.getDescripcion()%>&unidad_med=<%=e.getUnidad_med()%>&stock=<%=e.getStock()%>&precio=<%=e.getPrecio()%>" onclick="ajaxCRUD()" id="myHref">Vender</a></td>

                            </tr>   </div>
                            <%
                        }
                    %>
                    

            </table>
        </div>
                        



                        <script type="text/javascript">
                          function ajaxCRUD(){       
                                
                                $("#myHref").on('click', function() {
                                //var selectCod=$('#linkclick').val();
                                //var desc=desc_;                          
                                $.ajax({
                                    type: 'GET',
                                    data:{
                                        //selectCod:selectCod,
                                        id : 'id',
                                        desc: 'papa'
                                        
                                    },
                                    url: 'vender.jsp',
                                    success : function(result){
                                        $('#tabla').html(result);
                                    }
                                       });
                            });
                
                          }
                        </script>
                        
                        <div id="tabla" class="container">
      
            </div>  
                        
                        

    </body>
</html>
