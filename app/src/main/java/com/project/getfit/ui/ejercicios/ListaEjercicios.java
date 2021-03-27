package com.project.getfit.ui.ejercicios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.project.getfit.R;

import java.util.ArrayList;

public class ListaEjercicios extends ArrayAdapter<Ejercicio> {

    public ListaEjercicios(Context c, ArrayList<Ejercicio> records) {
        super(c, 0, records);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Ejercicio item= getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_ejercicios, null);
        }
        TextView cajaTitulo = convertView.findViewById(R.id.textoTituloEjercicio);
        TextView cajaSubtitulo = convertView.findViewById(R.id.textoSubtituloEjercicio);
        ImageView cajaImagen = convertView.findViewById(R.id.imageViewEjercicio);

        cajaTitulo.setText(item.getNombre());
        cajaSubtitulo.setText(item.getParteCuerpo());

        Glide.with(getContext())
                .load(item.getLinkImagen())
                .placeholder(R.drawable.ejercicio)
                .error(R.drawable.ejercicio_error)
                .into(cajaImagen);


        return convertView;
    }




}
