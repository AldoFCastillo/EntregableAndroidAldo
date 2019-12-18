package com.example.MercadoEsclavoAldo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.MercadoEsclavoAldo.dao.ProductoDao;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.Result;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private ProductoDao productoDao;
    public static final Integer PAGE_SIZE = 10;
    private Integer offset = 0;

    public MainViewModel(@NonNull Application application) {
        super(application);

        productoDao = new ProductoDao(application);
    }

    public LiveData<Result> getSearchResult(String query) {

        return productoDao.getSearchResults(query, PAGE_SIZE, offset);
    }

}
