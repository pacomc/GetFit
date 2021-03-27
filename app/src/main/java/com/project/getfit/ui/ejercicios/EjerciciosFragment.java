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

        DatosEjercicios datosEjercicios = new DatosEjercicios(getContext(), listViewEjercicios, progressBarEjercicios);
        datosEjercicios.empezar();



        return root;
    }

}