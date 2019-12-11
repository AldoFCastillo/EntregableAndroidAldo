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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements ProductoController.sendResponseUser, ProductoController.sendResponseComment, ProductoController.sendResponseProduct {

    public static final String KEY_PRODUCTO = "producto";
    private List<Comment> commentsList = new ArrayList<>();
    private ProductoController productoController = new ProductoController();
    private CommentsAdapter commentsAdapter;
    private String id;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private locationListener locationListener;
    private Boolean isNull;
    private String userName;

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

        setController();

        setDetails(view);

        setComments();

        setQuestionComments();


        setEnviarButton();


        /*AboutUsFragment aboutUsFragment = new AboutUsFragment();

        setFragment(aboutUsFragment);*/


        return view;
    }

    private void setController() {
        productoController.setSendResponseUser(DetailsFragment.this);
        productoController.setSendResponseComment(this);
        productoController.setSendResponseProduct(this);
        productoController.getUser();
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

            if (!isNull) {
                if (editTextComentarios.getText().equals(" ")) {
                    Toast.makeText(getContext(), "Debes escribir un comentario", Toast.LENGTH_SHORT).show();
                } else {
                    Comment comment = new Comment();
                    comment.setComment(editTextComentarios.getText().toString());
                    comment.setUsername(userName);
                    commentsList.add(comment);
                    Producto aProduct = new Producto();
                    aProduct.setCommentList(commentsList);
                    setControllerListeners(productoController);
                    productoController.addComment(id, aProduct);
                }

            } else
                Toast.makeText(getContext(), "Debes loguearte primero!", Toast.LENGTH_SHORT).show();
        });


    }



    public void setLocationListener(locationListener locationListener) {
        this.locationListener = locationListener;
    }

    private void setControllerListeners(ProductoController productoController){
        productoController.setSendResponseUser(DetailsFragment.this);
        productoController.setSendResponseComment(DetailsFragment.this);
        productoController.setSendResponseProduct(DetailsFragment.this);

    }

    private void setComments(){
        setControllerListeners(productoController);
        productoController.getProductFb(id);
    }

    private void setRecyclerComments(Producto producto1) {
        if (producto1 != null) {
            commentsList = producto1.getCommentList();
            commentsAdapter = new CommentsAdapter(commentsList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerComentarios.setLayoutManager(layoutManager);
            recyclerComentarios.setAdapter(commentsAdapter);
            recyclerComentarios.setItemViewCacheSize(20);
            recyclerComentarios.setHasFixedSize(true);
        }
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
        setComments();
    }

    @Override
    public void notifUser(User user) {
        userName = user.getUserName();

    }

    @Override
    public void notifNull(Boolean isNull) {
        this.isNull = isNull;
    }

    @Override
    public void notifAddComment(Boolean ok) {
        if (ok) {
            Toast.makeText(getContext(), "Se guardo tu comentario!", Toast.LENGTH_SHORT).show();
            editTextComentarios.setText("");
            recyclerComentarios.setAdapter(commentsAdapter);
        } else Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifProduct(Producto producto1) {
        setRecyclerComments(producto1);
    }


    public interface locationListener {
        public void sendLocation(Double lat, Double lon);
    }

}
