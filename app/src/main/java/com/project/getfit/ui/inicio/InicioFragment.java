package com.project.getfit.ui.inicio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.project.getfit.R;
import com.project.getfit.SobrenosotrosActivity;

public class InicioFragment extends Fragment {

    private TextView textoBienvenida;
    private ImageView imagenLogo;
    private com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView videoYoutube;
    private TextView textoVideoYoutube;
    private TextView textoRedesSociales;
    private FrameLayout contenedorTwitter;


    private Button botonSobrenosotros;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        textoBienvenida = root.findViewById(R.id.text_mensaje_inicio);
        textoVideoYoutube = root.findViewById(R.id.text_consejos_inicio);
        textoRedesSociales = root.findViewById(R.id.button_redes_sociales_inicio);
        imagenLogo = root.findViewById(R.id.imagenLogo);
        videoYoutube = root.findViewById(R.id.video_youtube);
        contenedorTwitter = root.findViewById(R.id.container);
        botonSobrenosotros = root.findViewById(R.id.button_redes_sociales_inicio);

        textoBienvenida.setText("¡BIENVENIDO!");

        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombreUsuario = datos.getString("nombrePerfil", "");
        if(!nombreUsuario.equals("")) {
            textoBienvenida.setText("¡BIENVENIDO " + nombreUsuario.toUpperCase() + "!");
        }

        botonSobrenosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SobrenosotrosActivity.class);
                startActivity(intent);
            }
        });



        animacionArriba(imagenLogo);
        animacionArriba(textoBienvenida);
        animacionIzquierda(textoVideoYoutube);
        animacionDerecha(videoYoutube);
        animacionIzquierda(textoRedesSociales);


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
    private void animacionDerecha(View view){
        Animation efectoAnimacionDerecha = AnimationUtils.loadAnimation(getContext(), R.anim.animacion_desde_derecha);
        view.startAnimation(efectoAnimacionDerecha);
    }
}