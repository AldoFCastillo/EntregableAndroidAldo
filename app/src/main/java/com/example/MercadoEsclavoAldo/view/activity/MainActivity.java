package com.example.MercadoEsclavoAldo.view.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.view.fragment.AboutUsFragment;
import com.example.MercadoEsclavoAldo.view.fragment.HomeFragment;
import com.example.MercadoEsclavoAldo.view.fragment.LoginFragment;
import com.example.MercadoEsclavoAldo.view.fragment.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeFragment.notificador {

    private HomeFragment homeFragment = new HomeFragment();
    private AboutUsFragment aboutUSFragment = new AboutUsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private FragmentManager fragmentManager;
    private LoginFragment loginFragment = new LoginFragment();
    private String name = "Tu Nombre";
    private Boolean logOk = false;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @BindView(R.id.contenedorDeFragmentsMain)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.drawerMainActivity)
    DrawerLayout drawerMainActivity;
    @BindView(R.id.navigationViewHome)
    NavigationView navigationViewHome;
    @BindView(R.id.toolbarMain)
    Toolbar toolbarMain;

    TextView textViewBienvenidaHeaderLog;
    TextView textViewBienvenidaHeaderLogNombre;
    TextView textViewIngresarHeader;
    TextView textViewBienvenidaHeaderNavigation;
    TextView textViewTextHeaderNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setFragment(homeFragment);

        setNavigationView();

        setToolbar();

        setHeaderLogin(logOk);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemToolbarHome:
                setFragment(homeFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setHeaderLogin(Boolean loginOk) {
        View hView = navigationViewHome.getHeaderView(0);
        textViewIngresarHeader = hView.findViewById(R.id.textViewIngresarHeader);
        textViewBienvenidaHeaderLogNombre = hView.findViewById(R.id.textViewBienvenidaHeaderLogNombre);
        textViewBienvenidaHeaderLog = hView.findViewById(R.id.textViewBienvenidaHeaderLog);
        textViewBienvenidaHeaderNavigation = hView.findViewById(R.id.textViewBienvenidaHeaderNavigation);
        textViewTextHeaderNavigation = hView.findViewById(R.id.textViewTextHeaderNavigation);

        if (loginOk) {
            textViewIngresarHeader.setVisibility(View.GONE);
            textViewTextHeaderNavigation.setVisibility(View.GONE);
            textViewBienvenidaHeaderNavigation.setVisibility(View.GONE);
            textViewBienvenidaHeaderLog.setVisibility(View.VISIBLE);
            textViewBienvenidaHeaderLogNombre.setText(name);
            textViewBienvenidaHeaderLogNombre.setVisibility(View.VISIBLE);


        } else {
            textViewIngresarHeader.setVisibility(View.VISIBLE);
            textViewTextHeaderNavigation.setVisibility(View.VISIBLE);
            textViewBienvenidaHeaderNavigation.setVisibility(View.VISIBLE);
            textViewBienvenidaHeaderLog.setVisibility(View.GONE);
            textViewBienvenidaHeaderLogNombre.setText(name);
            textViewBienvenidaHeaderLogNombre.setVisibility(View.GONE);
        }

        textViewIngresarHeader.setOnClickListener(v -> {

            setFragment(loginFragment);
            drawerMainActivity.closeDrawers();
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbarMain);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerMainActivity,
                toolbarMain,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerMainActivity.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void setNavigationView() {

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        navigationViewHome.setNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.aboutUsMenuNavigation:
                    setFragment(aboutUSFragment);
                    break;

                case R.id.navigationViewCerrarSesionItem:
                    mAuth = FirebaseAuth.getInstance();
                    currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        FirebaseAuth.getInstance().signOut();
                        logOk = false;
                        Toast.makeText(MainActivity.this, "Desconexion exitosa", Toast.LENGTH_SHORT).show();
                        setHeaderLogin(logOk);
                    } else
                        Toast.makeText(MainActivity.this, "Debes loguearte primero!", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.navigationViewPerfil:

                    if (currentUser != null) {
                       // profileFragment.getLinearMisDatosProfile().setVisibility(View.VISIBLE);
                        setFragment(profileFragment);
                    } else
                        Toast.makeText(MainActivity.this, "Debes loguearte primero!", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.navigationViewFavoritosItem:

                    if (currentUser != null) {
                        setFragment(profileFragment);
          //              profileFragment.getLinearMisDatosProfile().setVisibility(View.GONE);
                    } else
                        Toast.makeText(MainActivity.this, "Debes loguearte primero!", Toast.LENGTH_SHORT).show();
                    break;
            }


            drawerMainActivity.closeDrawers();
            return false;
        });
    }

    private void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction unaTransaccion = fragmentManager.beginTransaction();
        unaTransaccion.replace(R.id.contenedorDeFragmentsMain, fragment);
        unaTransaccion.commit();

    }

    public Boolean getLogOk() {
        return logOk;
    }

    public void setLogOk(Boolean logOk) {
        this.logOk = logOk;
    }

    @Override
    public void enviarNotificacion(Integer adapterPosition, List<Producto> productoList) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(DetailsActivity.KEY_POSITION, adapterPosition);
        Result result = new Result(productoList);
        bundle.putSerializable(DetailsActivity.KEY_PRODUCTOS, result);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public String getName() {
        return name;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            //String name = currentUser.getDisplayName();
            textViewBienvenidaHeaderLogNombre.setText("Nombre");
            logOk = true;
            setHeaderLogin(logOk);
            loginFragment.setmAuth(mAuth);
        }
    }


}
