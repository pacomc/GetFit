package com.project.getfit.ui.calendario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.getfit.R;

import java.util.ArrayList;

public class ListaCalendario extends ArrayAdapter<String> {

    public ListaCalendario(Context c, ArrayList<String> records) {

        super(c, 0, records);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String item= getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_calendario, null);
        }
        TextView textoTitulo = convertView.findViewById(R.id.textoTituloCalendario);
        TextView textoDescripcion = convertView.findViewById(R.id.textoDescripcionCalendario);
        TextView textoHorario = convertView.findViewById(R.id.textoHoraCalendario);

        Integer indice = 0;
        ArrayList<String> listaValores = new ArrayList<>();
        //Vamos cogiendo los diferentes campos dentro del string
        for(int i = 0; i < item.length(); i++) {
            if(item.charAt(i) == '&') {
                listaValores.add(item.substring(indice, i));
                indice = i + 1;
            }
        }
        String fecha = listaValores.get(0);
        String titulo = listaValores.get(1);
        String descripcion = listaValores.get(2);
        String hora = listaValores.get(3);

        textoTitulo.setText(titulo);
        textoDescripcion.setText(descripcion);
        textoHorario.setText(hora);

        return convertView;
    }


}
