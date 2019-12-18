package com.example.MercadoEsclavoAldo.dao;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.model.User;
import com.example.MercadoEsclavoAldo.view.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private List<String> favList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();




    public MutableLiveData<User> getUser(List<String> favList){
        this.favList = favList;
        getInstanceAndUser();
        DocumentReference docRef = db.collection("usuarios").document(userId);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            mutableLiveData.setValue(user);
        });

        return mutableLiveData;
    }

    //TODO esto

    public void addToFavs(List<String> favList, String id, Context context){
        getUser(favList).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                FirebaseUser aCurrentUser = mAuth.getCurrentUser();
                String uId = aCurrentUser.getUid();
                List<String> aFavList = user.getFavoritos();
                aFavList.add(id);
                user.setFavoritos(aFavList);
                db.collection("usuarios").document(userId).set(user).addOnSuccessListener(aVoid -> {
                  //  ProductoController.sendResponseFav.notifAddFav(aFavList, uId, true,context);

                }).addOnFailureListener(e ->{});// ProductoController.sendResponseFav.notifAddFav(favList, uId, false,context));

            }
        });


    }

    private void getInstanceAndUser() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();
    }
}
