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
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.view.fragment.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsAdapter extends RecyclerView.Adapter {

    private List<ProductoDetalles> favList = new ArrayList<>();
    private ProductoAdapterListener productoAdapterListener;
    private LoginFragment loginFragment = new LoginFragment();

    public ProductDetailsAdapter(List<ProductoDetalles> favList, ProductoAdapterListener productoAdapterListener) {
        this.favList = favList;
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
        ProductoDetalles producto = favList.get(position);
        ProductoViewHolder productoViewHolder = (ProductoViewHolder) holder;
        productoViewHolder.bind(producto);
    }

    @Override
    public int getItemCount() {
        return favList.size();
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

        public ProductoViewHolder(@NonNull View itemView) {
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
                ok = (loginFragment.addToFavs(id, itemView.getContext()));
            }
            return ok;

        }

        public void bind(ProductoDetalles producto) {


            textViewDescripcionCelda.setText(producto.getTitle());
            String precio = "$ " + producto.getPrice();
            textViewPrecioCelda.setText(precio);

            String url = producto.getPictures().get(0).getSecureUrl();

            Glide.with(itemView).load(url).into(imageViewCelda);

        }
    }

    public interface ProductoAdapterListener {
        public void informarSeleccion(Integer adapterPosition);
    }

    public interface FavsListener {
        public void informarSeleccion();
    }


}
