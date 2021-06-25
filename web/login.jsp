<%-- 
    Document   : login
    Created on : 20/06/2021, 02:19:49 PM
    Author     : Kokox
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include  file="assets/head.html" %>
    
    <body>
        
        <div class="container">
            <div class="row">
                <form action="CRUDServlet" method="POST">
                    <div class="form-group">
                        <label>Usuario</label>
                        <input type="text" id="uname" name="uname" placeholder="Usuario" class="form-control"/>
                    </div>

                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" id="pass" name="pass" placeholder="*****" class="form-control"/>
                    </div>    
                    
                    <div class="form-group">
                        <input type="submit" name="submit" value="Acceder" class="btn btn-success"/>
                    </div>
                    <input type="hidden" name="op" value="doLogin"/>
                </form>
             
            <%
                //Mensaje de error de usuario o contraseña
                if(request.getAttribute("msg")!=null){
                    String msg = request.getAttribute("msg").toString();
                    %>
                    
                    <div class="alert alert-danger" role="alert">
                        <%=msg%>
                    </div>    
                <%
                }
                %>
                
            </div>
        </div>
        
    </body>
</html>
