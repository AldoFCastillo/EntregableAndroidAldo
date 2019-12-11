package com.example.MercadoEsclavoAldo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.User;
import com.example.MercadoEsclavoAldo.view.fragment.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsAdapter extends RecyclerView.Adapter {

    private List<ProductoDetalles> favList;
    private ProductoAdapterListener productoAdapterListener;
    private LoginFragment loginFragment = new LoginFragment();
    private FirebaseFirestore db;

    public ProductDetailsAdapter(List<ProductoDetalles> favList, ProductoAdapterListener productoAdapterListener) {
        this.favList = favList;
        this.productoAdapterListener = productoAdapterListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.celda_producto, parent, false);
        ProductDetailsViewHolder productDetailsViewHolder = new ProductDetailsViewHolder(view);
        return productDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductoDetalles producto = favList.get(position);
        ProductDetailsViewHolder productDetailsViewHolder = (ProductDetailsViewHolder) holder;
        productDetailsViewHolder.bind(producto);
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder {

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

        public ProductDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            // rowView = itemView;

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {

                int adapterPosition = getAdapterPosition();
                productoAdapterListener.informarSeleccion(adapterPosition);

            });

           /* imageViewUnlikeCelda.setOnClickListener(v -> {

                if (addToFavs()) {
                    imageViewUnlikeCelda.setVisibility(View.GONE);
                    lottieLikeCelda.setVisibility(View.VISIBLE);
                    lottieLikeCelda.playAnimation();
                }

            });

            lottieLikeCelda.setOnClickListener(v -> {

                *//*imageViewUnlikeCelda.setVisibility(View.VISIBLE);
                lottieLikeCelda.setVisibility(View.GONE);*//*


            });*/
        }

        private Boolean addToFavs() {

            Boolean ok = false;
            mAuth = FirebaseAuth.getInstance();
            String id;

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                loginFragment.setmAuth(mAuth);
                id = favList.get(getAdapterPosition()).getId();
                loginFragment.addToFavs(id, itemView.getContext());
            }
            return ok;

        }

        public void bind(ProductoDetalles producto) {

            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String id = currentUser.getUid();

            textViewDescripcionCelda.setText(producto.getTitle());
            String precio = "$ " + producto.getPrice();
            textViewPrecioCelda.setText(precio);
            String url = producto.getPictures().get(0).getSecureUrl();
            Glide.with(itemView).load(url).into(imageViewCelda);

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

    public interface ProductoAdapterListener {
        public void informarSeleccion(Integer adapterPosition);
    }



}
