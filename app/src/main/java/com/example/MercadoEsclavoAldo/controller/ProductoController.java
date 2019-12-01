package com.example.MercadoEsclavoAldo.controller;

import com.example.MercadoEsclavoAldo.dao.ProductoDao;
import com.example.MercadoEsclavoAldo.model.Description;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import java.util.List;

public class ProductoController {

    private ProductoDao productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDao();
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

    public void getOfertas(final ResultListener<Result> listenerDeLaView, String query) {
        productoDAO.getOfertas(new ResultListener<Result>() {
            @Override
            public void onFinish(Result result) {
                listenerDeLaView.onFinish(result);
            }
        }, query );

    }

    public void getProducto(final ResultListener<ProductoDetalles> listenerDeLaView, String path) {
        productoDAO.getProducto(new ResultListener<ProductoDetalles>() {
            @Override
            public void onFinish(ProductoDetalles result) {
                listenerDeLaView.onFinish(result);
            }
        }, path);

    }

    public void getDescripcion(final ResultListener<Description> listenerDeLaView, String path) {
        productoDAO.getDescripcion(new ResultListener<Description>() {
            @Override
            public void onFinish(Description result) {
                listenerDeLaView.onFinish(result);
            }
        }, path);

    }
}
