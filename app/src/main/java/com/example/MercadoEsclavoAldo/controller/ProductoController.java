package com.example.MercadoEsclavoAldo.controller;

import com.example.MercadoEsclavoAldo.dao.productoDao;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import java.util.List;

public class ProductoController {

    private productoDao productoDAO;

    public ProductoController() {
        this.productoDAO = new productoDao();
    }

    public void getProductos(final ResultListener<List<Producto>> listenerDeLaView) {
        productoDAO.getProductos(new ResultListener<List<Producto>>() {
            @Override
            public void onFinish(List<Producto> result) {
                listenerDeLaView.onFinish(result);
            }
        });
    }

    public void getSearchResults(final ResultListener<Result> listenerDeLaView, String query) {
        productoDAO.getSearchResults(new ResultListener<Result>() {
            @Override
            public void onFinish(Result result) {
                listenerDeLaView.onFinish(result);
            }
        }, query );

    }
}
