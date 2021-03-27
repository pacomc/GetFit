package com.project.getfit.ui.ejercicios;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.project.getfit.ui.ejercicios.Ejercicio;
import com.project.getfit.ui.ejercicios.ListaEjercicios;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatosEjercicios {
    private Iterator<HashMap<String, String>> iteradorEnlaces;
    private static ArrayList<Ejercicio> ejercicios; // static para que guarde la info y no la borre al salir
    private String parteCuerpoActual;
    private Context contextoActual;
    private ArrayAdapter arrayAdapterEjercicios;
    private ListView listViewEjercicios;
    private ProgressBar progressBarEjercicios;

    public DatosEjercicios(Context contextoActual, ListView listViewEjercicios, ProgressBar progressBarEjercicios) {
        this.contextoActual = contextoActual;
        this.listViewEjercicios = listViewEjercicios;
        this.progressBarEjercicios = progressBarEjercicios;

    }

    public void empezar() {
        if (ejercicios != null) {
            arrayAdapterEjercicios = new ListaEjercicios(contextoActual, ejercicios);
            listViewEjercicios.setAdapter(arrayAdapterEjercicios);
            progressBarEjercicios.setVisibility(View.GONE);
            listViewEjercicios.setVisibility(View.VISIBLE);

        } else {
            new ExtraeEjerciciosRequest().execute("https://eresfitness.com/ejercicios/");
        }

    }

    private ArrayList<HashMap<String, String>> extraerInfoEjercicios(String paginaHTML) {
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




    private class ExtraeEjerciciosRequest extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... targetURL) {
            String content = null;
            URLConnection connection = null;
            try {
                connection = new URL(targetURL[0]).openConnection();
                Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8");
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
            } catch (Exception ex) {
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

            Map.Entry<String, String> entry = iteradorEnlaces.next().entrySet().iterator().next();
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
                // Este try es necesario ya que puede cambiar de fragment mientras
                // la peticion termina y el getConext no devolveria el context apropiado
                // (y cerraria la app)
                try {
                    arrayAdapterEjercicios = new ListaEjercicios(contextoActual, ejercicios);
                    listViewEjercicios.setAdapter(arrayAdapterEjercicios);
                    progressBarEjercicios.setVisibility(View.GONE);
                    listViewEjercicios.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.e("Warning", "Se ha cambiado de fragment mientras se estaba cargando los ejercicios");
                }

            }

        }
    }
}
