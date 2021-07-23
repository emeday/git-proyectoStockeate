/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.ProductosBeans;
import Utils.ConexionDB;
import com.google.gson.Gson;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kokox
 */
@WebServlet(name = "PDFServlet", urlPatterns = {"/PDFServlet"})
public class PDFServlet extends HttpServlet {
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
        response.setContentType("application/pdf");

    }

    //PETICIONES GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        String op=request.getParameter("op");
        OutputStream out = response.getOutputStream();
        
        if(op.equals("doTracePrint")){
            String tipoBusqueda = request.getParameter("selectTipoBusqueda");
            String movimientoSearch = request.getParameter("movimientoSearch");
            
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, PageSize.A4.rotate());

            Table table = new Table(UnitValue.createPercentArray(new float[] {4, 4, 4, 4, 4, 4}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setTextAlignment(TextAlignment.LEFT);

            table.addHeaderCell("ID Mov.");
            table.addHeaderCell("Tipo de Mov.");
            table.addHeaderCell("Nombre de Prod.");
            table.addHeaderCell("Cantidad");
            table.addHeaderCell("Fecha de Mov.");
            table.addHeaderCell("Usuario");
            
            String query = " ";
            switch (tipoBusqueda) {
                case "idMov":
                    query = "SELECT mov.id_movimiento, tmov.nombre_tipo_mov, prd.nombre, mov.cantidad, mov.fecha_movimiento, usr.username FROM movimientos mov INNER JOIN tipos_movimientos tmov ON tmov.id_tipo_movimiento = mov.id_tipo_movimiento INNER JOIN productos prd ON prd.id_prod = mov.id_prod INNER JOIN usuarios usr ON usr.idusuario = mov.idusuario WHERE mov.id_movimiento LIKE ? ORDER BY mov.id_movimiento ASC";
                    break;
                case "tipoMov":
                    query = "SELECT mov.id_movimiento, tmov.nombre_tipo_mov, prd.nombre, mov.cantidad, mov.fecha_movimiento, usr.username FROM movimientos mov INNER JOIN tipos_movimientos tmov ON tmov.id_tipo_movimiento = mov.id_tipo_movimiento INNER JOIN productos prd ON prd.id_prod = mov.id_prod INNER JOIN usuarios usr ON usr.idusuario = mov.idusuario WHERE tmov.nombre_tipo_mov LIKE ? ORDER BY mov.id_movimiento ASC";
                    break;
                case "nomMov":
                    query = "SELECT mov.id_movimiento, tmov.nombre_tipo_mov, prd.nombre, mov.cantidad, mov.fecha_movimiento, usr.username FROM movimientos mov INNER JOIN tipos_movimientos tmov ON tmov.id_tipo_movimiento = mov.id_tipo_movimiento INNER JOIN productos prd ON prd.id_prod = mov.id_prod INNER JOIN usuarios usr ON usr.idusuario = mov.idusuario WHERE prd.nombre LIKE ? ORDER BY mov.id_movimiento ASC";
                    break;
                case "fechaMov":
                    query = "SELECT mov.id_movimiento, tmov.nombre_tipo_mov, prd.nombre, mov.cantidad, mov.fecha_movimiento, usr.username FROM movimientos mov INNER JOIN tipos_movimientos tmov ON tmov.id_tipo_movimiento = mov.id_tipo_movimiento INNER JOIN productos prd ON prd.id_prod = mov.id_prod INNER JOIN usuarios usr ON usr.idusuario = mov.idusuario WHERE mov.fecha_movimiento LIKE ? ORDER BY mov.id_movimiento ASC";
                    break;
                case "userMov":
                    query = "SELECT mov.id_movimiento, tmov.nombre_tipo_mov, prd.nombre, mov.cantidad, mov.fecha_movimiento, usr.username FROM movimientos mov INNER JOIN tipos_movimientos tmov ON tmov.id_tipo_movimiento = mov.id_tipo_movimiento INNER JOIN productos prd ON prd.id_prod = mov.id_prod INNER JOIN usuarios usr ON usr.idusuario = mov.idusuario WHERE usr.username LIKE ? ORDER BY mov.id_movimiento ASC";
                    break;
            }            
            
            try {
                PreparedStatement psta = ConexionDB.getConexion().prepareStatement(query);
                psta.setString(1, "%" + movimientoSearch + "%");
                ResultSet rs= psta.executeQuery(); 
                while(rs.next()){
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(rs.getInt(1)))));
                    table.addCell(new Cell().add(new Paragraph(rs.getString(2))));
                    table.addCell(new Cell().add(new Paragraph(rs.getString(3))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(rs.getDouble(4)))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(rs.getDate(5)))));
                    table.addCell(new Cell().add(new Paragraph(rs.getString(6))));
                }
                
                doc.add(table);
                doc.close();
                
            } catch (SQLException e) {
                System.out.println("Error Servlet: " + e);
            }
            
        } else if (op.equals("doProdPrint")){
            String nombreSearch = request.getParameter("nombreSearch");
            
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document doc = new Document(pdfDoc, PageSize.A4.rotate());

            Table table = new Table(UnitValue.createPercentArray(new float[] {4, 6, 8, 3, 3, 3, 3, 4}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setTextAlignment(TextAlignment.LEFT);

            table.addHeaderCell("Código Prod.");
            table.addHeaderCell("Nombre");
            table.addHeaderCell("Descripción");
            table.addHeaderCell("Categoría");
            table.addHeaderCell("Precio");
            table.addHeaderCell("Stock");
            table.addHeaderCell("Unidad");
            table.addHeaderCell("Stock Mínimo");
            
            try {
                PreparedStatement psta=ConexionDB.getConexion().prepareStatement("SELECT prd.id_prod, cat.idcategoria, cat.nombre_categoria, prd.nombre, prd.descripcion, unid.idunidad, unid.simbolo, unid.nombre_unidad, prd.stock, prd.precio, prd.stockMin, prd.stockMax FROM productos AS prd INNER JOIN categorias AS cat ON prd.idcategoria = cat.idcategoria INNER JOIN unidades AS unid ON prd.idunidad = unid.idunidad WHERE prd.nombre LIKE ? ORDER BY prd.id_prod ASC");
                psta.setString(1, "%" + nombreSearch + "%");
                ResultSet rs= psta.executeQuery();
                
                while (rs.next()) {
                    //
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(rs.getInt(1)))));
                    table.addCell(new Cell().add(new Paragraph(rs.getString(4))));
                    table.addCell(new Cell().add(new Paragraph(rs.getString(5))));
                    table.addCell(new Cell().add(new Paragraph(rs.getString(2))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(rs.getDouble(10)))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(rs.getDouble(9)))));     
                    table.addCell(new Cell().add(new Paragraph(rs.getString(8))));  
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(rs.getDouble(11)))));   
                }
                
                doc.add(table);
                doc.close();               

            } catch (Exception e) {
                System.out.println("Error Servlet: "+e);
            }            
        }
        
    }

    
    
    //PETICIONES POST
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
