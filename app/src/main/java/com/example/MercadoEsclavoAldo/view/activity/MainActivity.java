package com.example.MercadoEsclavoAldo.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.MercadoEsclavoAldo.R;
import com.example.MercadoEsclavoAldo.view.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction unaTransaccion = fragmentManager.beginTransaction();
        unaTransaccion.replace(R.id.contenedorDeFragmentsMain, homeFragment);
        unaTransaccion.commit();
    }
}
