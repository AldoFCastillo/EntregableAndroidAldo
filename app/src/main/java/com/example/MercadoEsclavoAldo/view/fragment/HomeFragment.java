package com.example.MercadoEsclavoAldo.view.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.MercadoEsclavoAldo.R;

import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.Producto;

import com.example.MercadoEsclavoAldo.utils.ItemMoveCallback;
import com.example.MercadoEsclavoAldo.view.adapter.ProductoAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ProductoAdapter.ProductoAdapterListener {

    @BindView(R.id.recyclerHomeFragment)
    RecyclerView recyclerHomeFragment;
    @BindView(R.id.textViewTituloBusquedaFragmentHome)
    TextView textViewTituloBusquedaFragmentHome;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<Producto> productoList = new ArrayList<>();
    private String lastQuery;


    private notificador notificadorFragment;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.notificadorFragment = (notificador) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        setOfertas();
        refresh();

        return view;
    }


    public void setOfertas() {
        setSearch("ofertas");

    }

    private void setRecycler(String query, ProductoController productoController, ProductoAdapter productoAdapter) {

        this.lastQuery = query;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerHomeFragment.setLayoutManager(layoutManager);


        ItemTouchHelper.Callback callback = new ItemMoveCallback(productoAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerHomeFragment);

        recyclerHomeFragment.setAdapter(productoAdapter);
        swipeRefreshLayout.setRefreshing(false);
        recyclerHomeFragment.setItemViewCacheSize(6);
        recyclerHomeFragment.setHasFixedSize(true);
        recyclerHomeFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Integer actualPosition = layoutManager.findLastVisibleItemPosition();
                Integer lastPosition = layoutManager.getItemCount();
                if (actualPosition.equals(lastPosition - 4)) {
                    nexPage(query, productoController, productoAdapter);
                }


            }
        });

    }


    public void refresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            setSearch(lastQuery);

        });
    }

    public void setSearch(String query) {
        ProductoController productoController = new ProductoController();

        productoController.getSearchResults(result -> {

            productoList = result.getResults();
            ProductoAdapter productoAdapter = new ProductoAdapter(productoList, HomeFragment.this);
            setRecycler(query, productoController, productoAdapter);
            textViewTituloBusquedaFragmentHome.setText(query);

        }, query);

    }

    public void nexPage(String query, ProductoController productoController, ProductoAdapter productoAdapter) {

        if (productoController.getMore()) {
            productoController.getSearchResults(result -> {
                List<Producto> otraList = result.getResults();
                productoList.addAll(otraList);
                productoAdapter.addProductList(productoList);

            }, query);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();

    }

    @Override
    public void informarSeleccion(Integer adapterPosition) {
        notificadorFragment.enviarNotificacion(adapterPosition, productoList);

    }


    public interface notificador {
        public void enviarNotificacion(Integer adapterPosition, List<Producto> productoList);
    }

}
