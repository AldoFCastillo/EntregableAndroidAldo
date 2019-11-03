package com.example.MercadoEsclavoAldo.model;

import java.io.Serializable;

public class Producto implements Serializable {
    private String descripcion;
    private String precio;
    private Integer imagen;
    private String detalle;

    public Producto() {
    }

    public Producto(String descripcion, String precio, Integer imagen, String detalle) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.detalle = detalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
