package com.example.MercadoEsclavoAldo.view.fragment;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.MercadoEsclavoAldo.R;

import com.example.MercadoEsclavoAldo.model.User;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private LoginFragment loginFragment = new LoginFragment();




    @BindView(R.id.textViewNombreProfile)
    TextView textViewNombreProfile;
    @BindView(R.id.textViewApellidoProfile)
    TextView textViewApellidoProfile;
    @BindView(R.id.textViewEdadProfile)
    TextView textViewEdadProfile;
    @BindView(R.id.textViewMailProfile)
    TextView textViewMailProfile;
    @BindView(R.id.linearMisDatosProfile)
    LinearLayout linearMisDatosProfile;
    @BindView(R.id.textViewUserNameProfile)
    TextView textViewUserNameProfile;
    @BindView(R.id.linearContendeorFavprotpsProfile)
    LinearLayout linearContendeorFavprotpsProfile;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        setTextsAndFavs();

        setFavoritos();


        return view;
    }



    private void setTextsAndFavs() {


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(id);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);

            textViewMailProfile.setText(user.getMail());
            textViewEdadProfile.setText(user.getEdad());
            textViewApellidoProfile.setText(user.getApellido());
            textViewNombreProfile.setText(user.getNombre());
            textViewUserNameProfile.setText(user.getUserName());

        });
    }





    private void setFavoritos() {
        FavsFragment favsFragment = new FavsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction unaTransaccion = fragmentManager.beginTransaction();
        unaTransaccion.replace(R.id.linearContendeorFavprotpsProfile, favsFragment);
        unaTransaccion.commit();

    }


}
