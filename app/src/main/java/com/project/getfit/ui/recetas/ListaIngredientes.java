package com.project.getfit.ui.recetas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.getfit.R;

import java.util.ArrayList;

public class ListaIngredientes extends ArrayAdapter<String> {

    public ListaIngredientes(Context c, ArrayList<String> records) {

        super(c, 0, records);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String item= getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_ingredientes, null);
        }
        TextView cajaIngredientes = convertView.findViewById(R.id.textoIngredientes);

        cajaIngredientes.setText(item);

        return convertView;
    }


}
