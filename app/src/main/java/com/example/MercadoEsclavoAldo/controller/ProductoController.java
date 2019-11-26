package com.example.MercadoEsclavoAldo.controller;

import com.example.MercadoEsclavoAldo.dao.ProductoDAO;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import java.util.List;

public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO();
    }

    public void getProductos(final ResultListener<List<Producto>> listenerDeLaView) {
        productoDAO.getProductos(new ResultListener<List<Producto>>() {
            @Override
            public void onFinish(List<Producto> result) {
                listenerDeLaView.onFinish(result);
            }
        });
    }
}
