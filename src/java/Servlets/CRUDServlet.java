/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utils.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import Beans.ProductosBeans;
import Beans.UsuariosBeans;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 *
 * @author emeda
 */
@WebServlet(name = "CRUDServlet", urlPatterns = {"/CRUDServlet"})
public class CRUDServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    
    //OPCIONES DEL CRUD (GET)
    //----------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        String op=request.getParameter("op");
   
        //obtiene la lista de productos
        if (op.equals("listar")) {
            
            try {
                PreparedStatement psta=ConexionDB.getConexion().prepareStatement("select * from productos");
                ResultSet rs= psta.executeQuery();
                
                ArrayList<ProductosBeans> lista=new ArrayList<ProductosBeans>();
                
                while (rs.next()) {
                    
                    ProductosBeans prd=new ProductosBeans(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6),rs.getDouble(7));
                    lista.add(prd);
                }
                
                request.setAttribute("listaprod", lista);

            } catch (Exception e) {
                System.out.println("Error Servlet: "+e);
            }
            
        //no implementado - debe realizar el proceso de salidas
        }else if (op.equals("vender")) {
                 Integer cod= Integer.parseInt(request.getParameter("cod"));
                  Double cantidad=Double.parseDouble(request.getParameter("txtCantidad"));
            try {
           
                PreparedStatement psta=ConexionDB.getConexion().prepareStatement("update productos set stock=? where id_prod=?");
                psta.setDouble(1, cantidad);
                psta.setInt(2, cod);
                psta.executeUpdate();
                request.getRequestDispatcher("CRUDServlet?op=listar").forward(request, response);
                
            } catch (Exception e) {
                System.out.println("Error "+e);
            }
            
        //no implementado - debe realizar el proceso de entradas
        }else if (op.equals("insertar")) {
            
             String cod=request.getParameter("txtCod");
             String nom=request.getParameter("txtNom");
             int edad=Integer.parseInt(request.getParameter("txtEdad"));
             String sexo=request.getParameter("txtSexo");
             String pas=request.getParameter("txtPas");
             
            try {
                PreparedStatement psta=ConexionDB.getConexion().prepareStatement("insert into empleados values(?,?,?,?,?)");
                psta.setString(1, cod);
                psta.setString(2, nom);
                psta.setInt(3, edad);
                psta.setString(4, sexo);
                psta.setString(5, pas);
                
                psta.executeUpdate();
                request.getRequestDispatcher("CRUDServlet?op=listar").forward(request, response);
                
            } catch (Exception e) {
                System.out.println("Error "+e);
            }
           
        //redirige a la página login
        } else if (op.equals("login")){
            //direccionamos a la pagina listar.jsp
            request.getRequestDispatcher("login.jsp").forward(request, response); 
        
        //destruye la sesión y redirige al index
        } else if (op.equals("logout")){
            HttpSession session = request.getSession(false);
            if(session != null){
                session.invalidate();
                request.getRequestDispatcher("/index.jsp").forward(request,response); 
            }
            
        }
    }


    //OPCIONES DEL CRUD (POST)
    //------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        String op=request.getParameter("op");
        
        if(op.equals("doLogin")){
            
            String uname = request.getParameter("uname");
            String pass = request.getParameter("pass");
            
            //primero se obtiene la información del usuario a partir de username y password
            try {
                PreparedStatement psta = ConexionDB.getConexion().prepareStatement("SELECT usr.idusuario, usr.username, usr.password, usr.tipo, usr.last_session, usrin.nombre, usrin.apellido FROM usuarios AS usr INNER JOIN usuarios_info AS usrin ON usr.idusuario = usrin.idusuario WHERE usr.username = ? AND usr.password = ?");
                psta.setString(1, uname);
                psta.setString(2, pass);
                ResultSet rs = psta.executeQuery();
                
                //comprobar que solo existe 1 usuario con el "username" y "password"
                if(rs.next()){                        
                    //se formatea last_session para Date
                    //se actualiza el último inicio de sesión
                    Date date = new Date();
                    DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                        

                    //se prepara el query para actualizar last_session
                    PreparedStatement pstau = ConexionDB.getConexion().prepareStatement("UPDATE usuarios SET last_session = ? WHERE username = ?");
                    pstau.setString(1, fechaHora.format(date));
                    pstau.setString(2, uname);
                    pstau.executeUpdate();

                    //se guarda la info del usuario en un objeto de tipo UsuariosBeans
                    UsuariosBeans usr = new UsuariosBeans(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getString(6),rs.getString(7));  

                    //se asigna a la sesión al usuario logeado
                    HttpSession session = request.getSession(true);
                    session.setAttribute("sesUsername", usr);

                    //mandamos al usuario a index.jsp
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                    
                } else {
                    System.out.println("USUARIO INCORRECTO");
                    String msg = "Error: El usuario y/o la clave son incorrectas.";
                    request.setAttribute("msg", msg);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }                
                
            } catch (SQLException ex) {
                Logger.getLogger(CRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
           
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
