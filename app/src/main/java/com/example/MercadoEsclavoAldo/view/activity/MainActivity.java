package com.example.MercadoEsclavoAldo.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.model.Producto;
import com.example.MercadoEsclavoAldo.model.Result;
import com.example.MercadoEsclavoAldo.view.adapter.DetailsViewPagerAdapter;
import com.example.MercadoEsclavoAldo.view.fragment.AboutUsFragment;
import com.example.MercadoEsclavoAldo.view.fragment.DetailsFragment;
import com.example.MercadoEsclavoAldo.view.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeFragment.notificador {

    private HomeFragment homeFragment = new HomeFragment();
    private AboutUsFragment aboutUSFragment = new AboutUsFragment();
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
