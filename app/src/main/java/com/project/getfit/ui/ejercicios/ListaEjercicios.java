package com.project.getfit.ui.ejercicios;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.project.getfit.R;

import java.util.ArrayList;

import java.util.logging.LogRecord;

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


        Handler handler = new Handler();

        Glide.with(getContext())
                .load(item.getLinkImagen().replaceAll("370x150", "740x416")) // Para obtener mejores fotos se sustituye el enlace (porque existe)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        handler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             Glide.with(getContext())
                                                     .load(item.getLinkImagen()) // Para obtener mejores fotos se sustituye el enlace (porque existe)
                                                     .placeholder(R.drawable.ejercicio)
                                                     .error(R.drawable.ejercicio_error)
                                                     .into(cajaImagen);
                                         }
                                     });

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //on load success
                        return false;
                    }
                })
                .placeholder(R.drawable.ejercicio)
                .error(R.drawable.ejercicio_error)
                .into(cajaImagen);


        return convertView;
    }




}
