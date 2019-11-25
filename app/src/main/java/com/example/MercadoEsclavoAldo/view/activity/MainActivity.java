package com.example.MercadoEsclavoAldo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.view.fragment.AboutUSFragment;
import com.example.MercadoEsclavoAldo.view.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeFragment.notificador {

    private HomeFragment homeFragment = new HomeFragment();
    private AboutUSFragment aboutUSFragment = new AboutUSFragment();
    private FragmentManager fragmentManager;
    @BindView(R.id.contenedorDeFragmentsMain)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.drawerMainActivity)
    DrawerLayout drawerMainActivity;
    @BindView(R.id.navigationViewHome)
    NavigationView navigationViewHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setFragment(homeFragment);

        setNavigationView();
    }

    private void setNavigationView() {
        navigationViewHome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.aboutUsMenuNavigation:
                        setFragment(aboutUSFragment);
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
    public void enviarNotificacion(Producto producto) {

        Snackbar.make(coordinatorLayout, "Seleccionaste " + producto.getDescripcion(), Snackbar.LENGTH_SHORT).show();
    }
}
