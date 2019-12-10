package com.example.MercadoEsclavoAldo.dao;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.Description;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.service.MercadoService;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductoDao {

    public static final String BASE_URL = "https://api.mercadolibre.com";
    protected Retrofit retrofit;
    MercadoService mercadoService;

    public ProductoDao() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mercadoService = retrofit.create(MercadoService.class);
    }



    public void getProductos(ResultListener resultListener) {
        List<Producto> productoList = new ArrayList<>();
        productoList.add(new Producto("Medias de lujo", "$500", R.drawable.medias, "Finas medias realizadas con materiales de la mas alta calidad con estilo Mugre-Print"));
        productoList.add(new Producto("Higado poco uso", "$50000", R.drawable.higado, "Fresquito higado para transplantar en el acto o guardarlo en el freezer"));
        productoList.add(new Producto("Ametralladora de la paz", "$100", R.drawable.arma, "Siente la necesidad de entregar un mensaje de amor al mundo? No lo dude mas."));
        productoList.add(new Producto("Barril magico", "$200", R.drawable.barril, "El regalo ideal para el Dia del Niño!!. Un barril lleno de divertidas sorpresas y mutaciones sin fin (Producto importado de Chernobyl)"));
        productoList.add(new Producto("Show musical para fiestas y eventos", "$666", R.drawable.band, "Show de banda en vivo para tu fiesta de 15/casamiento/bar mitzvah/velorio. Pasarás una velada de ensueño bailando todos tus hits favoritos de la mano de Los Blasfemos Decadentes"));
        productoList.add(new Producto("Medias de lujo", "$500", R.drawable.medias, "Finas medias realizadas con materiales de la mas alta calidad con estilo Mugre-Print"));
        productoList.add(new Producto("Higado poco uso", "$50000", R.drawable.higado, "Fresquito higado para transplantar en el acto o guardarlo en el freezer"));
        productoList.add(new Producto("Ametralladora de la paz", "$100", R.drawable.arma, "Siente la necesidad de entregar un mensaje de amor al mundo? No lo dude mas."));
        productoList.add(new Producto("Barril magico", "$200", R.drawable.barril, "El regalo ideal para el Dia del Niño!!. Un barril lleno de divertidas sorpresas y mutaciones sin fin (Producto importado de Chernobyl)"));
        productoList.add(new Producto("Show musical para fiestas y eventos", "$666", R.drawable.band, "Show de banda en vivo para tu fiesta de 15/casamiento/bar mitzvah/velorio. Pasarás una velada de ensueño bailando todos tus hits favoritos de la mano de Los Blasfemos Decadentes"));
        productoList.add(new Producto("Medias de lujo", "$500", R.drawable.medias, "Finas medias realizadas con materiales de la mas alta calidad con estilo Mugre-Print"));
        productoList.add(new Producto("Higado poco uso", "$50000", R.drawable.higado, "Fresquito higado para transplantar en el acto o guardarlo en el freezer"));
        productoList.add(new Producto("Ametralladora de la paz", "$100", R.drawable.arma, "Siente la necesidad de entregar un mensaje de amor al mundo? No lo dude mas."));
        productoList.add(new Producto("Barril magico", "$200", R.drawable.barril, "El regalo ideal para el Dia del Niño!!. Un barril lleno de divertidas sorpresas y mutaciones sin fin (Producto importado de Chernobyl)"));
        productoList.add(new Producto("Show musical para fiestas y eventos", "$666", R.drawable.band, "Show de banda en vivo para tu fiesta de 15/casamiento/bar mitzvah/velorio. Pasarás una velada de ensueño bailando todos tus hits favoritos de la mano de Los Blasfemos Decadentes"));


        resultListener.onFinish(productoList);
    }


    public void getSearchResults(final ResultListener<Result> resultListener, String query, Integer limit, Integer offset) {

        Call<Result> call = mercadoService.getBusqueda(limit, offset, query);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result searchResult = response.body();

                resultListener.onFinish(searchResult);

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ha ocurrido un error" + message);
                t.printStackTrace();

            }
        });

    }


    public void getProducto(final ResultListener<ProductoDetalles> resultListener, String path) {

        Call<ProductoDetalles> call = mercadoService.getProducto(path);

        call.enqueue(new Callback<ProductoDetalles>() {
            @Override
            public void onResponse(Call<ProductoDetalles> call, Response<ProductoDetalles> response) {
                ProductoDetalles searchResult = response.body();

                resultListener.onFinish(searchResult);

            }

            @Override
            public void onFailure(Call<ProductoDetalles> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ha ocurrido un error" + message);
                t.printStackTrace();

            }
        });

    }

    public void getDescripcion(final ResultListener<Description> resultListener, String path) {

        Call<Description> call = mercadoService.getDescripcion(path);

        call.enqueue(new Callback<Description>() {
            @Override
            public void onResponse(Call<Description> call, Response<Description> response) {
                Description searchResult = response.body();

                resultListener.onFinish(searchResult);

            }

            @Override
            public void onFailure(Call<Description> call, Throwable t) {
                String message = t.getMessage();
                System.out.println("ha ocurrido un error" + message);
                t.printStackTrace();

            }
        });

    }

}
