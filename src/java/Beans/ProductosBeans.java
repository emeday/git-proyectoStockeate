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
    private String nombre_categoria;
    private String nombre;
    private String descripcion;
    private int idunidad;
    private String simbolo_unidad;
    private String nombre_unidad;
    private double stock;
    private double precio;
    private double stockMin;
    private double stockMax;
    private String alerta;

    public ProductosBeans(int id_prod, int idcategoria, String nombre_categoria, String nombre, String descripcion, int idunidad, String simbolo_unidad, String nombre_unidad, double stock, double precio, double stockMin, double stockMax, String alerta) {
        this.id_prod = id_prod;
        this.idcategoria = idcategoria;
        this.nombre_categoria = nombre_categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idunidad = idunidad;
        this.simbolo_unidad = simbolo_unidad;
        this.nombre_unidad = nombre_unidad;
        this.stock = stock;
        this.precio = precio;
        this.stockMin = stockMin;
        this.stockMax = stockMax;
        this.alerta = alerta;
    }

    public ProductosBeans(int idcategoria, String nombre_categoria) {
        this.idcategoria = idcategoria;
        this.nombre_categoria = nombre_categoria;
    }

    public ProductosBeans(int idunidad, String nombre_unidad, String simbolo_unidad) {
        this.idunidad = idunidad;
        this.simbolo_unidad = simbolo_unidad;
        this.nombre_unidad = nombre_unidad;
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

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
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

    public int getIdunidad() {
        return idunidad;
    }

    public void setIdunidad(int idunidad) {
        this.idunidad = idunidad;
    }

    public String getSimbolo_unidad() {
        return simbolo_unidad;
    }

    public void setSimbolo_unidad(String simbolo_unidad) {
        this.simbolo_unidad = simbolo_unidad;
    }

    public String getNombre_unidad() {
        return nombre_unidad;
    }

    public void setNombre_unidad(String nombre_unidad) {
        this.nombre_unidad = nombre_unidad;
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

    public double getStockMin() {
        return stockMin;
    }

    public void setStockMin(double stockMin) {
        this.stockMin = stockMin;
    }

    public double getStockMax() {
        return stockMax;
    }

    public void setStockMax(double stockMax) {
        this.stockMax = stockMax;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }
    
}
