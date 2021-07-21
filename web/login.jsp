<%-- 
    Document   : login
    Created on : 20/06/2021, 02:19:49 PM
    Author     : Kokox
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        if (session.getAttribute("sesUsername") != null){
            //si existe una sesión, mandamos hacia index
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        %>   
        
    <%@include  file="assets/head.html" %> 
    
    <body>
        <div class="container-fluid bg-login">
            <div class="bg-blanco">
            </div>
            <div class="logo-container">
                 <img src="img/logo.png" alt="alt"/>
             </div>    
             <div class="login-titulo">
                <p class="h1 white">Bienvenido</p>
                
            <div class="formulario-login">
               
                <form class="form-style" action="CRUDServlet" method="POST">
                    <div class="input-container">
                        <i class="bi bi-file-person"></i>
                        <input type="text" id="uname" name="uname" placeholder="Usuario" class=""/>
                    </div>

                    <div class="input-container">
                        <i class="bi bi-lock"></i>
                        <input type="password" id="pass" name="pass" placeholder="Contraseña" class=""/>
                    </div>    
                    
                    <div class="boton-container">
                        <input type="submit" name="submit" value="Ingresar" class="boton-login"/>
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
        </div>
    </body>
</html>
