package com.project.getfit.ui.rutina;

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

public class ListaRutinas extends ArrayAdapter<Rutina> {

    public ListaRutinas(Context c, ArrayList<Rutina> records) {
        super(c, 0, records);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Rutina item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_rutinas, null);
        }
        TextView cajaTitulo = convertView.findViewById(R.id.textoTituloRutina);

        cajaTitulo.setText(item.getNombreRutina());


        return convertView;
    }

}
