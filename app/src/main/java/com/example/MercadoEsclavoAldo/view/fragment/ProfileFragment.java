package com.example.MercadoEsclavoAldo.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.User;
import com.example.MercadoEsclavoAldo.utils.ResultListener;
import com.example.MercadoEsclavoAldo.view.adapter.ProductDetailsAdapter;
import com.example.MercadoEsclavoAldo.view.adapter.ProductoAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProductDetailsAdapter.ProductoAdapterListener, LoginFragment.favListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    LoginFragment loginFragment = new LoginFragment();
    private List<String> idList = new ArrayList<>();
    private List<ProductoDetalles> favoritosList = new ArrayList<>();

    @BindView(R.id.recyclerProfile)
    RecyclerView recyclerProfile;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);
        ProductoController productoController = new ProductoController();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();

        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("usuarios").document(id);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            String asd = user.getNombre();
            idList = user.getFavoritos();
            loginFragment.searchFavs(ProfileFragment.this);


        });


        return view;
    }

    private void searchFavs(ProductoController productoController, ResultListener resultListener) {
        for (String anId : idList) {
            productoController.getProducto(result -> {
                favoritosList.add(result);
            }, anId);
        }
        resultListener.onFinish(favoritosList);
    }

    @Override
    public void informarSeleccion(Integer adapterPosition) {

    }

    @Override
    public void sendState(List<ProductoDetalles> productoDetalles) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerProfile.setLayoutManager(layoutManager);
        ProductDetailsAdapter productDetailsAdapter = new ProductDetailsAdapter(productoDetalles, ProfileFragment.this);
        recyclerProfile.setAdapter(productDetailsAdapter);
        recyclerProfile.setItemViewCacheSize(20);
        recyclerProfile.setHasFixedSize(true);
    }
}
