package com.example.MercadoEsclavoAldo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.view.adapter.DetailsViewPagerAdapter;
import com.example.MercadoEsclavoAldo.view.fragment.DetailsFragment;
import com.example.MercadoEsclavoAldo.view.fragment.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private DetailsViewPagerAdapter detailsViewPagerAdapter;
    private FragmentManager fragmentManager;
    private DetailsFragment detailsFragment = new DetailsFragment();
    private List<Fragment> fragmentList = new ArrayList<>();

    public static final String KEY_POSITION = "position";
    public static final String KEY_PRODUCTOS = "productos";

    @BindView(R.id.viewPagerDetails)
    ViewPager viewPagerDetails;

    private Integer adapterPosition;
    private List<Producto> productoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Result result = (Result) bundle.getSerializable(KEY_PRODUCTOS);

        productoList = result.getResults();
        adapterPosition = bundle.getInt(KEY_POSITION);

        cargarListaDeSeries(productoList);
        viewPagerDetails.setCurrentItem(adapterPosition);

        Producto producto = productoList.get(adapterPosition);
        bundle.putSerializable(DetailsFragment.KEY_PRODUCTO, producto);


        detailsFragment.setArguments(bundle);





    }



    private void cargarListaDeSeries(List<Producto> productoList) {
        for (Producto producto : productoList){
            DetailsFragment detailsFragment = DetailsFragment.getInstance(producto);
            fragmentList.add(detailsFragment);
        }
        detailsViewPagerAdapter = new DetailsViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPagerDetails.setAdapter(detailsViewPagerAdapter);
    }




}
