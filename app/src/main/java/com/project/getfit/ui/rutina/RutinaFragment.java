package com.project.getfit.ui.rutina;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.project.getfit.MainActivity;
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
        root = inflater.inflate(R.layout.fragment_rutina, container, false);

        // Codigo para el boton de pulsar atras
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    if (contenido_principal.getVisibility() == View.GONE) {
                        contenido_principal.setVisibility(View.VISIBLE);
                        contenido_ejercicio.setVisibility(View.GONE);
                        contenido_empezar.setVisibility(View.GONE);
                        return true;
                    } else {
                        return false;
                    }

                }
                return false;
            }
        } );

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




    private void definirVariables(View root) {
        contenido_principal = root.findViewById(R.id.linearPrincipalRutina);
        contenido_ejercicio = root.findViewById(R.id.linearEjercicioRutina);
        contenido_empezar = root.findViewById(R.id.linearEmpezarRutina);
        boton_ejercicios = root.findViewById(R.id.botonIrAEjercicios);
        boton_rutina = root.findViewById(R.id.botonIrARutina);
        boton_atras_ejercicios = root.findViewById(R.id.botonAtrasEjercicios);
    }

    private void reiniciarLinear() {
        contenido_principal.setVisibility(View.GONE);
        contenido_ejercicio.setVisibility(View.GONE);
        contenido_empezar.setVisibility(View.GONE);
    }

}