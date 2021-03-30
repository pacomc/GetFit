package com.project.getfit.ui.calendario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarioFragment extends Fragment {

    private CalendarioViewModel calendarioViewModel;
    private TextView miFecha;
    private Button botonGuardarFecha;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarioViewModel = new ViewModelProvider(this).get(CalendarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendario, container, false);

        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        miFecha = root.findViewById(R.id.texto_fecha);
        botonGuardarFecha = root.findViewById(R.id.boton_guardar_fecha);
        if(botonGuardarFecha.callOnClick()){
            Calendar cambiarColorDia = Calendar.getInstance();
            calendarView.setDate(cambiarColorDia.getTimeInMillis());

        }





        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + month + "/" + year;
                miFecha.setText(fecha);
            }
        });



        return root;
    }
}