package com.project.getfit.ui.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.project.getfit.R;
import com.project.getfit.ui.models.Receta;
import com.project.getfit.ui.models.RecetaAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecetasFragmentOLD extends Fragment {
    private ListView listViewRecetas;
    private ArrayList<String> titulosRecetas = new ArrayList<>();
    private ArrayAdapter arrayAdapterRecetas;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recetas, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        arrayAdapterRecetas = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, titulosRecetas);

        listViewRecetas = root.findViewById(R.id.list_recetas);

        listViewRecetas.setAdapter(arrayAdapterRecetas);

        getRecipe("pollo", "0", "3", "591-722");

        return root;
    }

    private void getRecipe(String q, String from, String to, String calories) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-es.edamam.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetaAPI servicioReceta = retrofit.create(RecetaAPI.class);

        Call<List<Receta>> call = servicioReceta.getRecipe(q);

        call.enqueue(new Callback<List<Receta>>() {

            @Override

            public void onResponse(Call<List<Receta>> call, Response<List<Receta>> response) {
                try {
                    if (response.isSuccessful()) {
                        for(Receta receta : response.body()) {

                            titulosRecetas.add(receta.getTitulo());
                            Toast.makeText(getContext(), String.valueOf(receta), Toast.LENGTH_LONG).show();

                        }

                        arrayAdapterRecetas.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override

            public void onFailure(Call<List<Receta>> call, Throwable t) {

            }

        });

    }
}