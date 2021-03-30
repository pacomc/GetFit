package com.project.getfit.ui.rutina;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import androidx.room.Room;

import com.project.getfit.MainActivity;
import com.project.getfit.R;
import com.project.getfit.ui.ejercicios.DatosEjercicios;
import com.project.getfit.ui.ejercicios.Ejercicio;
import com.project.getfit.ui.ejercicios.ListaEjercicios;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RutinaFragment extends Fragment {


    private View root;

    public LinearLayout contenido_principal;
    public LinearLayout contenido_ejercicio;
    public LinearLayout contenido_empezar;
    public LinearLayout contenido_nueva_rutina;

    public Button boton_ejercicios;
    public Button boton_rutina;
    public Button boton_atras_ejercicios;
    public Button boton_tresPuntos;

    private ListView listViewEjerciciosRutina;
    private ProgressBar progressBarEjerciciosRutina;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_rutina, container, false);
        definirVariables(root);

        DatosEjercicios datosEjercicios = new DatosEjercicios(getContext(),listViewEjerciciosRutina, progressBarEjerciciosRutina);
        datosEjercicios.empezar();


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
                        contenido_nueva_rutina.setVisibility(View.GONE);
                        contenido_empezar.setVisibility(View.GONE);
                        return true;
                    } else {
                        return false;
                    }

                }
                return false;
            }
        } );


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
                contenido_nueva_rutina.setVisibility(View.VISIBLE);
            }
        });

        boton_atras_ejercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarLinear();
                contenido_principal.setVisibility(View.VISIBLE);
            }
        });



        /*
        //Ejemplo insertar datos en bd
        Rutina r = new Rutina("Nombre rutina");
        new InsertarRutina().execute(r);

        */


        return root;
    }

    private class InsertarRutina extends AsyncTask<Rutina, Void, String> {

        protected String doInBackground(Rutina... rutina) {
            // Código válido para almacenar las rutinas en una base de datos creadas con Room
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "basedatos-rutinas").build();
            RutinaDao rd = db.rutinaDao();

            rd.insert(rutina[0]);

            return rd.getAll().toString();
        }


        protected void onPostExecute(String peticionBaseDatos) {
            Log.e("Se han insertado datos:", peticionBaseDatos);
        }
    }

    private class CrearLista extends AsyncTask<Rutina, Void, ArrayList<Rutina>> {

        protected ArrayList<Rutina> doInBackground(Rutina... rutinas) {
            // Código válido para almacenar las rutinas en una base de datos creadas con Room
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "basedatos-rutinas").build();
            RutinaDao rd = db.rutinaDao();

            return new ArrayList<>(rd.getAll());
        }


        protected void onPostExecute(ArrayList<Rutina> listaRutinas) {
            // Hacer adapter de listview rutinas.


        }
    }




    private void definirVariables(View root) {
        listViewEjerciciosRutina = root.findViewById(R.id.listViewEjerciciosRutina);
        progressBarEjerciciosRutina = root.findViewById(R.id.progressBarEjerciciosRutina);
        contenido_principal = root.findViewById(R.id.linearPrincipalRutina);
        contenido_ejercicio = root.findViewById(R.id.linearEjercicioRutina);
        contenido_empezar = root.findViewById(R.id.linearEmpezarRutina);
        contenido_nueva_rutina = root.findViewById(R.id.linearNuevaRutina);
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