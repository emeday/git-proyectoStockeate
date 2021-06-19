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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        String op=request.getParameter("op");
   
        if (op.equals("listar")) {
            
            try {
                PreparedStatement psta=ConexionDB.getConexion().prepareStatement("select * from productos");
                ResultSet rs= psta.executeQuery();
                
                ArrayList<ProductosBeans> lista=new ArrayList<ProductosBeans>();
                while (rs.next()) {
                    
                    ProductosBeans prd=new ProductosBeans(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getDouble(5));
                    lista.add(prd);
   
                }
                
                request.setAttribute("listita", lista);
                request.getRequestDispatcher("listar.jsp").forward(request, response);
                
                
            } catch (Exception e) {
                System.out.println("Error Servlet: "+e);
            }
        }else if (op.equals("vender")) {
                 String cod=request.getParameter("cod");
            try {
           
                PreparedStatement psta=ConexionDB.getConexion().prepareStatement("delete from empleados where codemp=?");
                psta.setString(1, cod);
                psta.executeUpdate();
                request.getRequestDispatcher("CRUDServlet?op=listar").forward(request, response);
                
            } catch (Exception e) {
                System.out.println("Error "+e);
            }
            
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
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
