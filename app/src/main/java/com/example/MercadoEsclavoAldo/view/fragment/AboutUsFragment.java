package com.example.MercadoEsclavoAldo.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.MercadoEsclavoAldo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    @BindView(R.id.buttonHistoriaAboutFragment)
    Button buttonHistoriaAboutFragment;
    @BindView(R.id.textViewHistoriaAboutFragment)
    TextView textViewHistoriaAboutFragment;
    @BindView(R.id.buttonMisionAboutFragment)
    Button buttonMisionAboutFragment;
    @BindView(R.id.textViewMisionAboutFragment)
    TextView textViewMisionAboutFragment;
    @BindView(R.id.buttonVisionAboutFragment)
    Button buttonVisionAboutFragment;
    @BindView(R.id.textViewVisionAboutFragment)
    TextView textViewVisionAboutFragment;
    @BindView(R.id.buttonValoresAboutFragment)
    Button buttonValoresAboutFragment;
    @BindView(R.id.textViewValoresAboutFragment)
    TextView textViewValoresAboutFragment;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about_u, container, false);
        ButterKnife.bind(this, view);


        buttonsClick(buttonHistoriaAboutFragment, textViewHistoriaAboutFragment);
        buttonsClick(buttonMisionAboutFragment, textViewMisionAboutFragment);
        buttonsClick(buttonValoresAboutFragment, textViewValoresAboutFragment);
        buttonsClick(buttonVisionAboutFragment, textViewVisionAboutFragment);


        return view;
    }

    private void buttonsClick(Button button, TextView textView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getVisibility() == View.GONE)
                    textView.setVisibility(View.VISIBLE);
                else textView.setVisibility(View.GONE);

            }
        });
    }

}
