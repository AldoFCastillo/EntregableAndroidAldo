package com.example.MercadoEsclavoAldo.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.controller.ProductoController;
import com.example.MercadoEsclavoAldo.model.ProductoDetalles;
import com.example.MercadoEsclavoAldo.model.User;
import com.example.MercadoEsclavoAldo.view.activity.MainActivity;
import com.example.MercadoEsclavoAldo.view.adapter.ProductoAdapter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Integer RC_SIGN_IN = 0;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;
    private String userId;
    private List<String> favList = new ArrayList<>();
    private favListener favListener;
    private Integer i = 0;
    private Map<String, List> favMap = new HashMap<>();


    @BindView(R.id.editTextEmailFragmentLogin)
    EditText editTextEmailFragmentLogin;
    @BindView(R.id.editTextPasswordFragmentLogin)
    EditText editTextPasswordFragmentLogin;
    @BindView(R.id.buttonIngresarFragmentLogin)
    Button buttonIngresarFragmentLogin;
    @BindView(R.id.buttonRegistrarFragmentLogin)
    TextView buttonRegistrarFragmentLogin;
    @BindView(R.id.linearLayoutIniciarSesionFragmentLogin)
    LinearLayout linearLayoutIniciarSesionFragmentLogin;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.editTextNombreFragmentLogin)
    EditText editTextNombreFragmentLogin;
    @BindView(R.id.editTextApellidoFragmentLogin)
    EditText editTextApellidoFragmentLogin;
    @BindView(R.id.editTextEdadFragmentLogin)
    EditText editTextEdadFragmentLogin;
    @BindView(R.id.buttonEnviarFragmentLogin)
    Button buttonEnviarFragmentLogin;
    @BindView(R.id.cardViewRegistroLogin)
    CardView cardViewRegistroLogin;
    @BindView(R.id.textViewLogueadoLogin)
    TextView textViewLogueadoLogin;
    @BindView(R.id.textViewNoLogueadoLogin)
    TextView textViewNoLogueadoLogin;


    public LoginFragment() {
        // Required empty public constructor
    }


    CallbackManager callbackManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();


        db = FirebaseFirestore.getInstance();
        setButtons();


        return view;
    }

    public List<String> getFavList() {
        return favList;
    }

    public void setFavList(List<String> favList) {
        this.favList = favList;
    }

    private void setButtons() {
        mAuth = FirebaseAuth.getInstance();

        buttonIngresarFragmentLogin.setOnClickListener(view -> {
            Boolean vacio = (editTextEmailFragmentLogin.getText().equals("") || editTextPasswordFragmentLogin.getText().equals(""));
            if (!vacio) {
                Login();
            } else
                Toast.makeText(getContext(), "Ambos campos deben estar completos", Toast.LENGTH_SHORT).show();
        });

        buttonRegistrarFragmentLogin.setOnClickListener(view -> {
            cardViewRegistroLogin.setVisibility(View.VISIBLE);
            editTextNombreFragmentLogin.requestFocus();
        });

        buttonEnviarFragmentLogin.setOnClickListener(v -> {
                    Boolean regVacio = (editTextNombreFragmentLogin.getText().equals("") || editTextApellidoFragmentLogin.getText().equals("") || editTextEdadFragmentLogin.getText().equals("") || editTextEmailFragmentLogin.getText().equals("") || editTextPasswordFragmentLogin.getText().equals(""));
                    if (!regVacio) {
                        registerUser();
                    } else
                        Toast.makeText(getContext(), "Todos los campos deben estar completos", Toast.LENGTH_SHORT).show();
                }
        );


//Boton Signin en Header del NavigationView

        callbackManager = CallbackManager.Factory.create();

        signInButton.setOnClickListener(view -> {
            switch (view.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.w("error", "signInResult:failed code=" + e.getStatusCode());

        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        IrAlHome(user.getEmail());

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getContext(), "fallo autenticacion", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();

                    }

                });
    }

    private void IrAlHome(String userName) {
        Bundle bundle = new Bundle();
        bundle.putString("userEmail", userName);

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void registerUser() {
        String emailUser = editTextEmailFragmentLogin.getText().toString();
        String password = editTextPasswordFragmentLogin.getText().toString();

        String nombre = editTextNombreFragmentLogin.getText().toString();
        String apellido = editTextApellidoFragmentLogin.getText().toString();
        String edad = editTextEdadFragmentLogin.getText().toString();


        mAuth.createUserWithEmailAndPassword(emailUser, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        userId = user.getUid();
                        setPerfilFirebase(emailUser, password, nombre, apellido, edad);

                        Toast.makeText(getContext(), "Se registró con exito", Toast.LENGTH_SHORT).show();
                        // String name = user.getDisplayName();
                        IrAlHome(user.getEmail());
                        cardViewRegistroLogin.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "Falló el registro!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPerfilFirebase(String emailUser, String password, String nombre, String apellido, String edad) {

        User user = new User();
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEdad(edad);
        user.setPassword(password);
        user.setMail(emailUser);

        db.collection("usuarios").document(userId).set(user).addOnSuccessListener(aVoid ->
                System.out.println("EXITO"));
    }


    /*public logListener getLogListener() {
        return logListener;
    }

    public void setLogListener(logListener logListener) {
        this.logListener = logListener;
    }*/


    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    private void Login() {
        String email = editTextEmailFragmentLogin.getText().toString();
        String password = editTextPasswordFragmentLogin.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        userId = user.getUid();
                        //String name = user.getDisplayName();
                        Toast.makeText(getContext(), "Exito!", Toast.LENGTH_SHORT).show();
                        IrAlHome(user.getEmail());
                    } else {
                        Toast.makeText(getContext(), "fallo ingreso", Toast.LENGTH_SHORT).show();
                    }

                    // ...
                });
    }

    public void addToFavs(String id, Context context) {


        db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();

            /*textViewNoLogueadoLogin.setVisibility(View.GONE);
            textViewLogueadoLogin.setVisibility(View.VISIBLE);
            linearLayoutIniciarSesionFragmentLogin.setVisibility(View.GONE);*/

        favList.add(id);
        favMap.put("favoritos", favList);
        db.collection("usuarios").document(userId).set(favMap).addOnSuccessListener(aVoid -> {
            Toast.makeText(context, "Agregado a Favoritos!", Toast.LENGTH_SHORT).show();
        });

    }

    public void removeFromFavs(String id, Context context) {
        List<String> altFavList = favList;
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userId = currentUser.getUid();

        for (String anId : altFavList) {
            if (anId.equals(id)) {
                favList.remove(id);
                favMap.remove("favoritos");
                favMap.put("favoritos", favList);
                db.collection("usuarios").document(userId).set(favMap).addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                });

            }
        }
    }


    public List<ProductoDetalles> searchFavs(favListener favListener) {
        i = 0;
        this.favListener = favListener;
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        ProductoController productoController = new ProductoController();
        List<ProductoDetalles> productoList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("usuarios").document(id);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            // String asd = user.getNombre();
            favList = user.getFavoritos();

            for (String anId : favList) {

                productoController.getProducto(result -> {
                    i++;
                    productoList.add(result);
                    if (i.equals(favList.size())) {
                        favListener.sendState(productoList);
                    }
                }, anId);
            }
        });


        return productoList;
    }

    /*public void Logout(Context context) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context, "Desconexion exitosa", Toast.LENGTH_SHORT).show();
    }*/

    public interface favListener {
        public void sendState(List<ProductoDetalles> productoDetalles);//, String name);
    }


}