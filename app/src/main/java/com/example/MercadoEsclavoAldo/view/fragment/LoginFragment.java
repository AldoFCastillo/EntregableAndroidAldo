package com.example.MercadoEsclavoAldo.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.view.activity.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Integer RC_SIGN_IN = 0;


    @BindView(R.id.editTextEmailFragmentLogin)
    EditText editTextEmailFragmentLogin;
    @BindView(R.id.editTextPasswordFragmentLogin)
    EditText editTextPasswordFragmentLogin;
    @BindView(R.id.buttonIngresarFragmentLogin)
    Button buttonIngresarFragmentLogin;
    @BindView(R.id.buttonRegistrarFragmentLogin)
    Button buttonRegistrarFragmentLogin;
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

        setButtons();



        return view;
    }

    private void setButtons() {
        mAuth = FirebaseAuth.getInstance();

        //TODO comment

        /*LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });*/

        buttonIngresarFragmentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        buttonRegistrarFragmentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewRegistroLogin.setVisibility(View.VISIBLE);
            }
        });

        buttonEnviarFragmentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });



//Boton Signin en Heeader del NavigationView

        callbackManager = CallbackManager.Factory.create();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
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
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            IrAlHome(user.getEmail());

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "fallo autenticacion", Toast.LENGTH_SHORT).show();
                        }
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


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            linearLayoutIniciarSesionFragmentLogin.setVisibility(View.GONE);
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();

                        }

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

    private void Register() {
        String email = editTextEmailFragmentLogin.getText().toString();
        String password = editTextPasswordFragmentLogin.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            IrAlHome(user.getEmail());
                            Toast.makeText(getContext(), "Se registro con exito", Toast.LENGTH_SHORT).show();
                            cardViewRegistroLogin.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getContext(), "Fallo autenticacion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Login() {
        String email = editTextEmailFragmentLogin.getText().toString();
        String password = editTextPasswordFragmentLogin.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            IrAlHome(user.getEmail());
                        } else {
                            Toast.makeText(getContext(), "fallo ingreso", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void Logout() {
        FirebaseAuth.getInstance().signOut();


        Toast.makeText(getContext(), "Desconexion exitosa", Toast.LENGTH_SHORT).show();
    }


}