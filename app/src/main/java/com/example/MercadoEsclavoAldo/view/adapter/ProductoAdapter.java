package com.example.MercadoEsclavoAldo.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.User;
import com.example.MercadoEsclavoAldo.utils.ItemMoveCallback;
import com.example.MercadoEsclavoAldo.view.fragment.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductoAdapter extends RecyclerView.Adapter implements ItemMoveCallback.ItemTouchHelperContract {

    private List<Producto> productoList = new ArrayList<>();
    private List<ProductoDetalles> productoDetallesList = new ArrayList<>();
    private ProductoAdapterListener productoAdapterListener;
    private FirebaseFirestore db;
    private LoginFragment loginFragment = new LoginFragment();

    public ProductoAdapter(List<Producto> productoList, ProductoAdapterListener productoAdapterListener) {//, LoginFragment loginFragment) {


        this.productoList = productoList;
        this.productoAdapterListener = productoAdapterListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_producto, parent, false);
        ProductoViewHolder productoViewHolder = new ProductoViewHolder(view);

        return productoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Producto producto = productoList.get(position);
        ProductoViewHolder productoViewHolder = (ProductoViewHolder) holder;
        productoViewHolder.bind(producto);


    }

    @Override
    public int getItemCount() {

        return productoList.size();

    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(productoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(productoList, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(ProductoViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onRowClear(ProductoViewHolder myViewHolder) {
        myViewHolder.rowView.setBackgroundColor(Color.WHITE);
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewDescripcionCelda)
        TextView textViewDescripcionCelda;
        @BindView(R.id.textViewPrecioCelda)
        TextView textViewPrecioCelda;
        @BindView(R.id.imageViewCelda)
        ImageView imageViewCelda;
        @BindView(R.id.lottieLikeCelda)
        LottieAnimationView lottieLikeCelda;
        @BindView(R.id.imageViewUnlikeCelda)
        ImageView imageViewUnlikeCelda;
        private FirebaseAuth mAuth;


        View rowView;
        private List<String> favoritosId = new ArrayList<>();


        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            rowView = itemView;

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {

                int adapterPosition = getAdapterPosition();
                productoAdapterListener.informarSeleccion(adapterPosition);

            });

            imageViewUnlikeCelda.setOnClickListener(v -> {

                if (addToFavs(itemView)) {
                    imageViewUnlikeCelda.setVisibility(View.GONE);
                    lottieLikeCelda.setVisibility(View.VISIBLE);
                    lottieLikeCelda.playAnimation();
                }

            });

            lottieLikeCelda.setOnClickListener(v -> {
                if (removeFav(itemView)){

                imageViewUnlikeCelda.setVisibility(View.VISIBLE);
                lottieLikeCelda.setVisibility(View.GONE);}


            });
        }

        private Boolean removeFav(@NonNull View itemView) {
            Boolean ok = false;
            mAuth = FirebaseAuth.getInstance();
            String id;

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                loginFragment.setmAuth(mAuth);
                id = (productoList.get(getAdapterPosition())).getId();
                loginFragment.removeFromFavs(id, itemView.getContext());
                ok = true;
            } else
                Toast.makeText(itemView.getContext(), "Primero debes loguearte!", Toast.LENGTH_SHORT).show();

            return ok;
        }

        private Boolean addToFavs(@NonNull View itemView) {

            Boolean ok = false;
            mAuth = FirebaseAuth.getInstance();
            String id;

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                loginFragment.setmAuth(mAuth);
                id = (productoList.get(getAdapterPosition())).getId();
                loginFragment.addToFavs(id, itemView.getContext());
                ok = true;
            } else
                Toast.makeText(itemView.getContext(), "Primero debes loguearte!", Toast.LENGTH_SHORT).show();
            return ok;

        }




        public void bind(Producto producto) {

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            textViewDescripcionCelda.setText(producto.getTitle());
            String precio = "$ " + producto.getPrice();
            textViewPrecioCelda.setText(precio);
            String url = producto.getThumbnail();
            String https = "https";
            url = url.substring(4);
            url = https + url;
            Glide.with(itemView).load(url).into(imageViewCelda);

            if (currentUser != null) {

                String id = currentUser.getUid();
                db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("usuarios").document(id);
                docRef.get().addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    List<String> listaDeFavs = user.getFavoritos();

                    for (String anId : listaDeFavs) {
                        if (anId.equals(producto.getId())) {
                            imageViewUnlikeCelda.setVisibility(View.GONE);
                            lottieLikeCelda.setVisibility(View.VISIBLE);
                            lottieLikeCelda.playAnimation();
                            break;
                        }
                    }

                });
            }
        }
    }

        public interface ProductoAdapterListener {
            public void informarSeleccion(Integer adapterPosition);
        }

        public interface FavsListener {
            public void informarSeleccion();
        }
    }
