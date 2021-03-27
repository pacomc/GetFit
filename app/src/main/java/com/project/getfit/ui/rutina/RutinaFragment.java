package com.project.getfit.ui.rutina;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

public class RutinaFragment extends Fragment {


    private View root;

    public LinearLayout contenido_principal;
    public LinearLayout contenido_ejercicio;
    public LinearLayout contenido_empezar;

    public Button boton_ejercicios;
    public Button boton_rutina;
    public Button boton_atras_ejercicios;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rutina, container, false);


        definirVariables(root);

        boton_ejercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarLinear();
                contenido_ejercicio.setVisibility(View.VISIBLE);
            }
        });

        boton_rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarLinear();
                contenido_empezar.setVisibility(View.VISIBLE);
            }
        });

        boton_atras_ejercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarLinear();
                contenido_principal.setVisibility(View.VISIBLE);
            }
        });

        return root;
    }

    public void definirVariables(View root) {
        contenido_principal = root.findViewById(R.id.linearPrincipalRutina);
        contenido_ejercicio = root.findViewById(R.id.linearEjercicioRutina);
        contenido_empezar = root.findViewById(R.id.linearEmpezarRutina);
        boton_ejercicios = root.findViewById(R.id.botonIrAEjercicios);
        boton_rutina = root.findViewById(R.id.botonIrARutina);
        boton_atras_ejercicios = root.findViewById(R.id.botonAtrasEjercicios);
    }

    public void reiniciarLinear() {
        contenido_principal.setVisibility(View.GONE);
        contenido_ejercicio.setVisibility(View.GONE);
        contenido_empezar.setVisibility(View.GONE);
    }


}