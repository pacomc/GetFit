package com.project.getfit.ui.recetas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

    private String query;
    private String from;
    private String to;
    private String calories;

    private final String API_KEY = "cc758b2be822d3e8f2eea92b195e957e";
    private final String API_ID = "b054b49b";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recetas, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        listViewRecetas = root.findViewById(R.id.list_recetas);

        //Buscar receta:

        SearchView buscarReceta = root.findViewById(R.id.buscar_recetas);
        query = buscarReceta.getQuery().toString();
        Log.e(query, "ESTA ES LA QUERY");
        Log.e("ESTA ES LA QUERY", query);

        //FIN
        new RecetasRequest().execute("https://test-es.edamam.com/search?q=" + query);


        // Funcion OnClick para meternos dentro del layout de las recetas
        /*
        listViewRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Receta.class);
                intent.putExtra("objetoData", arrayAdapterRecetas.getPosition(posicion));
                view.startActionMode(intent);
            }
        });
        */

        //FIN

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
                    String calorias = jsonrecipe.getString("calories");
                    Float kcalFloat = Float.valueOf(calorias) / 1000;
                    Integer kcalorias = Math.round(kcalFloat);

                    JSONArray listaIngredientesJSON = (JSONArray) jsonrecipe.get("ingredientLines");

                    List<String> listaIngredientes = new ArrayList<>();
                    for (int j = 0; j < listaIngredientesJSON.length(); j++) {
                        listaIngredientes.add(listaIngredientesJSON.getString(j));
                    }

                    recetas.add(new Receta(titulo, linkImagen, kcalorias.toString(), listaIngredientes));

                }

                arrayAdapterRecetas = new ListaRecetas(getContext(), recetas);
                listViewRecetas.setAdapter(arrayAdapterRecetas);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}