package com.project.getfit.ui.recetas;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.project.getfit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RecetasFragment extends Fragment {

    private ListView listViewRecetas;
    private ArrayAdapter arrayAdapterRecetas;
    private ArrayAdapter arrayAdapterIngredientes;

    private final String API_KEY = "cc758b2be822d3e8f2eea92b195e957e";
    private final String API_ID = "b054b49b";

    private LinearLayout linearListaRecetas;
    private LinearLayout linearReceta;
    private LinearLayout linearListViewRecetas;
    private LinearLayout linearScrollViewsRecetas;
    private TextView textNombreReceta;
    private TextView textKcalorias;
    private TextView textRaciones;
    private ImageView imagenReceta;
    private ListView listViewIngredientes;

    private Button boton_100_200;
    private Button boton_200_300;
    private Button boton_300_500;
    private Button botonDesayuno;
    private Button botonAlmuerzo;
    private Button botonCena;
    private Button botonTentempie;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recetas, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        listViewRecetas = root.findViewById(R.id.list_recetas);
        linearListaRecetas = root.findViewById(R.id.linear_lista_recetas);
        linearListViewRecetas = root.findViewById(R.id.linear_list_view_recetas);
        linearScrollViewsRecetas = root.findViewById(R.id.linear_scrollviews_recetas);
        linearReceta = root.findViewById(R.id.linear_receta);
        textNombreReceta = root.findViewById(R.id.text_nombre_receta);
        textKcalorias = root.findViewById(R.id.text_kcalorias_recetas);
        textRaciones = root.findViewById(R.id.text_raciones);
        imagenReceta = root.findViewById(R.id.imagen_receta_individual);
        listViewIngredientes = root.findViewById(R.id.lista_ingredientes);
        boton_100_200 = root.findViewById(R.id.boton_100_200);
        boton_200_300 = root.findViewById(R.id.boton_200_300);
        boton_300_500 = root.findViewById(R.id.boton_300_500);
        botonDesayuno = root.findViewById(R.id.boton_desayuno);
        botonAlmuerzo = root.findViewById(R.id.boton_almuerzo);
        botonCena = root.findViewById(R.id.boton_cena);
        botonTentempie = root.findViewById(R.id.boton_tentempie);



        //Buscar receta:

        SearchView buscarReceta = root.findViewById(R.id.buscar_recetas);
        buscarReceta.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // lo que hace cuando se pulsa intro

                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);


                String url = "https://test-es.edamam.com/search?q=" + query;

                new RecetasRequest().execute(url);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
            // lo que se hace cada vez que el texto cambia
                return false;
            }
        });



        boton_100_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://test-es.edamam.com/search?q=&calories=100-200";

                new RecetasRequest().execute(url);
                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);
            }
        });

        boton_200_300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://test-es.edamam.com/search?q=&calories=200-300";

                new RecetasRequest().execute(url);
                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);
            }
        });

        boton_300_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://test-es.edamam.com/search?q=&calories=300-500";

                new RecetasRequest().execute(url);
                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);
            }
        });

        botonDesayuno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://test-es.edamam.com/search?q=&mealType=breakfast";

                new RecetasRequest().execute(url);
                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);
            }
        });

        botonAlmuerzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://test-es.edamam.com/search?q=&mealType=lunch";

                new RecetasRequest().execute(url);
                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);
            }
        });

        botonCena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://test-es.edamam.com/search?q=&mealType=dinner";

                new RecetasRequest().execute(url);
                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);
            }
        });

        botonTentempie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://test-es.edamam.com/search?q=&mealType=snack";

                new RecetasRequest().execute(url);
                linearScrollViewsRecetas.setVisibility(View.GONE);
                linearListViewRecetas.setVisibility(View.VISIBLE);
            }
        });





        return root;
    }

    class RecetasRequest extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... targetURL) {

            HttpURLConnection connection = null;
            try {
                //Create connection
                URL url = new URL(targetURL[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                connection.setRequestProperty("app_id",
                        API_ID);
                connection.setRequestProperty("app_key",
                        API_KEY);

                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream());
                wr.close();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;

                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }

                rd.close();
                return response.toString();
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        protected void onPostExecute(String feed) {

            try {
                // get JSONObject from JSON file
                JSONObject obj = new JSONObject(feed);
                JSONArray hitsElements = (JSONArray) obj.get("hits");

                ArrayList<Receta> recetas = new ArrayList<>();

                for (int i = 0; i < hitsElements.length(); i++) {
                    JSONObject jsoni = hitsElements.getJSONObject(i);
                    JSONObject jsonrecipe = (JSONObject) jsoni.get("recipe");
                    String titulo = jsonrecipe.getString("label");
                    String linkImagen = jsonrecipe.getString("image");
                    String kcalString = jsonrecipe.getString("calories");
                    Float kcalFloat = Float.valueOf(kcalString);
                    Integer kcaloriasTotal = Math.round(kcalFloat);
                    String numPorcionesString = jsonrecipe.getString("yield");
                    Integer numPorciones = Math.round(Float.valueOf(numPorcionesString));
                    Integer kcalPorcion = kcaloriasTotal / numPorciones;

                    JSONArray listaIngredientesJSON = (JSONArray) jsonrecipe.get("ingredientLines");

                    ArrayList<String> listaIngredientes = new ArrayList<>();
                    for (int j = 0; j < listaIngredientesJSON.length(); j++) {
                        listaIngredientes.add(listaIngredientesJSON.getString(j));
                    }

                    recetas.add(new Receta(titulo, linkImagen, kcalPorcion.toString(), numPorciones.toString(), listaIngredientes));

                }

                arrayAdapterRecetas = new ListaRecetas(getContext(), recetas);
                listViewRecetas.setAdapter(arrayAdapterRecetas);

                listViewRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        linearListaRecetas.setVisibility(View.GONE);
                        linearReceta.setVisibility(View.VISIBLE);
                        Receta recetaPulsada = recetas.get(position);

                        textNombreReceta.setText(recetaPulsada.getTitulo());
                        textKcalorias.setText(recetaPulsada.getKcalorias());
                        textRaciones.setText(recetaPulsada.getNumPorciones());

                        arrayAdapterIngredientes = new ListaIngredientes(getContext(), recetaPulsada.getIngredientes());
                        listViewIngredientes.setAdapter(arrayAdapterIngredientes);


                        Glide.with(getContext())
                                .load(recetaPulsada.getLinkImagen())
                                .placeholder(R.drawable.diet_error)
                                .error(R.drawable.diet)
                                .into(imagenReceta);



                    }
                });

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }





}