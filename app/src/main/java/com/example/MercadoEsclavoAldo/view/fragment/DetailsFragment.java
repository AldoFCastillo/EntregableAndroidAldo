package com.example.MercadoEsclavoAldo.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.Comment;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.User;
import com.example.MercadoEsclavoAldo.view.adapter.CommentsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    public static final String KEY_PRODUCTO = "producto";
    private List<Comment> commentsList = new ArrayList<>();
    private Map<String, String> commentMap = new HashMap<>();
    private CommentsAdapter commentsAdapter;
    private String id;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private locationListener locationListener;

    @BindView(R.id.imageViewDetails)
    ImageView imageViewDetails;
    @BindView(R.id.textViewTituloDetails)
    TextView textViewTituloDetails;
    @BindView(R.id.textViewPrecioDetails)
    TextView textViewPrecioDetails;
    @BindView(R.id.textViewDescripcionDetails)
    TextView textViewDescripcionDetails;
    @BindView(R.id.buttonEnviarComentarios)
    Button buttonEnviarComentarios;
    @BindView(R.id.editTextComentarios)
    EditText editTextComentarios;
    @BindView(R.id.recyclerComentarios)
    RecyclerView recyclerComentarios;
    @BindView(R.id.textViewPreguntaComentarios)
    TextView textViewPreguntaComentarios;
    @BindView(R.id.buttonMap)
    FloatingActionButton buttonMap;
    @BindView(R.id.fragments)
    LinearLayout fragments;


    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment getInstance(Producto producto) {
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

        id = producto.getId();

        setDetails(view);

        setRecyclerComments();


        setQuestionComments();


        setEnviarButton();


        /*AboutUsFragment aboutUsFragment = new AboutUsFragment();

        setFragment(aboutUsFragment);*/


        return view;
    }


    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction unaTransaccion = fragmentManager.beginTransaction();
        unaTransaccion.replace(R.id.fragments, fragment);
        unaTransaccion.commit();

    }

    private void setQuestionComments() {
        textViewPreguntaComentarios.setOnClickListener(v -> {
            textViewPreguntaComentarios.setVisibility(View.GONE);
            editTextComentarios.setVisibility(View.VISIBLE);
            buttonEnviarComentarios.setVisibility(View.VISIBLE);
            editTextComentarios.requestFocus();
        });
    }

    private void setEnviarButton() {
        buttonEnviarComentarios.setOnClickListener(v -> {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                if (editTextComentarios.equals("")) {
                    Toast.makeText(getContext(), "Debes escribir un comentario", Toast.LENGTH_SHORT).show();
                } else {
                    Comment comment = new Comment();
                    comment.setComment(editTextComentarios.getText().toString());
                    getUserName(comment);
                    commentsList.add(comment);
                    Producto aProduct = new Producto();
                    aProduct.setCommentList(commentsList);
                    db.collection("productos").document(id).set(aProduct).addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Se guardo tu comentario!", Toast.LENGTH_SHORT).show();
                        editTextComentarios.setText("");
                        recyclerComentarios.setAdapter(commentsAdapter);

                    }).addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show());

                }

            }else Toast.makeText(getContext(), "Debes loguearte primero!", Toast.LENGTH_SHORT).show();
        });



    }

    public void getUserName(Comment comment){

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(id);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            String userN = user.getUserName();
            comment.setUsername(user.getUserName());
        });

    }


    public void setLocationListener(locationListener locationListener) {
        this.locationListener = locationListener;
    }

    private void setRecyclerComments() {
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("productos").document(id);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Producto producto1 = documentSnapshot.toObject(Producto.class);
            if (producto1 != null) {
                commentsList = producto1.getCommentList();
                commentsAdapter = new CommentsAdapter(commentsList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerComentarios.setLayoutManager(layoutManager);
                recyclerComentarios.setAdapter(commentsAdapter);
                recyclerComentarios.setItemViewCacheSize(20);
                recyclerComentarios.setHasFixedSize(true);
            }

        });
    }

    private void setDetails(View view) {
        ProductoController productoController = new ProductoController();

        productoController.getProducto(result -> {
            String url = result.getPictures().get(0).getSecureUrl();
            Glide.with(view).load(url).into(imageViewDetails);
            String price = "$ " + result.getPrice();
            textViewPrecioDetails.setText(price);
            textViewTituloDetails.setText(result.getTitle());
            String idDescripcion = result.getId();
            productoController.getDescripcion(result1 -> textViewDescripcionDetails.setText(result1.getPlainText()), idDescripcion);

            buttonMap.setOnClickListener(v -> {
                Double lat = result.getGeolocation().getLatitude();
                Double lon = result.getGeolocation().getLongitude();
                locationListener.sendLocation(lat, lon);
            });

        }, id);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerComments();
    }


    public interface locationListener {
        public void sendLocation(Double lat, Double lon);
    }

}
