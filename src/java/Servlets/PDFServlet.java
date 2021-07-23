/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.UsuariosBeans;
import Utils.ConexionDB;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            //obtengo los datos de la sesión
            UsuariosBeans usr = (UsuariosBeans)request.getSession(false).getAttribute("sesUsername");    
            
            //se verifica que el usuario esté logeado
            if (usr != null){
                String tipoBusqueda = request.getParameter("selectTipoBusqueda");
                String movimientoSearch = request.getParameter("movimientoSearch");

                //se crea el objeto Document de tipo A4 en horizontal
                PdfWriter writer = new PdfWriter(out);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document doc = new Document(pdfDoc, PageSize.A4.rotate());

                //path al proyecto - carpeta de imagenes
                String path = request.getSession().getServletContext().getRealPath ("/") + "/img/logo-color.png";

                //se establece la imagen, se dimensiona y se centra
                ImageData imageData = ImageDataFactory.create(path);
                Image pdfImg = new Image(imageData);
                pdfImg.setWidth(200);
                pdfImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
                doc.add(pdfImg);

                //salto de linea en blanco
                doc.add(new Paragraph("\n"));

                //parrafo
                String parrafo1 = "En este reporte se muestra el historial de movimientos hechos en el sistema.";
                Paragraph prf = new Paragraph(parrafo1);
                doc.add(prf);

                Table table = new Table(UnitValue.createPercentArray(new float[] {4, 4, 4, 4, 4, 4}));
                table.setWidth(UnitValue.createPercentValue(100));
                table.setTextAlignment(TextAlignment.LEFT);

                //color de cabecera
                Color color = new DeviceRgb(134, 145, 250);
                Color colorHeader = new DeviceRgb(255, 255, 255);

                //se establece las cabeceras de la tabla, el color, bold y posicionado al centro
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("ID Mov.").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Tipo de Mov.").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Nombre de Prod.").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Cantidad").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Fecha de Mov.").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Usuario").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));

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
            }
            
        } else if (op.equals("doProdPrint")){
            //obtengo los datos de la sesión
            UsuariosBeans usr = (UsuariosBeans)request.getSession(false).getAttribute("sesUsername");    
            
            //se verifica que el usuario esté logeado
            if (usr != null){
                String nombreSearch = request.getParameter("nombreSearch");

                PdfWriter writer = new PdfWriter(out);
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document doc = new Document(pdfDoc, PageSize.A4.rotate());

                //path al proyecto - carpeta de imagenes
                String path = request.getSession().getServletContext().getRealPath ("/") + "/img/logo-color.png";

                //se establece la imagen, se dimensiona y se centra
                ImageData imageData = ImageDataFactory.create(path);
                Image pdfImg = new Image(imageData);
                pdfImg.setWidth(200);
                pdfImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
                doc.add(pdfImg);

                //salto de linea en blanco
                doc.add(new Paragraph("\n"));

                //parrafo
                String parrafo1 = "En este reporte se muestran los productos agregados al sistema.";
                Paragraph prf = new Paragraph(parrafo1);
                doc.add(prf);            

                Table table = new Table(UnitValue.createPercentArray(new float[] {4, 6, 8, 3, 3, 3, 3, 4}));
                table.setWidth(UnitValue.createPercentValue(100));
                table.setTextAlignment(TextAlignment.LEFT);

                //color de cabecera
                Color color = new DeviceRgb(134, 145, 250);
                Color colorHeader = new DeviceRgb(255, 255, 255);            

                //se establece las cabeceras de la tabla, el color, bold y posicionado al centro
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Código Prod.").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Nombre").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Descripción").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Categoría").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Precio").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Stock").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Unidad").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));
                table.addHeaderCell(new Cell().setBackgroundColor(color).add(new Paragraph("Stock Mínimo").setBold().setTextAlignment(TextAlignment.CENTER).setFontColor(colorHeader)));

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
