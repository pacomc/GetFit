package com.project.getfit.ui.rutina;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.project.getfit.R;
import com.project.getfit.ui.ejercicios.DatosEjercicios;
import com.project.getfit.ui.ejercicios.Ejercicio;
import com.project.getfit.ui.ejercicios.ListaEjercicios;
import com.project.getfit.ui.recetas.ListaIngredientes;
import com.project.getfit.ui.recetas.Receta;

import java.util.ArrayList;
import java.util.List;

public class RutinaFragment extends Fragment {

    private String nombreRutinaActualizado;

    private View root;

    private LinearLayout contenido_principal;
    private LinearLayout contenido_ejercicio;
    private LinearLayout contenido_empezar;
    private LinearLayout contenido_nueva_rutina;
    private LinearLayout contenido_actualizar_rutina;

    private Button boton_ejercicios;
    private Button boton_ejercicios_actualizar;
    private Button boton_rutina;
    private Button boton_atras_ejercicios;
    private Button boton_guardar;
    private Button boton_cancelar;
    private Button boton_actualizar_rutina;
    private Button boton_cancelar_actualizar;

    private ArrayAdapter arrayAdapterRutina;
    private ArrayAdapter arrayAdapterEjerciciosActualizados;
    private ArrayAdapter arrayAdapterEjerciciosInsertados;

    private EditText editTextNombreRutina;
    private EditText editTextNombreRutinaActualizada;


    private ListView listViewRutinas;
    private ListView listViewEjerciciosRutina;
    private ListView listViewEjerciciosActualizados;
    private ListView listViewEjerciciosInsertados;

    private ProgressBar progressBarEjerciciosRutina;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_rutina, container, false);
        listViewEjerciciosRutina = root.findViewById(R.id.listViewEjerciciosRutina);
        progressBarEjerciciosRutina = root.findViewById(R.id.progressBarEjerciciosRutina);
        contenido_principal = root.findViewById(R.id.linearPrincipalRutina);
        contenido_ejercicio = root.findViewById(R.id.linearEjercicioRutina);
        contenido_empezar = root.findViewById(R.id.linearEmpezarRutina);
        contenido_nueva_rutina = root.findViewById(R.id.linearNuevaRutina);
        contenido_actualizar_rutina = root.findViewById(R.id.linearActualizarRutina);
        boton_ejercicios = root.findViewById(R.id.botonIrAEjercicios);
        boton_ejercicios_actualizar = root.findViewById(R.id.botonIrAEjerciciosActualizar);
        boton_rutina = root.findViewById(R.id.botonIrARutina);
        boton_atras_ejercicios = root.findViewById(R.id.botonAtrasEjercicios);
        boton_guardar = root.findViewById(R.id.botonGuardarConfiguracionRutina);
        boton_cancelar = root.findViewById(R.id.botonCancelarConfiguracionRutina);
        boton_actualizar_rutina = root.findViewById(R.id.botonActualizarRutina);
        boton_cancelar_actualizar = root.findViewById(R.id.botonCancelarActualizarRutina);
        editTextNombreRutina = root.findViewById(R.id.edittext_nombre_rutina);
        editTextNombreRutinaActualizada = root.findViewById(R.id.edittext_nombre_rutina_actualizar);
        listViewRutinas = root.findViewById(R.id.listaDeRutinas);
        listViewEjerciciosActualizados = root.findViewById(R.id.ListViewEjericiciosActualizados);
        listViewEjerciciosInsertados = root.findViewById(R.id.ListViewEjericiciosAnyadidos);




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
                        reiniciarLinear();
                        contenido_principal.setVisibility(View.VISIBLE);

                        return true;
                    } else {
                        return false;
                    }

                }

                return false;
            }
        } );

        // Para que se muestre la lista de rutinas al principio
        actualizarLista();





        boton_rutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boton_guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombreRutina = String.valueOf(editTextNombreRutina.getText());
                        Rutina rutina = new Rutina(nombreRutina);

                        insertarRutina(rutina);
                        actualizarLista();

                        reiniciarLinear();
                        contenido_principal.setVisibility(View.VISIBLE);
                    }
                });

                boton_ejercicios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatosEjercicios datosEjercicios = new DatosEjercicios(getContext(),listViewEjerciciosRutina, progressBarEjerciciosRutina);
                        datosEjercicios.empezar();

                        reiniciarLinear();
                        contenido_ejercicio.setVisibility(View.VISIBLE);

                    }
                });


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







        return root;
    }

    private void insertarRutina(Rutina rutina) {
        new InsertarRutina().execute(rutina);
    }

    private void insertarEjercicioRutina(Rutina rutina, ArrayList<Ejercicio> ejerciciosActualizados) {
        new InsertarEjercicioRutina(ejerciciosActualizados).execute(rutina);
    }


    private void actualizarRutina(Rutina rutina, String nuevoNombreRutina) {
        nombreRutinaActualizado = nuevoNombreRutina;

        new ActualizarRutina().execute(rutina);
    }

    private void actualizarLista() {
        new ActualizarLista().execute();
    }



    private class InsertarRutina extends AsyncTask<Rutina, Void, String> {

        protected String doInBackground(Rutina... rutina) {
            // Código válido para almacenar las rutinas en una base de datos creadas con Room
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "basedatos-rutinas")
                    .fallbackToDestructiveMigration()
                    .build();;
            RutinaDao rd = db.rutinaDao();

            rd.insertarRutina(rutina[0]);

            return rd.obtenerTodas().toString();
        }


        protected void onPostExecute(String peticionBaseDatos) {
            Log.d("Se han insertado datos:", peticionBaseDatos);
        }
    }

    private class InsertarEjercicioRutina extends AsyncTask<Rutina, Void, String> {
        private ArrayList<Ejercicio> ejerciciosActualizados;

        public InsertarEjercicioRutina(ArrayList<Ejercicio> ejerciciosActualizados) {
            this.ejerciciosActualizados = ejerciciosActualizados;
        }

        protected String doInBackground(Rutina... rutina) {
            // Código válido para almacenar las rutinas en una base de datos creadas con Room
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "basedatos-rutinas")
                    .fallbackToDestructiveMigration()
                    .build();;
            RutinaDao rd = db.rutinaDao();

            rd.insertarEjercicioRutina(rutina[0].getUid(), ejerciciosActualizados);

            return rd.obtenerTodas().toString();
        }


        protected void onPostExecute(String peticionBaseDatos) {
            Log.d("Se han insertado datos:", peticionBaseDatos);
        }
    }

    private class ActualizarRutina extends AsyncTask<Rutina, Void, Rutina> {

        protected Rutina doInBackground(Rutina... rutina) {
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "basedatos-rutinas")
                    .fallbackToDestructiveMigration()
                    .build();;
            RutinaDao rd = db.rutinaDao();

            rd.actualizarRutina(rutina[0].getUid(), nombreRutinaActualizado);

            return rutina[0];
        }


        protected void onPostExecute(Rutina rutinaActualizada) {
            Log.d("Se han actualizado datos:", rutinaActualizada.toString());
        }
    }

    private class ActualizarLista extends AsyncTask<Rutina, Void, List<Rutina>> {

        protected List<Rutina> doInBackground(Rutina... rutinas) {
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "basedatos-rutinas").build();
            RutinaDao rd = db.rutinaDao();
            return rd.obtenerTodas();
        }


        protected void onPostExecute(List<Rutina> listaRutinas) {
            ArrayList<Rutina> listaRutinasArrayList = new ArrayList<>(listaRutinas);

            arrayAdapterRutina = new ListaRutinas(getContext(), listaRutinasArrayList);
            listViewRutinas.setAdapter(arrayAdapterRutina);

            listViewRutinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Rutina rutinaPulsada = listaRutinas.get(position);
                    ArrayList<Ejercicio> ejerciciosOriginales = rutinaPulsada.getEjercicios();

                    editTextNombreRutinaActualizada.setText(rutinaPulsada.getNombreRutina());
                    arrayAdapterEjerciciosActualizados = new ListaEjercicios(getContext(), rutinaPulsada.getEjercicios());
                    listViewEjerciciosActualizados.setAdapter(arrayAdapterEjerciciosActualizados);

                    reiniciarLinear();
                    contenido_actualizar_rutina.setVisibility(View.VISIBLE);

                    boton_actualizar_rutina.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            actualizarRutina(rutinaPulsada, editTextNombreRutinaActualizada.getText().toString());

                            actualizarLista();
                            reiniciarLinear();
                            contenido_principal.setVisibility(View.VISIBLE);
                        }
                    });

                    boton_cancelar_actualizar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Si cancela, modificamos la rutina y ponemos los ejercicios originales
                            insertarEjercicioRutina(rutinaPulsada, ejerciciosOriginales);
                            reiniciarLinear();
                            contenido_principal.setVisibility(View.VISIBLE);
                        }
                    });



                    boton_ejercicios_actualizar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatosEjercicios datosEjercicios = new DatosEjercicios(getContext(),listViewEjerciciosRutina, progressBarEjerciciosRutina);
                            datosEjercicios.empezar();


                            reiniciarLinear();
                            contenido_ejercicio.setVisibility(View.VISIBLE);

                            listViewEjerciciosRutina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    ArrayList<Ejercicio> ejercicios = datosEjercicios.getEjercicios();
                                    Ejercicio ejercicioPulsado = ejercicios.get(position);


                                    ArrayList<Ejercicio> ejerciciosActualizados = new ArrayList<>(ejerciciosOriginales);
                                    ejerciciosActualizados.add(ejercicioPulsado);

                                    insertarEjercicioRutina(rutinaPulsada, ejerciciosActualizados);

                                    arrayAdapterEjerciciosActualizados = new ListaEjercicios(getContext(), ejerciciosActualizados);
                                    listViewEjerciciosActualizados.setAdapter(arrayAdapterEjerciciosActualizados);

                                    reiniciarLinear();
                                    contenido_actualizar_rutina.setVisibility(View.VISIBLE);


                                }
                            });




                        }
                    });




                }
            });


        }
    }



    private void reiniciarLinear() {
        contenido_principal.setVisibility(View.GONE);
        contenido_ejercicio.setVisibility(View.GONE);
        contenido_empezar.setVisibility(View.GONE);
        contenido_nueva_rutina.setVisibility(View.GONE);
        contenido_actualizar_rutina.setVisibility(View.GONE);
    }


}