<%-- 
    Document   : editProd
    Created on : 16/07/2021, 06:48:26 PM
    Author     : Kokox
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include  file="assets/head.html" %>
    
    <%
        if (session.getAttribute("sesUsername") == null){
            //si no existe una sesión, llamamos a "login" en el servlet
            request.getRequestDispatcher("CRUDServlet?op=login").forward(request, response);
        }

        //obtenemos la información del producto
        ArrayList<ProductosBeans> listaInfoProd = (ArrayList < ProductosBeans >)request.getAttribute("listaInfoProd");
        ProductosBeans oListaInfoProd = listaInfoProd.get(0);

        %>
        
    <body>
        <!-- llama al archivo topmenu.jsp que contiene la llamada a la sesión y el menú superior -->
        <%@include  file="assets/topmenu.jsp" %>

        <div class="container">
            <div class="row titulo-index">
                <div class="col-12">
                    <H3>¡Hola <%=usr.getNombre()%>!. Aquí podrás editar tus productos.</H3>                     
                </div>               
            </div>
        </div>
                
        <div class="container-lg">
            <div class="row">
                <div class="col-md-10 mx-auto cajaop">
                    <div class="addproduct-form">
                        <h1>Editar Producto</h1>
                        <p class="hint-text">Puedes editar cierta información de los productos creados.<br/>Si deseas agregar stock, ve a la sección de "Movimientos" y registra una entrada.</p>       
                        <form id="form-editProd">
                            <div class="row">
                                <div class="form-group">
                                    <label for="inputNombre">Nombre del Producto</label>
                                    <input type="text" class="form-control" id="inputNombre" name="inputNombre" value="<%=oListaInfoProd.getNombre()%>" placeholder="Un nombre corto..." required>
                                    <input type="hidden" name="inputIdProd" value="<%=oListaInfoProd.getId_prod()%>"/>
                                </div>                                
                                <div class="col-sm-3">
                                    <div class="form-group ">
                                        <label for="inputCategoria">Categoría</label>
                                        <select name="selectCat" class="form-select" id="inputCategoria">
                                            <%
                                                ArrayList<ProductosBeans> listaPrdCat = (ArrayList < ProductosBeans >)request.getAttribute("listaPrdCat");
                                                for (int i = 0; i < listaPrdCat.size(); i++){
                                                    ProductosBeans prd = listaPrdCat.get(i);
                                                    
                                                    if(prd.getIdcategoria() == oListaInfoProd.getIdcategoria()){
                                                %> 

                                                <option value="<%=prd.getIdcategoria()%>" selected="selected"><%=prd.getNombre_categoria()%></option>

                                            <%
                                                    } else {
                                            %>
                                                <option value="<%=prd.getIdcategoria()%>"><%=prd.getNombre_categoria()%></option>
                                            <%
                                                    }
                                                }
                                                %>                                            
                                        </select>
                                    </div>
                                </div>               
                                <div class="col-sm-3">
                                    <label for="inputPrecio">Precio</label>                                    
                                    <div class="input-group">
                                        <div class="input-group-append">
                                            <span class="input-group-text">S/</span>
                                        </div>                                         
                                        <input type="number" class="form-control" id="inputPrecio" name="inputPrecio" value="<%=oListaInfoProd.getPrecio()%>" placeholder="Precio de venta..." required>                       
                                    </div>                                    
                                </div>   
                                <div class="col-sm-3">
                                    <div class="form-group ">
                                        <label for="inputUnidad">Unidad</label>
                                        <select name="selectUnid" class="form-select" id="inputUnidad">
                                            <%
                                                ArrayList<ProductosBeans> listaPrdUnid = (ArrayList < ProductosBeans >)request.getAttribute("listaPrdUnid");
                                                for (int i = 0; i < listaPrdUnid.size(); i++){
                                                    ProductosBeans prd = listaPrdUnid.get(i);
                                                    
                                                    if(prd.getIdunidad() == oListaInfoProd.getIdunidad()){
                                                %> 

                                                <option value="<%=prd.getIdunidad()%>" selected="selected"><%=prd.getNombre_unidad()%></option>

                                            <%
                                                    } else {
                                            %>
                                                <option value="<%=prd.getIdunidad()%>"><%=prd.getNombre_unidad()%></option>
                                            <%
                                                    }
                                                }
                                                %>                                            
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <label for="inputMinRate">Stock Mínimo</label>                                    
                                    <div class="input-group">
                                        <input type="number" class="form-control" id="inputMinRate" name="inputMinRate" value="<%=oListaInfoProd.getStockMin()%>" placeholder="Mínimo de stock..." required>
                                        <div class="input-group-append">
                                            <span class="input-group-text">unid.</span>
                                        </div>                        
                                    </div>                                    
                                </div>                                        
                            </div>            
                            <div class="form-group">
                                <label for="inputMessage">Descripción del Producto</label>
                                <textarea class="form-control" id="inputMessage" name="inputDescripcion" rows="5" required><%=oListaInfoProd.getDescripcion()%></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-paper-plane"></i> Editar Producto</button>
                            <div class="cargando">
                                <img src="./img/cargando.gif" alt="cargando"/>
                            </div>                              
                        </form>                      
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="render-alerta col-md-10 mx-auto" id="render-alerta">
                   <!-- aquí se renderizará el mensaje de alerta -->
                </div>  
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
        </div>        
        
        <script src="js/script.js"></script>
    </body>
</html>