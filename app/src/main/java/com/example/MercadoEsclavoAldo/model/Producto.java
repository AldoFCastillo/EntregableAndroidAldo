package com.example.MercadoEsclavoAldo.model;

import java.io.Serializable;

public class Producto implements Serializable {
    private String titulo;
    private String precio;
    private Integer imagen;
    private String descripcion;

    public Producto() {
    }

    public Producto(String titulo, String precio, Integer imagen, String descripcion) {
        this.titulo = titulo;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Integer getImagen() {
        return imagen;
    }

    public void setImagen(Integer imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
