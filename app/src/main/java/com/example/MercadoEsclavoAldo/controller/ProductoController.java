package com.example.MercadoEsclavoAldo.controller;



import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.MercadoEsclavoAldo.dao.ProductoDao;
import com.example.MercadoEsclavoAldo.model.Description;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.model.User;
import com.example.MercadoEsclavoAldo.utils.ResultListener;

import com.example.MercadoEsclavoAldo.view.fragment.FavsFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductoController implements Serializable {

    public static final Integer PAGE_SIZE = 10;
    private Integer offset = 0;
    private Boolean more = true;
    private ProductoDao productoDAO;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private sendResponseUser sendResponseUser;
    private sendResponseComment sendResponseComment;
    private sendResponseProduct sendResponseProduct;
    private sendResponseFav sendResponseFav;
    private List<String> aFavList = new ArrayList<>();
    private Integer i;


    public ProductoController() {
        this.productoDAO = new ProductoDao();
    }

    public void getProductos(final ResultListener<List<Producto>> listenerDeLaView) {
        productoDAO.getProductos((ResultListener<List<Producto>>) result -> listenerDeLaView.onFinish(result));
    }

    public void getSearchResults(final ResultListener<Result> listenerDeLaView, String query) {
        productoDAO.getSearchResults(result -> {
            listenerDeLaView.onFinish(result);
            if (result.getResults().size() < PAGE_SIZE) {
                more = false;
            }

        }, query, PAGE_SIZE, offset);
        offset = offset + PAGE_SIZE;

    }


    public void getProducto(final ResultListener<ProductoDetalles> listenerDeLaView, String path) {
        productoDAO.getProducto(result -> listenerDeLaView.onFinish(result), path);

    }

    public void getDescripcion(final ResultListener<Description> listenerDeLaView, String path) {
        productoDAO.getDescripcion(result -> listenerDeLaView.onFinish(result), path);

    }

    public Boolean getMore() {
        return more;
    }


    public void getUser() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sendResponseUser.notifNull(false);
            String id = currentUser.getUid();
            db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("usuarios").document(id);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                sendResponseUser.notifUser(user);
            });
        } else sendResponseUser.notifNull(true);
    }


    public void addComment(String id, Producto aProduct){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("productos").document(id).set(aProduct).addOnSuccessListener(aVoid -> {
            sendResponseComment.notifAddComment(true);

        }).addOnFailureListener(e -> sendResponseComment.notifAddComment(false));

    }

    public void getProductFb(String id){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("productos").document(id);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Producto producto1 = documentSnapshot.toObject(Producto.class);
            sendResponseProduct.notifProduct(producto1);

        });
    }

   public void addProfile(String userId, User user ){
       FirebaseFirestore db;
       db = FirebaseFirestore.getInstance();
       db.collection("usuarios").document(userId).set(user).addOnSuccessListener(aVoid ->
               System.out.println("EXITO"));
   }

   public void addToFavs(List<String> favList, String id, Context context){
       this.aFavList = favList;
       mAuth = FirebaseAuth.getInstance();
       FirebaseFirestore db = FirebaseFirestore.getInstance();
       FirebaseUser currentUser = mAuth.getCurrentUser();
       String userId = currentUser.getUid();

       DocumentReference docRef = db.collection("usuarios").document(userId);
       docRef.get().addOnSuccessListener(documentSnapshot -> {
           User user = documentSnapshot.toObject(User.class);
           FirebaseUser aCurrentUser = mAuth.getCurrentUser();
           String uId = aCurrentUser.getUid();
           this.aFavList = user.getFavoritos();
           this.aFavList.add(id);
           user.setFavoritos(this.aFavList);
           db.collection("usuarios").document(userId).set(user).addOnSuccessListener(aVoid -> {
               sendResponseFav.notifAddFav(this.aFavList, uId, true,context);
           }).addOnFailureListener(e -> sendResponseFav.notifAddFav(favList, uId, false,context));

       });
   }

   public void removeFav(String id, List<String> favList, Context context){
       this.aFavList = favList;
       mAuth = FirebaseAuth.getInstance();
       FirebaseFirestore db = FirebaseFirestore.getInstance();
       FirebaseUser currentUser = mAuth.getCurrentUser();
       String userId = currentUser.getUid();

       DocumentReference docRef = db.collection("usuarios").document(userId);
       docRef.get().addOnSuccessListener(documentSnapshot -> {
           User user = documentSnapshot.toObject(User.class);
           FirebaseUser aCurrentUser = mAuth.getCurrentUser();
           aFavList = user.getFavoritos();
           List<String> altFavList = aFavList;
           for (String anId : altFavList) {
               if (anId.equals(id)) {
                   aFavList.remove(id);
                   user.setFavoritos(aFavList);
                   db.collection("usuarios").document(userId).set(user).addOnSuccessListener(aVoid -> {
                       sendResponseFav.notifRemoveFav(aFavList, userId, true, context);
                   }).addOnFailureListener(e -> sendResponseFav.notifRemoveFav(favList, userId, false, context));
                   break;
               }
           }
       });
   }

   public void searchFavs(FavsFragment favListener, List<String> favList){

        this.aFavList = favList;
       i = 0;
       FirebaseFirestore db = FirebaseFirestore.getInstance();
       mAuth = FirebaseAuth.getInstance();
       FirebaseUser currentUser = mAuth.getCurrentUser();
       String id = currentUser.getUid();

       ProductoController productoController = new ProductoController();
       List<ProductoDetalles> productoList = new ArrayList<>();


       DocumentReference docRef = db.collection("usuarios").document(id);
       docRef.get().addOnSuccessListener(documentSnapshot -> {
           User user = documentSnapshot.toObject(User.class);
           aFavList = user.getFavoritos();

           for (String anId : aFavList) {

               productoController.getProducto(result -> {
                   i++;
                   productoList.add(result);
                   if (i.equals(aFavList.size())) {
                       favListener.sendState(productoList);
                   }
               }, anId);
           }
       });

   }


    public void setSendResponseUser(sendResponseUser sendResponseUser) {
        this.sendResponseUser = sendResponseUser;
    }


    public void setSendResponseComment(ProductoController.sendResponseComment sendResponseComment) {
        this.sendResponseComment = sendResponseComment;
    }

    public void setSendResponseProduct(ProductoController.sendResponseProduct sendResponseProduct) {
        this.sendResponseProduct = sendResponseProduct;
    }

    public void setSendResponseFav(ProductoController.sendResponseFav sendResponseFav) {
        this.sendResponseFav = sendResponseFav;
    }

    public interface sendResponseUser {
        void notifUser (User user);
        void notifNull (Boolean isNull);
    }

    public interface sendResponseComment{
        void notifAddComment(Boolean ok);
    }

    public interface sendResponseProduct{
        void notifProduct(Producto producto);
    }

    public interface sendResponseFav{
        void notifAddFav(List<String> favList, String uId, Boolean ok, Context context);
        void notifRemoveFav(List<String> favList, String uId, Boolean ok, Context context);
    }

}
