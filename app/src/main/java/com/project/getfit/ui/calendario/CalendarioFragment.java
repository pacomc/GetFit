package com.project.getfit.ui.calendario;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;
import com.project.getfit.ui.recetas.ListaCalendario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CalendarioFragment extends Fragment {

    private CalendarioViewModel calendarioViewModel;
    private TextView miFecha;
    private Button botonAñadirEvento;
    private Button botonGuardarEvento;
    private Button botonCancelarEvento;
    private Button botonHora;
    private LinearLayout linearMostrarEvento;
    private LinearLayout linearAñadirEvento;
    private TextView tituloEvento;
    private TextView descripcionEvento;
    private TextView horaEvento;
    private String diaActual;
    private String mesActual;
    private String añoActual;
    private ListView listViewCalendario;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        calendarioViewModel = new ViewModelProvider(this).get(CalendarioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendario, container, false);

        final CalendarView calendarView = root.findViewById(R.id.calendarView);
        miFecha = root.findViewById(R.id.texto_fecha);
        tituloEvento = root.findViewById(R.id.texto_titulo_evento_calendario);
        descripcionEvento = root.findViewById(R.id.texto_descripcion_evento_calendario);
        horaEvento = root.findViewById(R.id.texto_hora_evento_calendario);

        Calendar fechaActual= Calendar.getInstance();
        Integer diaInt = fechaActual.get(Calendar.DAY_OF_MONTH);
        Integer mesInt = fechaActual.get(Calendar.MONTH);
        Integer año = fechaActual.get(Calendar.YEAR);
        String dia = "" + diaInt; String mes = "" + mesInt;
        if(diaInt < 10) {dia = "0" + diaInt;}
        if(mesInt < 10) {mes = "0" + mesInt;}
        String fecha = dia + "/" + mes + "/" + año;
        diaActual = dia;mesActual = mes;añoActual = año.toString();
        miFecha.setText(fecha);

        linearMostrarEvento = root.findViewById(R.id.linearMostrarEventos);
        linearAñadirEvento = root.findViewById(R.id.linearAñadirEventos);
        botonAñadirEvento = root.findViewById(R.id.boton_añadir_evento);
        botonGuardarEvento = root.findViewById(R.id.boton_guardar_evento);
        botonCancelarEvento = root.findViewById(R.id.boton_cancelar_evento);
        botonHora = root.findViewById(R.id.botonSeleccionarHora);

        linearMostrarEvento.setVisibility(View.VISIBLE);
        linearAñadirEvento.setVisibility(View.GONE);
        mostrarEvento(root);


        botonAñadirEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    linearMostrarEvento.setVisibility(View.GONE);
                    linearAñadirEvento.setVisibility(View.VISIBLE);
            }
        });

        botonGuardarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearMostrarEvento.setVisibility(View.VISIBLE);
                linearAñadirEvento.setVisibility(View.GONE);
                guardarEvento(root);
                mostrarEvento(root);
            }
        });

        botonCancelarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearMostrarEvento.setVisibility(View.VISIBLE);
                linearAñadirEvento.setVisibility(View.GONE);
                mostrarEvento(root);
            }
        });

        botonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog inputHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hora = "" + hourOfDay;
                        String minuto = "" + minute;
                        if(hourOfDay < 10) {hora = "0" + hourOfDay;}
                        if(minute < 10) {minuto = "0" + minute;}
                        botonHora.setText(hora + ":" + minuto);
                    }
                }, 12, 00, true);

                inputHora.show();

            }
        });



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String mes = "" + month;
                String dia = "" + dayOfMonth;
                if(month < 10){mes = "0" + month; }
                if(dayOfMonth < 10){dia = "0" + dayOfMonth;}
                String fecha = dia + "/" + mes + "/" + year;
                diaActual = dia;mesActual = mes;añoActual = año.toString();
                miFecha.setText(fecha);

            }
        });

        animacionArriba(calendarView);
        animacionIzquierda(miFecha);
        animacionIzquierda(tituloEvento);
        animacionIzquierda(descripcionEvento);
        animacionIzquierda(horaEvento);
        animacionIzquierda(linearMostrarEvento);
        animacionIzquierda(linearAñadirEvento);
        animacionDerecha(botonHora);
        animacionAbajo(botonAñadirEvento);


    return root;
    }
    // Creación efectos visuales pagina de inicio:
    private void animacionArriba(View view){
        Animation efectoAnimacionArriba = AnimationUtils.loadAnimation(getContext(), R.anim.animacion_desde_arriba);
        view.startAnimation(efectoAnimacionArriba);

    }
    private void animacionAbajo(View view){
        Animation efectoAnimacionAbajo = AnimationUtils.loadAnimation(getContext(), R.anim.animacion_desde_abajo);
        view.startAnimation(efectoAnimacionAbajo);

    }
    private void animacionIzquierda(View view){
        Animation efectoAnimacionIzquierda = AnimationUtils.loadAnimation(getContext(), R.anim.animacion_desde_izquierda);
        view.startAnimation(efectoAnimacionIzquierda);

    }
    private void animacionDerecha(View view){
        Animation efectoAnimacionDerecha = AnimationUtils.loadAnimation(getContext(), R.anim.animacion_desde_derecha);
        view.startAnimation(efectoAnimacionDerecha);

    }

    public void guardarEvento(View root) {
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        Set<String> listaEventos = new HashSet<>();
        listaEventos = datos.getStringSet("listaEventos", new HashSet<>());
        SharedPreferences.Editor editar = datos.edit();
        EditText titulo = root.findViewById(R.id.editTituloCalendar);
        EditText descripcion = root.findViewById(R.id.editDescripcionCalendario);
        Button hora = root.findViewById(R.id.botonSeleccionarHora);
        //Lo almacenamos en un unico string que luego parsearemos (es cutre Paco, lo se :( )
        String evento = diaActual + "/" + mesActual + "/" + añoActual + "&" + titulo.getText().toString() + "&"
                + descripcion.getText().toString() + "&" + hora.getText().toString() + "&";
        listaEventos.add(evento);
        editar.putStringSet("listaEventos", listaEventos);
        editar.commit();
    }

    public void mostrarEvento(View root) {
        listViewCalendario = root.findViewById(R.id.lista_eventos);
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        Set<String> listaEventos = new HashSet<>();
        listaEventos = datos.getStringSet("listaEventos", new HashSet<>());
        //Hay que filtrar para mostrar solo los eventos de ese dia
        ArrayList listaEventosArray = filtrarEventosPorFecha(listaEventos);
        ArrayAdapter listaEventosAdapter = new ListaCalendario(getContext(), listaEventosArray);
        listViewCalendario.setAdapter(listaEventosAdapter);

    }

    public ArrayList<String> filtrarEventosPorFecha(Set<String> listaEventosSet) {
        ArrayList<String> listaEventos = new ArrayList<>(listaEventosSet);
        String fecha = diaActual + "/" + mesActual + "/" + añoActual;
        for (int i = 0; i < listaEventos.size(); i++) {
            if(!fecha.equals(listaEventos.get(i).substring(0, 10))) {//Si no es la fecha seleccionamos la borramos
                listaEventos.remove(i);
            }
        }
        return listaEventos;
    }

}