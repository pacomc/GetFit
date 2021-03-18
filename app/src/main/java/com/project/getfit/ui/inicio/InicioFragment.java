package com.project.getfit.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

public class InicioFragment extends Fragment {

<<<<<<< HEAD
    private InicioViewModel inicioViewModel;
    private TextView textoBienvenida;
    private TextView textoConsejos;
    private TextView textoNoticias;
    private TextView textoRedesSociales;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inicioViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        textoBienvenida = root.findViewById(R.id.text_mensaje_inicio);
        textoConsejos = root.findViewById(R.id.text_consejos_inicio);
        textoNoticias = root.findViewById(R.id.text_noticias_inicio);
        textoRedesSociales = root.findViewById(R.id.text_redes_sociales_inicio);

        inicioViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textoBienvenida.setText(s);
            }
        });

        animacionArriba(textoBienvenida);
        animacionIzquierda(textoConsejos);
        animacionIzquierda(textoNoticias);
        animacionIzquierda(textoRedesSociales);
=======
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

>>>>>>> ad018ab8e9bf55e85b64509005cca6e1726e9078

        return root;
    }

    // Creación efectos visuales pagina de inicio:
    private void animacionArriba(View view){
        Animation efectoAnimacionArriba = AnimationUtils.loadAnimation(getContext(), R.anim.animacion_desde_arriba);
        view.startAnimation(efectoAnimacionArriba);

    }
    private void animacionIzquierda(View view){
        Animation efectoAnimacionIzquierda = AnimationUtils.loadAnimation(getContext(), R.anim.animacion_desde_izquierda);
        view.startAnimation(efectoAnimacionIzquierda);
    }
}