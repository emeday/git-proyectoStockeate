<%-- 
    Document   : addProd
    Created on : 24/06/2021, 01:53:26 AM
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
        %>
        
    <body>
        <!-- llama al archivo topmenu.jsp que contiene la llamada a la sesión y el menú superior -->
        <%@include  file="assets/topmenu.jsp" %>

        <div class="container">
            <div class="row titulo-index">
                <div class="col-12">
                    <H3>¡Hola <%=usr.getNombre()%>!. Aquí podrás agregar productos nuevos.</H3>                     
                </div>               
            </div>
        </div>
                
        <div class="container-lg">
            <div class="row">
                <div class="col-md-10 mx-auto cajaop">
                    <div class="addproduct-form">
                        <h1>Registrar Producto Nuevo</h1>
                        <p class="hint-text">Puedes registrar productos nuevos. Los productos nuevos se registran con "stock 0".<br/>Si deseas agregar stock, ve a la sección de "Entradas".</p>       
                        <form action="#" method="post">
                            <div class="row">
                                <div class="form-group">
                                    <label for="inputNombre">Nombre del Producto</label>
                                    <input type="text" class="form-control" id="inputNombre" placeholder="Un nombre corto..." required>
                                </div>                                
                                <div class="col-sm-4">
                                    <div class="form-group ">
                                        <label for="inputCategoria">Categoría</label>
                                        <select name="selectCat" class="form-select" id="inputCategoria">
                                            <option value="1">General</option>
                                            <option value="2">Vegetales</option>
                                        </select>
                                    </div>
                                </div>               
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <label for="inputPrecio">Precio</label>
                                        <input type="number" class="form-control" id="inputPrecio" placeholder="(S/) Precio de venta..." required>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group ">
                                        <label for="inputUnidad">Unidad</label>
                                        <select name="selectUnid" class="form-select" id="inputUnidad">
                                            <option value="kg">kg</option>
                                            <option value="unidad">unidad</option>
                                            <option value="saco">saco</option>
                                            <option value="lata">lata</option>
                                            <option value="botella">botella</option>
                                        </select>
                                    </div>
                                </div>
                            </div>            
                            <div class="form-group">
                                <label for="inputMessage">Descripción del Producto</label>
                                <textarea class="form-control" id="inputMessage" rows="5" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-paper-plane"></i> Crear</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>        
        
        <script src="js/script.js"></script>
    </body>
</html>
