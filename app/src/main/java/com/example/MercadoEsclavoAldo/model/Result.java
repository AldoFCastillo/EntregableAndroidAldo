package com.example.MercadoEsclavoAldo.model;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

    private List<Producto> results;

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
