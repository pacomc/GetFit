package com.project.getfit.ui.ejercicios;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.project.getfit.R;
import com.project.getfit.ui.recetas.ListaIngredientes;
import com.project.getfit.ui.recetas.ListaRecetas;
import com.project.getfit.ui.recetas.Receta;
import com.project.getfit.ui.recetas.RecetasFragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EjerciciosFragment extends Fragment {
    private ImageView imageViewEjercicio;
    private TextView textViewNombreEjercicio;
    private TextView textViewParteEjercicio;

    private ListView listViewEjercicios;

    private ProgressBar progressBarEjercicios;

    private LinearLayout linearLayoutEjercicios;
    private LinearLayout linearLayoutEjercicio;

    private SearchView searchViewFragmentEjercicios;

    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_ejercicios, container, false);

        linearLayoutEjercicios = root.findViewById(R.id.linear_fragment_ejercicios);
        linearLayoutEjercicio = root.findViewById(R.id.linear_ejercicio);
        textViewNombreEjercicio = root.findViewById(R.id.text_nombre_ejercicio);
        textViewParteEjercicio = root.findViewById(R.id.text_partecuerpo_ejercicio);
        imageViewEjercicio = root.findViewById(R.id.imagen_ejercicio_individual);
        listViewEjercicios = root.findViewById(R.id.list_ejercicios);
        progressBarEjercicios = root.findViewById(R.id.progressBarEjercicios);
        searchViewFragmentEjercicios = root.findViewById(R.id.search_view_fragment_ejercicios);

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
                    if (linearLayoutEjercicios.getVisibility() == View.GONE) {
                        linearLayoutEjercicios.setVisibility(View.VISIBLE);
                        linearLayoutEjercicio.setVisibility(View.GONE);

                        return true;
                    } else {
                        return false;
                    }

                }
                return false;
            }
        } );


        DatosEjercicios datosEjercicios = new DatosEjercicios(getContext(), listViewEjercicios, progressBarEjercicios);
        datosEjercicios.empezar();

        searchViewFragmentEjercicios.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                datosEjercicios.empezarConBusqueda(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        listViewEjercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                linearLayoutEjercicios.setVisibility(View.GONE);
                linearLayoutEjercicio.setVisibility(View.VISIBLE);
                Ejercicio ejercicioPulsado = datosEjercicios.getEjerciciosFiltrados().get(position);

                textViewNombreEjercicio.setText(ejercicioPulsado.getNombre());
                textViewParteEjercicio.setText(ejercicioPulsado.getParteCuerpo());



                Handler handler = new Handler();

                Glide.with(getContext())
                        .load(ejercicioPulsado.getLinkImagen().replaceAll("370x150", "740x416")) // Para obtener mejores fotos se sustituye el enlace (porque existe)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.with(getContext())
                                                .load(ejercicioPulsado.getLinkImagen()) // Para obtener mejores fotos se sustituye el enlace (porque existe)
                                                .placeholder(R.drawable.ejercicio)
                                                .error(R.drawable.ejercicio_error)
                                                .into(imageViewEjercicio);
                                    }
                                });

                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                //on load success
                                return false;
                            }
                        })
                        .placeholder(R.drawable.ejercicio)
                        .error(R.drawable.ejercicio_error)
                        .into(imageViewEjercicio);


            }
        });

        return root;
    }

}