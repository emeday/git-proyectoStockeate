/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author emeda
 * @author Kokox
 */
public class ProductosBeans {
    
    private int id_prod;
    private int idcategoria;
    private String nombre;
    private String descripcion;
    private String unid_med;
    private double stock;
    private double precio;

    public ProductosBeans(int id_prod, int idcategoria, String nombre, String descripcion, String unid_med, double stock, double precio) {
        this.id_prod = id_prod;
        this.idcategoria = idcategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unid_med = unid_med;
        this.stock = stock;
        this.precio = precio;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnid_med() {
        return unid_med;
    }

    public void setUnid_med(String unid_med) {
        this.unid_med = unid_med;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
  
   

}
