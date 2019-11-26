package com.example.MercadoEsclavoAldo.view.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MercadoEsclavoAldo.R;

import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.dao.ProductoDAO;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.utils.ResultListener;
import com.example.MercadoEsclavoAldo.view.adapter.ProductoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ProductoAdapter.ProductoAdapterListener {

    @BindView(R.id.recyclerHomeFragment)
    RecyclerView recyclerHomeFragment;

    notificador notificadorFragment;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorFragment = (notificador) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerHomeFragment.setLayoutManager(layoutManager);


        ProductoController productoController = new ProductoController();
        productoController.getProductos(new ResultListener<List<Producto>>() {
            @Override
            public void onFinish(List<Producto> result) {
                ProductoAdapter productoAdapter = new ProductoAdapter(result, HomeFragment.this);
                recyclerHomeFragment.setAdapter(productoAdapter);
                recyclerHomeFragment.setHasFixedSize(true);
            }
        });


        return view;
    }

    @Override
    public void informarSeleccion(Producto producto) {
        notificadorFragment.enviarNotificacion(producto);

    }

    public interface notificador {
        public void enviarNotificacion(Producto producto);
    }

}
