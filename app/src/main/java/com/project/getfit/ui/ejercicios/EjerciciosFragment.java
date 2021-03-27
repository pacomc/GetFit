package com.project.getfit.ui.ejercicios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;
import com.project.getfit.ui.recetas.ListaRecetas;
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
    private ArrayList<Ejercicio> ejercicios;
    private String parteCuerpoActual;
    private ArrayAdapter arrayAdapterEjercicios;
    private ListView listViewEjercicios;
    private Iterator<HashMap<String, String>> iteradorEnlaces;
    private ProgressBar progressBarEjercicios;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ejercicios, container, false);

        listViewEjercicios = root.findViewById(R.id.list_ejercicios);
        progressBarEjercicios = root.findViewById(R.id.progressBarEjercicios);

        new ExtraeEjerciciosRequest().execute("https://eresfitness.com/ejercicios/");



        return root;
    }




    private ArrayList<HashMap<String, String>> extraerInfoEjercicios (String paginaHTML) {
        ArrayList<HashMap<String, String>> infoEjercicios = new ArrayList<>();

        String patronEnlaces = "src=\\\"([^\\\"]*)\\\"[^>]*>[\\s|\\n]*</a>[\\s|\\n]*<div[\\s|\\n]*[^>]*>[\\s|\\n]*</div>[\\s|\\n]*</div>[\\s|\\n]*<div[\\s|\\n]*[^>]*>[\\s|\\n]*<div[\\s|\\n]*[^>]*>[\\s|\\n]*<h4[\\s|\\n]*class=\\\"entry-title[\\s|\\n]*h3\\\">[\\s|\\n]*<a[\\s|\\n]*href=\\\"([^\\\"]*)\\\"[\\s|\\n]*>[\\s|\\n]*([^<]*)";
        Pattern r = Pattern.compile(patronEnlaces);

        Matcher m = r.matcher(paginaHTML);
        while (m.find()) {
            HashMap<String, String> infoEjercicio = new HashMap<>();
            String nombre = m.group(3);
            String linkEjercicio = m.group(2);
            String linkImagen = m.group(1);
            infoEjercicio.put("nombre", nombre);
            infoEjercicio.put("linkEjercicio", linkEjercicio);
            infoEjercicio.put("linkImagen", linkImagen);
            infoEjercicios.add(infoEjercicio);
        }


        return infoEjercicios;
    }





    class ExtraeEjerciciosRequest extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... targetURL) {
            String content = null;
            URLConnection connection = null;
            try {
                connection =  new URL(targetURL[0]).openConnection();
                Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8");
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
            }catch ( Exception ex ) {
                ex.printStackTrace();
            }

            return content;
        }


        protected void onPostExecute(String paginaHTML) {
           ejercicios = new ArrayList<>();
            ArrayList<HashMap<String, String>> listaIteradoraEnlaces = new ArrayList<>();

            Pattern r = Pattern.compile("<div[\\s]*class=\\\"consebox\\\"[\\s]*[^>]*>[\\s|\\n]*<a[\\s|\\n]*href=\\\"([^\\\"]*)\\\"[\\s|\\n]*>[\\s|\\n]*</p>[\\s|\\n]*<h3>([^<]*)");

            Matcher m = r.matcher(paginaHTML);
            while (m.find()) {

                HashMap<String, String> nombreEnlaces = new HashMap<>();
                String nombre = m.group(2);
                String enlace = m.group(1);
                nombreEnlaces.put(nombre, enlace);
                listaIteradoraEnlaces.add(nombreEnlaces);

            }
            iteradorEnlaces = listaIteradoraEnlaces.iterator();

            Map.Entry<String,String> entry = iteradorEnlaces.next().entrySet().iterator().next();
            parteCuerpoActual = entry.getKey();
            new ExtraeInfoEjerciciosRequest().execute(entry.getValue());

        }
    }


    class ExtraeInfoEjerciciosRequest extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... targetURL) {
            HttpURLConnection connection = null;
            try {
                //Create connection
                URL url = new URL(targetURL[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

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


        protected void onPostExecute(String paginaHtmlEjercicio) {
            ArrayList<HashMap<String, String>> infoEjercicios = extraerInfoEjercicios(paginaHtmlEjercicio);

            for (HashMap<String, String> infoEjercicio : infoEjercicios) {
                Ejercicio ejercicio = new Ejercicio(infoEjercicio.get("nombre"), infoEjercicio.get("linkEjercicio"), infoEjercicio.get("linkImagen"), parteCuerpoActual);
                ejercicios.add(ejercicio);
            }

            if (iteradorEnlaces.hasNext()) {
                Map.Entry<String,String> entry = iteradorEnlaces.next().entrySet().iterator().next();
                parteCuerpoActual = entry.getKey();
                new ExtraeInfoEjerciciosRequest().execute(entry.getValue());
            } else {
                arrayAdapterEjercicios = new ListaEjercicios(getContext(), ejercicios);
                listViewEjercicios.setAdapter(arrayAdapterEjercicios);
                progressBarEjercicios.setVisibility(View.GONE);
                listViewEjercicios.setVisibility(View.VISIBLE);
            }

        }
    }

}