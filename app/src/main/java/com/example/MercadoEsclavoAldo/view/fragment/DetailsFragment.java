package com.example.MercadoEsclavoAldo.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment  {

    public static final String KEY_PRODUCTO = "producto";

    @BindView(R.id.imageViewDetails)
    ImageView imageViewDetails;
    @BindView(R.id.textViewTituloDetails)
    TextView textViewTituloDetails;
    @BindView(R.id.textViewPrecioDetails)
    TextView textViewPrecioDetails;
    @BindView(R.id.textViewDescripcionDetails)
    TextView textViewDescripcionDetails;


    public DetailsFragment() {
        // Required empty public constructor
    }



    public static DetailsFragment getInstance(Producto producto){
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PRODUCTO, producto);
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        Producto producto = (Producto) bundle.getSerializable(KEY_PRODUCTO);

        Glide.with(view).load(producto.getImagen()).into(imageViewDetails);
        textViewPrecioDetails.setText(producto.getPrecio());
        textViewTituloDetails.setText(producto.getTitulo());
        textViewDescripcionDetails.setText(producto.getDescripcion());



        return view;
    }


}
