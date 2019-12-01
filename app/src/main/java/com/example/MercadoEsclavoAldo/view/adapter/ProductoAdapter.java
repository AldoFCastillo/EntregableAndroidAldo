package com.example.MercadoEsclavoAldo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.Producto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductoAdapter extends RecyclerView.Adapter {

    private List<Producto> productoList;
    private ProductoAdapterListener productoAdapterListener;

    public ProductoAdapter(List<Producto> productoList, ProductoAdapterListener productoAdapterListener) {
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
        ProductoViewHolder productoViewHolder =  (ProductoViewHolder) holder;
        productoViewHolder.bind(producto);

    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    class ProductoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewDescripcionCelda)
        TextView textViewDescripcionCelda;
        @BindView(R.id.textViewPrecioCelda)
        TextView textViewPrecioCelda;
        @BindView(R.id.imageViewCelda)
        ImageView imageViewCelda;


        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int adapterPosition = getAdapterPosition();
                    productoAdapterListener.informarSeleccion(adapterPosition);

                }
            });
        }

        public void bind(Producto producto) {
            textViewDescripcionCelda.setText(producto.getTitle());
            String precio = "$ "+ producto.getPrice();
            textViewPrecioCelda.setText(precio);

            String url = producto.getThumbnail();
            String https = "https";
            url = url.substring(4);
            url = https+url;
            Glide.with(itemView).load(url).into(imageViewCelda);


        }
    }

    public interface ProductoAdapterListener {
        public void informarSeleccion(Integer adapterPosition);
    }
}
