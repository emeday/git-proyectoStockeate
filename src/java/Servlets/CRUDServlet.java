/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utils.ConexionDB;
import java.io.IOException;
import java.sql.PreparedStatement;
import Beans.ProductosBeans;
import Beans.UsuariosBeans;
import com.google.gson.Gson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
   
        //obtiene la lista de productos buscando por nombre
        if (op.equals("listar")) {
            
            String nombreSearch = request.getParameter("nombreSearch");
            String alerta = "";
            
            try {
                PreparedStatement psta=ConexionDB.getConexion().prepareStatement("SELECT prd.id_prod, cat.idcategoria, cat.nombre_categoria, prd.nombre, prd.descripcion, unid.idunidad, unid.simbolo, unid.nombre_unidad, prd.stock, prd.precio, prd.stockMin, prd.stockMax FROM productos AS prd INNER JOIN categorias AS cat ON prd.idcategoria = cat.idcategoria INNER JOIN unidades AS unid ON prd.idunidad = unid.idunidad WHERE prd.nombre LIKE ? ORDER BY prd.id_prod ASC");
                psta.setString(1, "%" + nombreSearch + "%");
                ResultSet rs= psta.executeQuery();
                
                ArrayList<ProductosBeans> lista=new ArrayList<>();
                
                while (rs.next()) {
                    double stock = rs.getDouble(9);
                    double stockMin = rs.getDouble(11);
                    
                    if (stock < stockMin){
                        alerta = "pocoStock";
                    } else {
                        alerta = "normalStock";
                    }
                    ProductosBeans prd=new ProductosBeans(rs.getInt(1),rs.getInt(2), rs.getString(3),rs.getString(4),rs.getString(5), rs.getInt(6),rs.getString(7),rs.getString(8), rs.getDouble(9),rs.getDouble(10), rs.getDouble(11), rs.getDouble(12), alerta);
                    lista.add(prd);
                }
                
                //request.setAttribute("listaprod", lista);
                
                //se crea la cadena en json
                String json = new Gson().toJson(lista);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);                 

            } catch (Exception e) {
                System.out.println("Error Servlet: "+e);
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
        
        //muestra las unidades y categorías en la página de agregar producto
        } else if (op.equals("addProd")){
            try {
                PreparedStatement psta= ConexionDB.getConexion().prepareStatement("SELECT * FROM categorias");
                PreparedStatement pstb= ConexionDB.getConexion().prepareStatement("SELECT * FROM unidades");
                ResultSet rs = psta.executeQuery();
                ResultSet rsb = pstb.executeQuery();
                
                ArrayList<ProductosBeans> listaPrdCat = new ArrayList<>();
                ArrayList<ProductosBeans> listaPrdUnid = new ArrayList<>();
                
                while(rs.next()){
                    ProductosBeans prd = new ProductosBeans(rs.getInt(1), rs.getString(2));
                    listaPrdCat.add(prd);
                }
                
                while(rsb.next()){
                    ProductosBeans prd = new ProductosBeans(rsb.getInt(1), rsb.getString(2), rsb.getString(3));
                    listaPrdUnid.add(prd);
                }
                
                request.setAttribute("listaPrdCat", listaPrdCat);
                request.setAttribute("listaPrdUnid", listaPrdUnid);
                request.getRequestDispatcher("addProd.jsp").forward(request, response);                
            } catch (SQLException ex) {
                Logger.getLogger(CRUDServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        //agrega un producto a la base de datos
        } else if (op.equals("doAddProd")){
            //ingresa un producto en la base de datos
            int prdId = 0;
            Map<String, String> map = new HashMap<>();     
            
            try {
                PreparedStatement psta = ConexionDB.getConexion().prepareStatement("INSERT INTO productos(idcategoria, nombre, descripcion, idunidad, stock, precio, stockMin, stockMax) "
                        + "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                
                int idcategoria = Integer.parseInt(request.getParameter("selectCat"));
                String nombre = request.getParameter("inputNombre");
                String descripcion = request.getParameter("inputDescripcion");
                int idunidad = Integer.parseInt(request.getParameter("selectUnid"));
                double stock = 0; // el stock es 0 al crear el producto
                double precio = Double.parseDouble(request.getParameter("inputPrecio"));
                double stockMin = Double.parseDouble(request.getParameter("inputMin"));
                double stockMaxRate = 1; //1 = 100% de stock máximo [futura implementación]
                
                psta.setInt(1, idcategoria);
                psta.setString(2, nombre);
                psta.setString(3, descripcion);
                psta.setInt(4, idunidad);
                psta.setDouble(5, stock);
                psta.setDouble(6, precio);
                psta.setDouble(7, stockMin);
                psta.setDouble(8, stockMaxRate);
                //ejecuto el query
                psta.executeUpdate();
                
                //objeto ResultSet con el ID generado en "psta"
                ResultSet rs = psta.getGeneratedKeys();
                
                if (rs.next()){
                    //id obtenido de psta
                    prdId = rs.getInt(1);
                    //se prepara el segundo query
                    PreparedStatement pstb = ConexionDB.getConexion().prepareStatement("INSERT INTO movimientos(id_prod, id_tipo_movimiento, cantidad, idusuario) VALUES (?,?,?,?)");

                    //obtengo los datos de la sesión
                    UsuariosBeans usr = (UsuariosBeans)request.getSession(false).getAttribute("sesUsername");
            
                    int tipoMovimiento = 1; //1 = creado
                    double cantidad = 0; //0 stock al ser creado
                    int idUsuario = usr.getIdusuario();

                    pstb.setInt(1, prdId);
                    pstb.setInt(2, tipoMovimiento);
                    pstb.setDouble(3, cantidad);
                    pstb.setInt(4, idUsuario);
                    //ejecuto el query    
                    pstb.executeUpdate();

                    //el query de inserción en "movimientos" fue exitoso, se envía mensaje de exito al agregar un producto
                    System.out.println("[PROCESO EXITOSO] El producto y el movimiento se agregaron correctamente.");

                    //se setea los mensajes en "map"
                    map.put("estado", "exitoso");
                    map.put("mensaje", "El producto y el movimiento se agregaron correctamente.");

                    //se crea la cadena en json
                    String json = new Gson().toJson(map);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);                     
                }        
            } catch (Exception e) {
                System.out.println("Error Servlet: " + e);
            }
            
        } else if (op.equals("eliminar")){
            String cod = request.getParameter("codigo");
            try {
                PreparedStatement psta = ConexionDB.getConexion().prepareStatement("DELETE FROM movimientos WHERE id_prod = ?");
                psta.setString(1, cod);
                psta.executeUpdate();
                
                PreparedStatement pstb = ConexionDB.getConexion().prepareStatement("DELETE FROM productos WHERE id_prod = ?");
                pstb.setString(1, cod);
                pstb.executeUpdate();
                
                request.getRequestDispatcher("/index.jsp").forward(request,response);   
            } catch (Exception e) {
                System.out.println("Error Eliminar: "+e);
            }
            
        //setea los valores del producto en la interfaz de edición    
        } else if (op.equals("editar")){
            String cod = request.getParameter("codigo");
            String alerta = "";
            try {
                PreparedStatement psta = ConexionDB.getConexion().prepareStatement("SELECT prd.id_prod, cat.idcategoria, cat.nombre_categoria, prd.nombre, prd.descripcion, unid.idunidad, unid.simbolo, unid.nombre_unidad, prd.stock, prd.precio, prd.stockMin, prd.stockMax FROM productos AS prd INNER JOIN categorias AS cat ON prd.idcategoria = cat.idcategoria INNER JOIN unidades AS unid ON prd.idunidad = unid.idunidad WHERE prd.id_prod = ?");
                psta.setString(1, cod);
                ResultSet rs = psta.executeQuery();
                
                ArrayList<ProductosBeans> listaInfoProd=new ArrayList<>();
                
                if(rs.next()){
                    double stock = rs.getDouble(9);
                    double stockMin = rs.getDouble(11);
                    
                    if (stock < stockMin){
                        alerta = "pocoStock";
                    } else {
                        alerta = "normalStock";
                    }                    
                    ProductosBeans prd=new ProductosBeans(rs.getInt(1),rs.getInt(2), rs.getString(3),rs.getString(4),rs.getString(5), rs.getInt(6),rs.getString(7),rs.getString(8), rs.getDouble(9),rs.getDouble(10), rs.getDouble(11), rs.getDouble(12), alerta);
                    listaInfoProd.add(prd);
                } else {
                    System.out.println("Error en la consulta.");
                } 
                
                request.setAttribute("listaInfoProd", listaInfoProd);
                
                //obtiene las categorías y unidades de la bd
                PreparedStatement pstb= ConexionDB.getConexion().prepareStatement("SELECT * FROM categorias");
                PreparedStatement pstc= ConexionDB.getConexion().prepareStatement("SELECT * FROM unidades");
                ResultSet rsb = pstb.executeQuery();
                ResultSet rsc = pstc.executeQuery();
                
                ArrayList<ProductosBeans> listaPrdCat = new ArrayList<>();
                ArrayList<ProductosBeans> listaPrdUnid = new ArrayList<>();
                
                while(rsb.next()){
                    ProductosBeans prd = new ProductosBeans(rsb.getInt(1), rsb.getString(2));
                    listaPrdCat.add(prd);
                }
                
                while(rsc.next()){
                    ProductosBeans prd = new ProductosBeans(rsc.getInt(1), rsc.getString(2), rsc.getString(3));
                    listaPrdUnid.add(prd);
                }
                
                request.setAttribute("listaPrdCat", listaPrdCat);
                request.setAttribute("listaPrdUnid", listaPrdUnid);                
                request.getRequestDispatcher("editProd.jsp").forward(request, response);
            } catch (Exception e) {
                System.out.println("Error Servlet: " + e);
            }
            
        } else if (op.equals("doEditProd")){
            Map<String, String> map = new HashMap<>();            
            try {
                PreparedStatement psta = ConexionDB.getConexion().prepareStatement("UPDATE productos SET idcategoria = ?, nombre = ?, descripcion = ?, idunidad = ?, precio = ?, stockMin = ? WHERE id_prod = ?");
                
                int idcategoria = Integer.parseInt(request.getParameter("selectCat"));
                String nombre = request.getParameter("inputNombre");
                String descripcion = request.getParameter("inputDescripcion");
                int idunidad = Integer.parseInt(request.getParameter("selectUnid"));
                double precio = Double.parseDouble(request.getParameter("inputPrecio")); 
                int idProd = Integer.parseInt(request.getParameter("inputIdProd"));
                double stockMin = Double.parseDouble(request.getParameter("inputMin"));
                //double stockMaxRate = 0; //1 = 100% de stock máximo [futura implementación]                
                
                psta.setInt(1, idcategoria);
                psta.setString(2, nombre);
                psta.setString(3, descripcion);
                psta.setInt(4, idunidad);
                psta.setDouble(5, precio);
                psta.setDouble(6, stockMin);
                psta.setInt(7, idProd);                
                //ejecuto query
                psta.executeUpdate();
                
                //agregamos el movimiento                
                PreparedStatement pstb = ConexionDB.getConexion().prepareStatement("INSERT INTO movimientos(id_prod, id_tipo_movimiento, idusuario) VALUES (?,?,?)");
                
                UsuariosBeans usr = (UsuariosBeans)request.getSession(false).getAttribute("sesUsername");                
                int tipoMovimiento = 5; //5 = editado
                int idUsuario = usr.getIdusuario();  
                
                pstb.setInt(1, idProd);
                pstb.setInt(2, tipoMovimiento);
                pstb.setInt(3, idUsuario);
                //ejecuto query
                pstb.executeUpdate();
                
                //el query de inserción en "movimientos" fue exitoso, se envía mensaje de exito al editar un producto
                System.out.println("[PROCESO EXITOSO] El producto y el movimiento se actualizo/agrego correctamente.");

                //se setea los mensajes en "map"
                map.put("estado", "exitoso");
                map.put("mensaje", "El producto se actualizó correctamente. Se agregó el registro de movimiento respectivo.");

                //se crea la cadena en json
                String json = new Gson().toJson(map);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);                     
            } catch (Exception e) {
                System.out.println("Error Servlet: " + e);
            }
            
        } else if (op.equals("infoInOutProd")){
            String cod = request.getParameter("codigo");
            String alerta = "";
            
            try {
                PreparedStatement psta = ConexionDB.getConexion().prepareStatement("SELECT prd.id_prod, cat.idcategoria, cat.nombre_categoria, prd.nombre, prd.descripcion, unid.idunidad, unid.simbolo, unid.nombre_unidad, prd.stock, prd.precio, prd.stockMin, prd.stockMax FROM productos AS prd INNER JOIN categorias AS cat ON prd.idcategoria = cat.idcategoria INNER JOIN unidades AS unid ON prd.idunidad = unid.idunidad WHERE prd.id_prod = ?");
                psta.setString(1, cod);
                ResultSet rs = psta.executeQuery();
                
                ArrayList<ProductosBeans> listaInfoProd=new ArrayList<>();
                
                if(rs.next()){
                    double stock = rs.getDouble(9);
                    double stockMin = rs.getDouble(11);
                    
                    if (stock < stockMin){
                        alerta = "pocoStock";
                    } else {
                        alerta = "normalStock";
                    }
                    
                    ProductosBeans prd=new ProductosBeans(rs.getInt(1),rs.getInt(2), rs.getString(3),rs.getString(4),rs.getString(5), rs.getInt(6),rs.getString(7),rs.getString(8), rs.getDouble(9),rs.getDouble(10), rs.getDouble(11), rs.getDouble(12), alerta);
                    listaInfoProd.add(prd);
                } else {
                    System.out.println("Error en la consulta.");
                } 
                
                //se crea la cadena en json
                String json = new Gson().toJson(listaInfoProd);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);                  
            } catch (Exception e) {
                System.out.println("Error Servlet: " + e);
            }
            
        } else if (op.equals("addoutStock")){
            Map<String, String> map = new HashMap<>();
            int codigo = Integer.parseInt(request.getParameter("codigo"));
            double newStock = Double.parseDouble(request.getParameter("newStock"));
            String tipoOP = request.getParameter("tipoOP");
            
            try {
                //entrada de stock
                if(tipoOP.equals("entrada")){
                    PreparedStatement psta = ConexionDB.getConexion().prepareStatement("UPDATE productos SET stock = stock + ? WHERE id_prod = ?");
                    psta.setDouble(1, newStock);
                    psta.setInt(2, codigo);
                    //ejecuto query
                    psta.executeUpdate();
                    
                    //agregamos el movimiento                
                    PreparedStatement pstb = ConexionDB.getConexion().prepareStatement("INSERT INTO movimientos(id_prod, id_tipo_movimiento, cantidad, idusuario) VALUES (?,?,?,?)");

                    UsuariosBeans usr = (UsuariosBeans)request.getSession(false).getAttribute("sesUsername");                
                    int tipoMovimiento = 2; //2 = entrada
                    int idUsuario = usr.getIdusuario();  

                    pstb.setInt(1, codigo);
                    pstb.setInt(2, tipoMovimiento);
                    pstb.setDouble(3, newStock);
                    pstb.setInt(4, idUsuario);
                    //ejecuto query
                    pstb.executeUpdate();

                    //el query de inserción en "movimientos" fue exitoso, se envía mensaje de exito al agregar stock
                    System.out.println("[PROCESO EXITOSO] El stock y el movimiento se actualizo/agrego correctamente.");

                    //se setea los mensajes en "map"
                    map.put("estado", "exitoso");
                    map.put("mensaje", "El stock se actualizó correctamente.");

                    //se crea la cadena en json
                    String json = new Gson().toJson(map);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);                     
                
                //salida de stock
                } else if(tipoOP.equals("salida")){
                    PreparedStatement psta = ConexionDB.getConexion().prepareStatement("UPDATE productos SET stock = stock - ? WHERE id_prod = ?");
                    psta.setDouble(1, newStock);
                    psta.setInt(2, codigo);
                    //ejecuto query
                    psta.executeUpdate();
                    
                    //agregamos el movimiento                
                    PreparedStatement pstb = ConexionDB.getConexion().prepareStatement("INSERT INTO movimientos(id_prod, id_tipo_movimiento, cantidad, idusuario) VALUES (?,?,?,?)");

                    UsuariosBeans usr = (UsuariosBeans)request.getSession(false).getAttribute("sesUsername");                
                    int tipoMovimiento = 3; //2 = entrada
                    int idUsuario = usr.getIdusuario();  

                    pstb.setInt(1, codigo);
                    pstb.setInt(2, tipoMovimiento);
                    pstb.setDouble(3, newStock);
                    pstb.setInt(4, idUsuario);
                    //ejecuto query
                    pstb.executeUpdate();

                    //el query de inserción en "movimientos" fue exitoso, se envía mensaje de exito al salir stock
                    System.out.println("[PROCESO EXITOSO] El stock y el movimiento se actualizo/agrego correctamente.");

                    //se setea los mensajes en "map"
                    map.put("estado", "exitoso");
                    map.put("mensaje", "El stock se actualizó correctamente.");

                    //se crea la cadena en json
                    String json = new Gson().toJson(map);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);                     
                } 
            } catch (Exception e) {
                System.out.println("Error Servlet: " + e);
            }
            
        } else if (op.equals("trace")){
            //direccionamos a la pagina trace.jsp
            request.getRequestDispatcher("trace.jsp").forward(request, response);    
            
        } else if (op.equals("doTrace")){
            String tipoBusqueda = request.getParameter("selectTipoBusqueda");
            String movimientoSearch = request.getParameter("movimientoSearch");
            
            //lista de objetos
            List<Object[]> lista = null;
            
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
                lista = new ArrayList<>();
                while(rs.next()){
                    Object[] columna = new Object[6];
                    columna[0] = rs.getInt(1);
                    columna[1] = rs.getString(2);
                    columna[2] = rs.getString(3);
                    columna[3] = rs.getDouble(4);
                    columna[4] = rs.getDate(5);
                    columna[5] = rs.getString(6);
                    lista.add(columna);
                }
                
                //se crea la cadena en json
                String json = new Gson().toJson(lista);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);                
                
            } catch (IOException | SQLException e) {
                System.out.println("Error Servlet: " + e);
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
