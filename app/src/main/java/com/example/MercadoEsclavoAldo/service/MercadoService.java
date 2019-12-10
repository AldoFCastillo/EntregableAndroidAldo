package com.example.MercadoEsclavoAldo.service;

import com.example.MercadoEsclavoAldo.model.Description;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MercadoService {


    @GET("/sites/MLA/search &/paging?&offset={offset}&limit={limit}")
    Call<Result> getBusqueda(@Path("limit")Integer limit, @Path("offset") Integer offset, @Query("q") String key);

    @GET ("/items/{path}")
    Call<ProductoDetalles> getProducto(@Path("path") String path);

    @GET ("/items/{path}/description")
    Call<Description> getDescripcion(@Path("path") String path);



    // search/multi?api_key=e00143389939e722c4dd551059122bbf&language=es-AR&page=1&include_adult=false
}
