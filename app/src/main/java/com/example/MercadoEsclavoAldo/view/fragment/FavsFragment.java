package com.example.MercadoEsclavoAldo.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.view.adapter.ProductDetailsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavsFragment extends Fragment implements LoginFragment.favListener, ProductDetailsAdapter.ProductoAdapterListener{

    @BindView(R.id.recyclerFavs)
    RecyclerView recyclerFavs;

    private LoginFragment loginFragment = new LoginFragment();


    public FavsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favs, container, false);

        ButterKnife.bind(this, view);

        loginFragment.searchFavs(FavsFragment.this);




        return view;
    }

    @Override
    public void sendState(List<ProductoDetalles> productoDetalles) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerFavs.setLayoutManager(layoutManager);
        ProductDetailsAdapter productDetailsAdapter = new ProductDetailsAdapter(productoDetalles, FavsFragment.this);
        recyclerFavs.setAdapter(productDetailsAdapter);
        recyclerFavs.setItemViewCacheSize(20);
        recyclerFavs.setHasFixedSize(true);

    }

    @Override
    public void informarSeleccion(Integer adapterPosition) {

    }
}
