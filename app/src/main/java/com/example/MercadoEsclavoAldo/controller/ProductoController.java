package com.example.MercadoEsclavoAldo.controller;

import com.example.MercadoEsclavoAldo.dao.ProductoDao;
import com.example.MercadoEsclavoAldo.model.Description;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import java.util.List;

public class ProductoController {

    public static final Integer PAGE_SIZE = 10;
    private Integer offset = 0;
    private Boolean more = true;

    private ProductoDao productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDao();
    }

    public void getProductos(final ResultListener<List<Producto>> listenerDeLaView) {
        productoDAO.getProductos((ResultListener<List<Producto>>) result -> listenerDeLaView.onFinish(result));
    }

    public void getSearchResults(final ResultListener<Result> listenerDeLaView, String query) {
        productoDAO.getSearchResults(result -> {
            listenerDeLaView.onFinish(result);
            if (result.getResults().size() < PAGE_SIZE) {
                more = false;
            }

        }, query, PAGE_SIZE, offset);
        offset = offset + PAGE_SIZE;

    }


    public void getProducto(final ResultListener<ProductoDetalles> listenerDeLaView, String path) {
        productoDAO.getProducto(result -> listenerDeLaView.onFinish(result), path);

    }

    public void getDescripcion(final ResultListener<Description> listenerDeLaView, String path) {
        productoDAO.getDescripcion(result -> listenerDeLaView.onFinish(result), path);

    }

    public Boolean getMore() {
        return more;
    }
}
