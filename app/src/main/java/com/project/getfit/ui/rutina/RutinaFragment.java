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
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.project.getfit.R;
import com.project.getfit.ui.ejercicios.DatosEjercicios;
import com.project.getfit.ui.ejercicios.Ejercicio;
import com.project.getfit.ui.ejercicios.ListaEjercicios;

import java.util.ArrayList;
import java.util.List;

public class RutinaFragment extends Fragment {

    private String nombreRutinaActualizado;

    private View root;

    private LinearLayout linearPrincipal;
    private LinearLayout linearEjercicios;
    private LinearLayout linearActualizarRutina;
    private LinearLayout linearBotonEliminar;

    private Button botonEjerciciosActualizar;
    private Button botonNuevaRutina;
    private Button botonActualizarRutina;
    private Button botonCancelarActualizar;
    private Button botonEliminarRutina;
    private Button botonFiltraPectorales;
    private Button botonFiltraEspalda;
    private Button botonFiltraPiernas;
    private Button botonFiltraBiceps;
    private Button botonFiltraAbdomen;
    private Button botonFiltraHombros;
    private Button botonFiltraPantorrillas;
    private Button botonFiltraTriceps;
    private Button botonFiltraTodos;

    private SearchView searchViewEjercicios;


    private ArrayAdapter arrayAdapterRutina;
    private ArrayAdapter arrayAdapterEjerciciosActualizados;

    private EditText editTextNombreRutinaActualizada;

    private TextView textViewTituloRutina;

    private ListView listViewRutinas;
    private ListView listViewEjerciciosRutina;
    private ListView listViewEjerciciosActualizados;

    private ProgressBar progressBarEjerciciosRutina;

    private DatosEjercicios datosEjercicios;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_rutina, container, false);
        listViewEjerciciosRutina = root.findViewById(R.id.listViewEjerciciosRutina);
        progressBarEjerciciosRutina = root.findViewById(R.id.progressBarEjerciciosRutina);
        linearPrincipal = root.findViewById(R.id.linearPrincipalRutina);
        linearEjercicios = root.findViewById(R.id.linearEjercicioRutina);
        linearActualizarRutina = root.findViewById(R.id.linearActualizarRutina);
        botonEjerciciosActualizar = root.findViewById(R.id.botonIrAEjerciciosActualizar);
        botonNuevaRutina = root.findViewById(R.id.botonIrARutina);
        botonActualizarRutina = root.findViewById(R.id.botonActualizarRutina);
        botonCancelarActualizar = root.findViewById(R.id.botonCancelarActualizarRutina);
        editTextNombreRutinaActualizada = root.findViewById(R.id.edittext_nombre_rutina_actualizar);
        listViewRutinas = root.findViewById(R.id.listaDeRutinas);
        listViewEjerciciosActualizados = root.findViewById(R.id.ListViewEjericiciosActualizados);
        linearBotonEliminar = root.findViewById(R.id.linearBotonEliminar);
        botonEliminarRutina = root.findViewById(R.id.botonEliminarRutina);
        botonFiltraPectorales = root.findViewById(R.id.boton_pectoral);
        botonFiltraEspalda = root.findViewById(R.id.boton_espalda);
        botonFiltraPiernas = root.findViewById(R.id.boton_piernas);
        botonFiltraBiceps = root.findViewById(R.id.boton_biceps);
        botonFiltraTriceps = root.findViewById(R.id.boton_triceps);
        botonFiltraAbdomen = root.findViewById(R.id.boton_abdomen);
        botonFiltraHombros = root.findViewById(R.id.boton_hombros);
        botonFiltraPantorrillas = root.findViewById(R.id.boton_pantorrillas);
        botonFiltraTodos = root.findViewById(R.id.boton_todos);
        searchViewEjercicios = root.findViewById(R.id.search_view_ejercicios);
        textViewTituloRutina = root.findViewById(R.id.text_view_titulo_rutina);

        datosEjercicios = new DatosEjercicios(getContext(),listViewEjerciciosRutina, progressBarEjerciciosRutina);
        datosEjercicios.empezar();



        // Codigo para el boton de pulsar atras
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK) // && event.getAction() == KeyEvent.ACTION_UP
                {
                    if (linearPrincipal.getVisibility() == View.GONE) {
                        reiniciarLinear();
                        linearPrincipal.setVisibility(View.VISIBLE);

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





        botonNuevaRutina.setOnClickListener(new View.OnClickListener() {

            ArrayList<Ejercicio> ejerciciosOriginales = new ArrayList<Ejercicio>();
            ArrayList<Ejercicio> ejerciciosActualizados = new ArrayList<Ejercicio>();
            Rutina rutina = new Rutina("", new ArrayList<Ejercicio>());

            @Override
            public void onClick(View v) {
                editTextNombreRutinaActualizada.setText("");
                textViewTituloRutina.setText("Nueva rutina");
                arrayAdapterEjerciciosActualizados = new ListaEjercicios(getContext(), new ArrayList<>());
                listViewEjerciciosActualizados.setAdapter(arrayAdapterEjerciciosActualizados);

                botonActualizarRutina.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombreRutina = String.valueOf(editTextNombreRutinaActualizada.getText());
                        rutina.setNombreRutina(nombreRutina);

                        insertarRutina(rutina);

                        actualizarLista();
                        reiniciarLinear();
                        linearPrincipal.setVisibility(View.VISIBLE);
                    }
                });

                botonCancelarActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Si cancela no hace nada
                        actualizarEjerciciosRutina(rutina, ejerciciosOriginales);
                        rutina.setEjercicios(ejerciciosOriginales);

                        reiniciarLinear();
                        linearPrincipal.setVisibility(View.VISIBLE);
                    }
                });

                botonEjerciciosActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Se cargan los ejercicios para que el usuario pueda elegirlos y se muestra el linear correspondiente



                        pulsaEjercicio(datosEjercicios.getEjercicios());

                        searchViewEjercicios.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                datosEjercicios.empezarConBusqueda(query);
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());

                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                return false;
                            }


                        });

                        botonFiltraTodos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezar();
                                pulsaEjercicio(datosEjercicios.getEjercicios());
                            }
                        });

                        botonFiltraPectorales.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Pectorales");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });

                        botonFiltraAbdomen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Abdomen");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });

                        botonFiltraEspalda.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Espalda");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });

                        botonFiltraBiceps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Bíceps");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });

                        botonFiltraTriceps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Tríceps");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });

                        botonFiltraPiernas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Piernas");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });

                        botonFiltraHombros.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Hombros");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });


                        botonFiltraPantorrillas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datosEjercicios.empezarConFiltro("Pantorrillas");
                                pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                            }
                        });

                        reiniciarLinear();
                        linearEjercicios.setVisibility(View.VISIBLE);
                    }

                    private void pulsaEjercicio (ArrayList<Ejercicio> listaEjercicios) {
                        // Al pulsar un ejercicio, se inserta en la rutina que estaba
                        // y se vuelve al linear de actualizar rutina con el listview de ejercicios actualizado
                        listViewEjerciciosRutina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ArrayList<Ejercicio> ejercicios = listaEjercicios;
                                Ejercicio ejercicioPulsado = ejercicios.get(position);


                                ejerciciosActualizados.add(ejercicioPulsado);

                                actualizarEjerciciosRutina(rutina, ejerciciosActualizados);
                                rutina.setEjercicios(ejerciciosActualizados);

                                arrayAdapterEjerciciosActualizados = new ListaEjercicios(getContext(), ejerciciosActualizados);
                                listViewEjerciciosActualizados.setAdapter(arrayAdapterEjerciciosActualizados);



                                reiniciarLinear();
                                linearActualizarRutina.setVisibility(View.VISIBLE);



                            }
                        });
                    }
                });



                reiniciarLinear();
                linearActualizarRutina.setVisibility(View.VISIBLE);
                linearBotonEliminar.setVisibility(View.GONE);



            }
        });








        return root;
    }

    private void insertarRutina(Rutina rutina) {
        new InsertarRutina().execute(rutina);
    }

    private void eliminarRutina(Rutina rutina) {
        new EliminarRutina().execute(rutina);
    }

    private void actualizarEjerciciosRutina(Rutina rutina, ArrayList<Ejercicio> ejerciciosActualizados) {
        new ActualizarEjerciciosRutina(ejerciciosActualizados).execute(rutina);
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
                    .build();
            RutinaDao rd = db.rutinaDao();

            rd.insertarRutina(rutina[0]);

            return rd.obtenerTodas().toString();
        }


        protected void onPostExecute(String peticionBaseDatos) {
            Log.d("Se han insertado datos:", peticionBaseDatos);
        }
    }

    private class EliminarRutina extends AsyncTask<Rutina, Void, Rutina> {

        protected Rutina doInBackground(Rutina... rutina) {
            // Código válido para almacenar las rutinas en una base de datos creadas con Room
            AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "basedatos-rutinas")
                    .fallbackToDestructiveMigration()
                    .build();;
            RutinaDao rd = db.rutinaDao();

            rd.eliminarRutina(rutina[0]);

            return rutina[0];
        }


        protected void onPostExecute(Rutina rutinaEliminada) {
            Log.d("Se ha eliminado dato:", rutinaEliminada.toString());
        }
    }

    private class ActualizarEjerciciosRutina extends AsyncTask<Rutina, Void, String> {
        private ArrayList<Ejercicio> ejerciciosActualizados;

        public ActualizarEjerciciosRutina(ArrayList<Ejercicio> ejerciciosActualizados) {
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

            // ListView donde se muestran las rutinas creadas
            arrayAdapterRutina = new ListaRutinas(getContext(), listaRutinasArrayList);
            listViewRutinas.setAdapter(arrayAdapterRutina);
            // Al pulsar una rutina, aparece el linear para actualizarla:
            listViewRutinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Rutina rutinaPulsada = listaRutinas.get(position);
                    ArrayList<Ejercicio> ejerciciosOriginales = rutinaPulsada.getEjercicios();
                    ArrayList<Ejercicio> ejerciciosActualizados = new ArrayList<>(ejerciciosOriginales);

                    editTextNombreRutinaActualizada.setText(rutinaPulsada.getNombreRutina());
                    // ListView de los ejercicios de cada rutina
                    arrayAdapterEjerciciosActualizados = new ListaEjercicios(getContext(), rutinaPulsada.getEjercicios());
                    listViewEjerciciosActualizados.setAdapter(arrayAdapterEjerciciosActualizados);
                    // Al pulsar un ejercicio, se borra
                    listViewEjerciciosActualizados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ejerciciosActualizados.remove(position);
                            actualizarEjerciciosRutina(rutinaPulsada, ejerciciosActualizados);
                            arrayAdapterEjerciciosActualizados = new ListaEjercicios(getContext(), ejerciciosActualizados);
                            listViewEjerciciosActualizados.setAdapter(arrayAdapterEjerciciosActualizados);

                        }
                    });

                    reiniciarLinear();
                    linearActualizarRutina.setVisibility(View.VISIBLE);
                    linearBotonEliminar.setVisibility(View.VISIBLE);

                    botonEliminarRutina.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eliminarRutina(rutinaPulsada);

                            actualizarLista();
                            reiniciarLinear();
                            linearPrincipal.setVisibility(View.VISIBLE);
                        }
                    });

                    botonActualizarRutina.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            actualizarRutina(rutinaPulsada, editTextNombreRutinaActualizada.getText().toString());

                            actualizarLista();
                            reiniciarLinear();
                            linearPrincipal.setVisibility(View.VISIBLE);
                        }
                    });

                    botonCancelarActualizar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Si cancela, modificamos la rutina y ponemos los ejercicios originales
                            actualizarEjerciciosRutina(rutinaPulsada, ejerciciosOriginales);
                            reiniciarLinear();
                            linearPrincipal.setVisibility(View.VISIBLE);
                        }
                    });

                    botonEjerciciosActualizar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Se cargan los ejercicios para que el usuario pueda elegirlos y se muestra el linear correspondiente


                            searchViewEjercicios.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    datosEjercicios.empezarConBusqueda(query);
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());

                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    return false;
                                }
                            });

                            botonFiltraTodos.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezar();
                                    pulsaEjercicio(datosEjercicios.getEjercicios());
                                }
                            });

                            botonFiltraPectorales.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Pectorales");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });

                            botonFiltraAbdomen.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Abdomen");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });

                            botonFiltraEspalda.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Espalda");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });

                            botonFiltraBiceps.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Bíceps");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });

                            botonFiltraTriceps.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Tríceps");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });

                            botonFiltraPiernas.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Piernas");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });

                            botonFiltraHombros.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Hombros");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });


                            botonFiltraPantorrillas.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    datosEjercicios.empezarConFiltro("Pantorrillas");
                                    pulsaEjercicio(datosEjercicios.getEjerciciosFiltrados());
                                }
                            });



                            pulsaEjercicio(datosEjercicios.getEjercicios());

                            reiniciarLinear();
                            linearEjercicios.setVisibility(View.VISIBLE);

                        }

                        private void pulsaEjercicio (ArrayList<Ejercicio> listaEjercicios) {
                            // Al pulsar un ejercicio, se inserta en la rutina que estaba
                            // y se vuelve al linear de actualizar rutina con el listview de ejercicios actualizado
                            listViewEjerciciosRutina.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Ejercicio ejercicioPulsado = listaEjercicios.get(position);


                                    ejerciciosActualizados.add(ejercicioPulsado);

                                    actualizarEjerciciosRutina(rutinaPulsada, ejerciciosActualizados);


                                    arrayAdapterEjerciciosActualizados = new ListaEjercicios(getContext(), ejerciciosActualizados);
                                    listViewEjerciciosActualizados.setAdapter(arrayAdapterEjerciciosActualizados);



                                    reiniciarLinear();
                                    linearActualizarRutina.setVisibility(View.VISIBLE);



                                }
                            });
                        }
                    });




                }
            });


        }
    }



    private void reiniciarLinear() {
        linearPrincipal.setVisibility(View.GONE);
        linearEjercicios.setVisibility(View.GONE);
        linearActualizarRutina.setVisibility(View.GONE);
    }


}