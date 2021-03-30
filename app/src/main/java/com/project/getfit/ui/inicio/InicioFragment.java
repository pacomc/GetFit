package com.project.getfit.ui.inicio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

public class InicioFragment extends Fragment {

    private InicioViewModel inicioViewModel;
    private TextView textoBienvenida;
    private ImageView imagenLogo;
    private com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView videoYoutube;
    private TextView textoVideoYoutube;
    private TextView textoRedesSociales;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inicioViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        textoBienvenida = root.findViewById(R.id.text_mensaje_inicio);
        textoVideoYoutube = root.findViewById(R.id.text_consejos_inicio);
        textoRedesSociales = root.findViewById(R.id.text_redes_sociales_inicio);
        imagenLogo = root.findViewById(R.id.imagenLogo);
        videoYoutube = root.findViewById(R.id.video_youtube);

        textoBienvenida.setText("¡BIENVENIDO!");

        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombreUsuario = datos.getString("nombrePerfil", "");
        if(!nombreUsuario.equals("")) {
            textoBienvenida.setText("¡BIENVENIDO " + nombreUsuario.toUpperCase() + "!");
        }

        inicioViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textoBienvenida.setText(s);
            }
        });

        animacionArriba(imagenLogo);
        animacionIzquierda(textoBienvenida);
        animacionIzquierda(textoVideoYoutube);
        animacionIzquierda(videoYoutube);
        animacionIzquierda(textoRedesSociales);




        /*
        super.onCreate(savedInstanceState);

        //Buscamos a ver si hay algún fragmento en el contenedor
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        // En caso de que no haya ningún fragmento, obtenemos los Tweets del Timeline de la API de twtitter y manejamos el Callback
        if (fragment == null) {
            TweetRepository.getInstance().getTimelineAsync(timelineListener); // => timelineListener
        } else {
            View progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
        }

        */



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



    /*
    // Esta clase interna es la encargada de manejar el callback,  tiene dos métodos para manejar la posibilidad de éxito y de error.
    TwitterListener timelineListener = new TwitterAdapter() {

        @Override
        public void gotHomeTimeline(ResponseList<Status> statuses) {
        }
            showTimeline(statuses);
        }

        @Override
        public void onException(TwitterException te, TwitterMethod method) {
            showError();
        }
    */
}