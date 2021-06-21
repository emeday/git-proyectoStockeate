/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Date;

/**
 *
 * @author Kokox
 */
public class UsuariosBeans {
    
    private int idusuario;
    private String username;
    private String password;
    private int tipo;
    private Date last_session;
    
    //info
    private String nombre;
    private String Apellido;

    public UsuariosBeans(int idusuario, String username, String password, int tipo, Date last_session, String nombre, String Apellido) {
        this.idusuario = idusuario;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.last_session = last_session;
        this.nombre = nombre;
        this.Apellido = Apellido;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getLast_session() {
        return last_session;
    }

    public void setLast_session(Date last_session) {
        this.last_session = last_session;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }
    
    
    
}
