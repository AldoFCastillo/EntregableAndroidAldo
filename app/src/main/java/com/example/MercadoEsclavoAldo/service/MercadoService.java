package com.example.MercadoEsclavoAldo.service;

import com.example.MercadoEsclavoAldo.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MercadoService {

    /*@GET("/sites/MLA/search?q=%22auto%22")
    Call<Result> getBusqueda();//(@Query("q") String key);*/

    @GET("/sites/MLA/search")
    Call<Result> getBusqueda(@Query("q") String key);



    // search/multi?api_key=e00143389939e722c4dd551059122bbf&language=es-AR&page=1&include_adult=false
}
