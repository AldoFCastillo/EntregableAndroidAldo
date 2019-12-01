package com.example.MercadoEsclavoAldo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    @SerializedName("results")
    private List<Producto> results = null;

    public Result(List<Producto> results) {
        this.results = results;
    }

    public List<Producto> getResults() {
        return results;
    }

    public void setResults(List<Producto> results) {
        this.results = results;
    }
}
