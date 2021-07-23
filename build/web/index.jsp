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
                        <div class="col-md-8">
                            <div class="search"> <i class="fa fa-search"></i> <input type="text" id="buscador-main" name="nombreSearch" class="form-control" placeholder="Ingresa el nombre del producto"> <button class="btn btn-primary">Buscar</button> </div>
                        </div>
                        <div class="col-md-3 fixButton">
                            <button type="button" id="reporteProd" class="btn btn-primary btn-lg tipoBusqueda">Reporte PDF</button>
                        </div>                        
                    </div>                  
                </div>
            </div>
                    
            <div class="row borde-i">                
                <div class="col-12">
                    <h4>Lista de productos</h4>
                    <p><b>[ATENCIÓN]</b> - Los productos con fondo <span class="alertaToolTip">RESALTADO</span> tiene menos stock que el mínimo establecido.</p>
                    <div class="render-listaProd tblListaProd" id="render-listaProd">
                        
                    </div>
                </div> 
                <script id="plantilla-listaProd" type="text/x-handlebars-template">
                    <table class="table">
                        <thead>
                            <tr class="table-titulos" bgcolor="#8691FA">
                                <th>Código Prod.</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Categoría</th>
                                <th>Precio</th>
                                <th>Stock</th>
                                <th>Unidad</th>
                                <th>Stock Mínimo</th>
                                <th>Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                        {{#each this}}
                        {{#if_eq alerta 'normalStock'}}
                        <tr>
                        {{else}}
                        <tr class="alertaStock">
                        {{/if_eq}}
                            <td>{{id_prod}}</td>
                            <td>{{nombre}}</td>
                            <td>{{descripcion}}</td>
                            <td>{{nombre_categoria}}</td>
                            <td>S/ {{precio}}</td>
                            <td>{{stock}}</td>
                            <td>{{nombre_unidad}}</td>
                            <td>{{stockMin}}</td>
                            <td><%
                                    if(usr.getTipo() == 2){
                                        %>
                                        <a href="#" data-bs-href="CRUDServlet?op=eliminar&codigo={{id_prod}}" data-bs-toggle="modal" data-bs-target="#confirm-delete"><i class="fa fa-trash"></i></a>&nbsp&nbsp<a href="CRUDServlet?op=editar&codigo={{id_prod}}"><i class="fa fa-pencil"></i></a>&nbsp&nbsp
                                <%    
                                    }
                                %><a href="#" class="ioMovimiento" data-tipo-value="salida" data-id-value="{{id_prod}}" data-bs-toggle="modal" data-bs-target="#agregarStock"><i class="fa fa-arrow-down"></i></a>&nbsp&nbsp<a href="#" class="ioMovimiento" data-tipo-value="entrada" data-id-value="{{id_prod}}" data-bs-toggle="modal" data-bs-target="#agregarStock"><i class="fa fa-arrow-up"></i></a></td>                          
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
                    
        <!-- Modal para borrar producto -->                    
        <div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5>Eliminación de producto...</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>                      
                    </div>
                    <div class="modal-body">
                        <p>Por favor, confirma la eliminación del producto.</p>
                        <p>Esto removerá al producto y sus movimientos de la base de datos.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                        <a class="btn btn-danger btn-ok">Borrar</a>
                    </div>
                </div>
            </div>
        </div> 
        <!-- Modal para agregar stock -->              
        <div class="modal fade" id="agregarStock" tabindex="-1" role="dialog" aria-labelledby="agregarStock" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form id="form-addoutStock">
                        <div class="modal-header">
                            <h5 id="modalIOStockTitulo">Titulo...</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>                      
                        </div>
                        <div class="modal-body">
                            <p id="modalIOStockDescripcion">Ingrese la entrada de stock:</p>
                              <div class="mb-3">
                                <label for="nombreProducto" class="col-form-label">Producto:</label>
                                <input type="text" name="nombreProducto" class="form-control" id="nombreProducto" disabled="">
                                <input type="hidden" name="inputTipoStock"/>
                                <input type="hidden" name="inputIdProd"/>
                              </div>
                              <div class="mb-3">
                                <label for="inputStock" class="col-form-label">Entrada o salida:</label>
                                <input type="number" name="inputStock" class="form-control sumarestaInput" id="inputStock" required>
                              </div>
                              <div class="mb-3">
                                <label for="outputStock" class="col-form-label">Stock resultante:</label>
                                <input type="hidden" name="hiddenOutputStock" class="actualStockInput"/>
                                <input type="number" name="outputStock" class="form-control" id="outputStock" disabled="">
                              </div>
                              <div class="mb-3">
                                <label for="dateStock" class="col-form-label">Fecha y hora de movimiento:</label>
                                <input type="text" name="dateStock" class="form-control iDateStock" id="dateStock" disabled="">
                              </div>
                            <div class="render-alerta mb-3" id="render-alerta"></div>  
                            <script id="plantilla-alerta" type="text/x-handlebars-template">
                                    {{#if_eq estado 'exitoso'}}
                                        <div class="alert alert-success">
                                            <h5>Exitoso</h5>
                                            <p>{{mensaje}}</p>  
                                        </div>
                                    {{else}}
                                        <div class="alert alert-danger">
                                            <h5>Fallido</h5>
                                            <p>{{mensaje}}</p>  
                                        </div>                            
                                    {{/if_eq}}                 
                            </script> 
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <button type="submit" class="btn btn-success" >Confirmar</button>               
                        </div>                            
                    </form>
                </div>
            </div>
        </div>                     
        <script src="js/script.js"></script>
    </body>
</html>
