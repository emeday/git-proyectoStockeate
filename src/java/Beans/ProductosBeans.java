/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author emeda
 */
public class ProductosBeans {
    
    private int id_prod;
    private String descripcion,unidad_med;
    private double stock,precio;

    public ProductosBeans(int id_prod, String descripcion, String unidad_med, double stock, double precio) {
        this.id_prod = id_prod;
        this.descripcion = descripcion;
        this.unidad_med = unidad_med;
        this.stock = stock;
        this.precio = precio;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad_med() {
        return unidad_med;
    }

    public void setUnidad_med(String unidad_med) {
        this.unidad_med = unidad_med;
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
