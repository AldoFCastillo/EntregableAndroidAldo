package com.example.MercadoEsclavoAldo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

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
import com.example.MercadoEsclavoAldo.view.adapter.DetailsViewPagerAdapter;
import com.example.MercadoEsclavoAldo.view.fragment.AboutUsFragment;
import com.example.MercadoEsclavoAldo.view.fragment.DetailsFragment;
import com.example.MercadoEsclavoAldo.view.fragment.HomeFragment;
import com.example.MercadoEsclavoAldo.view.fragment.LoginFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeFragment.notificador {

    private HomeFragment homeFragment = new HomeFragment();
    private AboutUsFragment aboutUSFragment = new AboutUsFragment();
    private FragmentManager fragmentManager;
    private LoginFragment loginFragment = new LoginFragment();

    @BindView(R.id.contenedorDeFragmentsMain)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.drawerMainActivity)
    DrawerLayout drawerMainActivity;
    @BindView(R.id.navigationViewHome)
    NavigationView navigationViewHome;
    @BindView(R.id.toolbarMain)
    Toolbar toolbarMain;
  /*  @BindView(R.id.textViewIngresarHeader)*/
    TextView textViewIngresarHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setFragment(homeFragment);

        setNavigationView();

        setToolbar();

        setHeaderLogin();






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
        }}


    private void setHeaderLogin() {
        View hView =  navigationViewHome.getHeaderView(0);
        textViewIngresarHeader = hView.findViewById(R.id.textViewIngresarHeader);

        textViewIngresarHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(loginFragment);
                drawerMainActivity.closeDrawers();
            }
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
        navigationViewHome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.aboutUsMenuNavigation:
                        setFragment(aboutUSFragment);
                        break;

                    case R.id.navigationViewCerrarSesionItem:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainActivity.this, "Desconexion exitosa", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerMainActivity.closeDrawers();
                return false;
            }

        });
    }

    private void setFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction unaTransaccion = fragmentManager.beginTransaction();
        unaTransaccion.replace(R.id.contenedorDeFragmentsMain, fragment);
        unaTransaccion.commit();

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





}
