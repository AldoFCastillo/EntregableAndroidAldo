package com.example.MercadoEsclavoAldo.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;


import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.view.adapter.DetailsViewPagerAdapter;
import com.example.MercadoEsclavoAldo.view.fragment.DetailsFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.locationListener{

    private DetailsViewPagerAdapter detailsViewPagerAdapter;
    private DetailsFragment detailsFragment = new DetailsFragment();
    private List<Fragment> fragmentList = new ArrayList<>();

    public static final String KEY_POSITION = "position";
    public static final String KEY_PRODUCTOS = "productos";


    @BindView(R.id.viewPagerDetails)
    ViewPager viewPagerDetails;
    @BindView(R.id.toolbarDetails)
    Toolbar toolbarDetails;


    private Integer adapterPosition;
    private List<Producto> productoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbarDetails);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Result result = (Result) bundle.getSerializable(KEY_PRODUCTOS);
        productoList = result.getResults();
        adapterPosition = bundle.getInt(KEY_POSITION);


        cargarListaDeProductos(productoList);
        viewPagerDetails.setCurrentItem(adapterPosition);

        Producto producto = productoList.get(adapterPosition);
        bundle.putSerializable(DetailsFragment.KEY_PRODUCTO, producto);


        detailsFragment.setArguments(bundle);

    }


    private void cargarListaDeProductos(List<Producto> productoList) {
        for (Producto producto : productoList) {
            DetailsFragment detailsFragment = DetailsFragment.getInstance(producto);
            detailsFragment.setLocationListener(DetailsActivity.this);
            fragmentList.add(detailsFragment);
        }
        detailsViewPagerAdapter = new DetailsViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPagerDetails.setAdapter(detailsViewPagerAdapter);
    }


    @Override
    public void sendLocation(Double lat, Double lon) {
        Intent intent = new Intent(DetailsActivity.this, MapActivity.class);
        Bundle mapBundle = new Bundle();
        mapBundle.putDouble(MapActivity.LATITUDE, lat);
        mapBundle.putDouble(MapActivity.LONGITUDE, lon);
        intent.putExtras(mapBundle);
        startActivity(intent);
    }
}
