package com.example.MercadoEsclavoAldo.service;

import com.example.MercadoEsclavoAldo.model.Description;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MercadoService {


    @GET("/sites/MLA/search")
    Call<Result> getBusqueda(@Query("q") String key, @Query("offset") Integer offset, @Query("limit")Integer limit);

    @GET ("/items/{path}")
    Call<ProductoDetalles> getProducto(@Path("path") String path);

    @GET ("/items/{path}/description")
    Call<Description> getDescripcion(@Path("path") String path);



}
