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
    private TextView textoTituloConfiguracion;
    private TextView textoConsejos;
    private TextView textoNoticias;
    private TextView textoRedesSociales;
    private EditText editNombre;
    private EditText editEstatura;
    private EditText editPeso;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inicioViewModel = new ViewModelProvider(this).get(InicioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        textoBienvenida = root.findViewById(R.id.text_mensaje_inicio);
        textoConsejos = root.findViewById(R.id.text_consejos_inicio);
        textoNoticias = root.findViewById(R.id.text_noticias_inicio);
        textoRedesSociales = root.findViewById(R.id.text_redes_sociales_inicio);
        textoTituloConfiguracion = root.findViewById(R.id.tituloConfiguracionPerfil);

        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombre = datos.getString("nombrePerfil", "");
        if (nombre != "") {
            textoBienvenida.setText("BIENVENIDO " + nombre.toUpperCase() + "!");
            textoTituloConfiguracion.setText("Perfil de " + nombre);
        }

        LinearLayout linearInicio = root.findViewById(R.id.linearInicio);
        linearInicio.setVisibility(View.VISIBLE);
        LinearLayout linearConfiguracion = root.findViewById(R.id.linearConfigurarPerfil);
        linearConfiguracion.setVisibility(View.GONE);

        editNombre = root.findViewById(R.id.edittext_nombre_perfil);
        editEstatura = root.findViewById(R.id.edittext_estatura);
        editPeso = root.findViewById(R.id.edittext_peso);



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

        Button configurar = root.findViewById(R.id.botonIrAConfiguracion);
        configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearInicio.setVisibility(View.GONE);
                linearConfiguracion.setVisibility(View.VISIBLE);
                mostrarInfo();
            }
        });

        Button guardar = root.findViewById(R.id.botonGuardarConfiguracionPerfil);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInfo();
                linearInicio.setVisibility(View.VISIBLE);
                linearConfiguracion.setVisibility(View.GONE);
            }
        });

        Button cancelar = root.findViewById(R.id.botonCancelarConfiguracionPerfil);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearInicio.setVisibility(View.VISIBLE);
                linearConfiguracion.setVisibility(View.GONE);
            }
        });
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


    private void guardarInfo() {
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editar = datos.edit();
        editar.putString("nombrePerfil", editNombre.getText().toString());
        editar.putString("estaturaPerfil", editEstatura.getText().toString());
        editar.putString("pesoPerfil", editPeso.getText().toString());
        editar.commit();

        if (editNombre.getText().toString() != "") {
            textoBienvenida.setText("BIENVENIDO " + editNombre.getText().toString().toUpperCase() + "!");
        }
    }

    private void mostrarInfo() {
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombre = datos.getString("nombrePerfil", "");
        editNombre.setText(nombre);
        String estatura = datos.getString("estaturaPerfil", "");
        editEstatura.setText(estatura);
        String peso = datos.getString("pesoPerfil", "");
        editPeso.setText(peso);
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