package com.example.MercadoEsclavoAldo.dao;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void getProductos(ResultListener resultListener) {
        List<Producto> productoList = new ArrayList<>();
        productoList.add(new Producto("Medias de lujo", "$500", R.drawable.medias, "Finas medias realizadas con materiales de la mas alta calidad con estilo Mugre-Print"));
        productoList.add(new Producto("Higado poco uso", "$50000", R.drawable.higado, "Fresquito higado para transplantar en el acto o guardarlo en el freezer"));
        productoList.add(new Producto("Ametralladora de la paz", "$100", R.drawable.arma, "Siente la necesidad de entregar un mensaje de amor al mundo? No lo dude mas."));
        productoList.add(new Producto("Barril magico", "$200",R.drawable.barril, "El regalo ideal para el Dia del Niño!!. Un barril lleno de divertidas sorpresas y mutaciones sin fin (Producto importado de Chernobyl)"));
        productoList.add(new Producto ("Show musical para fiestas y eventos", "$666", R.drawable.band, "Show de banda en vivo para tu fiesta de 15/casamiento/bar mitzvah/velorio. Pasarás una velada de ensueño bailando todos tus hits favoritos de la mano de Los Blasfemos Decadentes"));
        productoList.add(new Producto("Medias de lujo", "$500", R.drawable.medias, "Finas medias realizadas con materiales de la mas alta calidad con estilo Mugre-Print"));
        productoList.add(new Producto("Higado poco uso", "$50000", R.drawable.higado, "Fresquito higado para transplantar en el acto o guardarlo en el freezer"));
        productoList.add(new Producto("Ametralladora de la paz", "$100", R.drawable.arma, "Siente la necesidad de entregar un mensaje de amor al mundo? No lo dude mas."));
        productoList.add(new Producto("Barril magico", "$200",R.drawable.barril, "El regalo ideal para el Dia del Niño!!. Un barril lleno de divertidas sorpresas y mutaciones sin fin (Producto importado de Chernobyl)"));
        productoList.add(new Producto ("Show musical para fiestas y eventos", "$666", R.drawable.band, "Show de banda en vivo para tu fiesta de 15/casamiento/bar mitzvah/velorio. Pasarás una velada de ensueño bailando todos tus hits favoritos de la mano de Los Blasfemos Decadentes"));
        productoList.add(new Producto("Medias de lujo", "$500", R.drawable.medias, "Finas medias realizadas con materiales de la mas alta calidad con estilo Mugre-Print"));
        productoList.add(new Producto("Higado poco uso", "$50000", R.drawable.higado, "Fresquito higado para transplantar en el acto o guardarlo en el freezer"));
        productoList.add(new Producto("Ametralladora de la paz", "$100", R.drawable.arma, "Siente la necesidad de entregar un mensaje de amor al mundo? No lo dude mas."));
        productoList.add(new Producto("Barril magico", "$200",R.drawable.barril, "El regalo ideal para el Dia del Niño!!. Un barril lleno de divertidas sorpresas y mutaciones sin fin (Producto importado de Chernobyl)"));
        productoList.add(new Producto ("Show musical para fiestas y eventos", "$666", R.drawable.band, "Show de banda en vivo para tu fiesta de 15/casamiento/bar mitzvah/velorio. Pasarás una velada de ensueño bailando todos tus hits favoritos de la mano de Los Blasfemos Decadentes"));



        resultListener.onFinish(productoList);
    }

}
