package com.project.getfit.ui.rutina;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

public class RutinaFragment extends Fragment {

    private RutinaViewModel rutinaViewModel;

    private View root;

    public LinearLayout contenido_principal;
    public LinearLayout contenido_ejercicio;
    public LinearLayout contenido_empezar;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rutinaViewModel = new ViewModelProvider(this).get(RutinaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rutina, container, false);

        final TextView textView = root.findViewById(R.id.text_slideshow);

        rutinaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        definirVariables();
        reiniciarLinear();
        contenido_principal.setVisibility(View.VISIBLE);

        return root;
    }

    public void definirVariables() {
        contenido_principal = root.findViewById(R.id.linearPrincipalRutina);
        contenido_ejercicio = root.findViewById(R.id.linearEjercicioRutina);
        contenido_empezar = root.findViewById(R.id.linearEmpezarRutina);
    }

    public void reiniciarLinear() {
        contenido_principal.setVisibility(View.GONE);
        contenido_ejercicio.setVisibility(View.GONE);
        contenido_empezar.setVisibility(View.GONE);
    }

    public void irAEjercicios(View v) {
        reiniciarLinear();
        contenido_ejercicio.setVisibility(View.VISIBLE);
    }

    public void irARutina(View v) {
        reiniciarLinear();
        contenido_empezar.setVisibility(View.VISIBLE);
    }
}