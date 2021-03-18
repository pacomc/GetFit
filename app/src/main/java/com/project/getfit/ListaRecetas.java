package com.project.getfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.getfit.ui.recetas.Receta;

import java.util.ArrayList;

public class ListaRecetas extends ArrayAdapter<Receta> {

    public ListaRecetas(Context c, ArrayList<Receta> records) {

        super(c, 0, records);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Receta item= getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_recetas, null);
        }
        TextView cajaTitulo = convertView.findViewById(R.id.textoTituloReceta);
        TextView cajaSubtitulo = convertView.findViewById(R.id.textoSubtituloReceta);

        cajaTitulo.setText(item.getTitulo());
        cajaSubtitulo.setText(item.getKcalorias());

        return convertView;
    }
}
