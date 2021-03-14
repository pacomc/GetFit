package com.project.getfit.ui.medidor_tiempo;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.project.getfit.MainActivity;
import com.project.getfit.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedidorTiempoFragment extends Fragment {
    private Boolean estadoMedidorTiempo = false; // Para controlar si el cronometro está activo

    private MedidorTiempoViewModel medidorTiempoViewModel;
    private View root;

    private CountDownTimer countDownTimer;
    private Integer tiempoContador = 0;
    private Integer segundo = 0;
    private Integer minuto = 0;
    private Integer hora = 0;

    private TextView textViewReloj;
    private Button botonSuma1Descanso;
    private Button botonSuma2Descanso;
    private Button botonResta1Descanso;
    private Button botonResta2Descanso;
    private Button botonSuma1Ejercicio;
    private Button botonSuma2Ejercicio;
    private Button botonResta1Ejercicio;
    private Button botonResta2Ejercicio;
    private Button botonSumaSeries;
    private Button botonRestaSeries;
    private Button botonMedidorTiempo;
    private EditText editTextNum1Descanso;
    private EditText editTextNum2Descanso;
    private EditText editTextNum1Ejercicio;
    private EditText editTextNum2Ejercicio;
    private EditText editTextNumSeries;
    private List<EditText> editTextsMedidorTiempo = new ArrayList<>();

    private Integer seriesRestantes;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        medidorTiempoViewModel = new ViewModelProvider(this).get(MedidorTiempoViewModel.class);
        root = inflater.inflate(R.layout.fragment_medidor_tiempo, container, false);

        //  --- INICIO BOTONES DE MEDIDOR DE TIEMPO PARA SUMAR Y RESTAR VALORES ---
        // Inicializacion textView
        textViewReloj = root.findViewById(R.id.text_medidor_tiempo);
        // Inicializacion botones
        botonSuma1Descanso = root.findViewById(R.id.boton_suma1_descanso);
        botonSuma2Descanso = root.findViewById(R.id.boton_suma2_descanso);
        botonResta1Descanso = root.findViewById(R.id.boton_resta1_descanso);
        botonResta2Descanso = root.findViewById(R.id.boton_resta2_descanso);
        botonSumaSeries = root.findViewById(R.id.boton_suma_series);
        botonRestaSeries = root.findViewById(R.id.boton_resta_series);
        botonSuma1Ejercicio = root.findViewById(R.id.boton_suma1_ejercicio);
        botonSuma2Ejercicio = root.findViewById(R.id.boton_suma2_ejercicio);
        botonResta1Ejercicio = root.findViewById(R.id.boton_resta1_ejercicio);
        botonResta2Ejercicio = root.findViewById(R.id.boton_resta2_ejercicio);
        botonMedidorTiempo = root.findViewById(R.id.boton_comenzar_crono);
        // Inicializacion edittexts
        editTextNum1Descanso = root.findViewById(R.id.text_num1_descanso);
        editTextNum2Descanso = root.findViewById(R.id.text_num2_descanso);
        editTextNum1Ejercicio = root.findViewById(R.id.text_num1_ejercicio);
        editTextNum2Ejercicio = root.findViewById(R.id.text_num2_ejercicio);
        editTextNumSeries = root.findViewById(R.id.text_num_series);
        // Inicializacion lista con edittexts (para poder recorrerlos facilmente)
        editTextsMedidorTiempo.add(editTextNum1Descanso);
        editTextsMedidorTiempo.add(editTextNum2Descanso);
        editTextsMedidorTiempo.add(editTextNum1Ejercicio);
        editTextsMedidorTiempo.add(editTextNum2Ejercicio);
        editTextsMedidorTiempo.add(editTextNumSeries);


        funcionalidadBotones(); // Se inserta funcionalidad a los botones

        botonMedidorTiempo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                accionBotonMedidorTiempo();
            }

        });

        return root;
    }


    private void accionBotonMedidorTiempo() {
        Integer definirTiempo = 10000000*10000000;
        MediaPlayer sonidoTerminarSerie = MediaPlayer.create(getContext(), R.raw.nokia_sms_tone);

        if (estadoMedidorTiempo) {
            estadoMedidorTiempo = false; // Para llevar control de pause y comienzo
            botonMedidorTiempo.setText("Comenzar");

            tiempoContador = 0;
            if (countDownTimer != null) { // Porque se puede pausar y el countDownTime no estar inicializado (si las series son 0)
                countDownTimer.cancel();
            }

        } else {

            if (comprobacionValoresEditText()) { // Si pasa las comprobaciones
                estadoMedidorTiempo = true;
                botonMedidorTiempo.setText("Pausar");
                seriesRestantes = numeroSeriesEstablecidas();
                if (seriesRestantes > 0) { // Si hay series que contar
                    seriesRestantes = seriesRestantes - 1;

                    Integer segundosTotalesDescanso = segundosDescansoEstablecidos();
                    Integer segundosTotalesEjercicio = segundosEjercicioEstablecidos();

                    countDownTimer = new CountDownTimer(definirTiempo, 1000) { // antes cada 1 segundos
                        @Override
                        public void onTick(long l) {
                            tiempoContador = tiempoContador + 1;
                            textViewReloj.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                            segundo = tiempoContador % 60;
                            minuto = (tiempoContador / 60) % 60;
                            hora = tiempoContador / 3600;

                            Boolean moduloSerie = tiempoContador % (segundosTotalesEjercicio + segundosTotalesDescanso) == 0; // True si el tiempo total es modulo de una serie, es decir, si ha terminado el tiempo de una serie
                            if (moduloSerie) {
                                if (seriesRestantes == 0) { // Si se han acabado las series, es decir, ha terminado la duracion total del ejercicio
                                    countDownTimer.cancel();
                                    tiempoContador = 0;
                                    textViewReloj.setTextColor(ContextCompat.getColor(getContext(), R.color.color_reloj_terminar_serie));
                                    estadoMedidorTiempo = false;
                                    botonMedidorTiempo.setText("Comenzar");

                                } else {
                                    seriesRestantes = seriesRestantes - 1;
                                    textViewReloj.setTextColor(ContextCompat.getColor(getContext(), R.color.color_reloj_serie));
                                }
                                sonidoTerminarSerie.start();
                            }


                            String tiempoFormateado = String.format("%02d:%02d:%02d", hora, minuto, segundo);
                            textViewReloj.setText(tiempoFormateado);

                        }

                        @Override
                        public void onFinish() {
                            tiempoContador = 0;
                            Toast.makeText(getContext(), "Se ha producido un error, inicie de nuevo", Toast.LENGTH_LONG).show();
                        }
                    }.start();
                }
            }
        }
    }

    private Boolean comprobacionValoresEditText() {
        Boolean pasaComprobaciones = true;

        for (EditText editText: editTextsMedidorTiempo) { // Se comprueban todos los edit text para que no esten vacios, se ponen 0 en los vacios
            String contenidoEditText = String.valueOf(editText.getText());
            if (contenidoEditText.matches("")) {
                editText.setText("0");
            }
        }

        Integer numeroSeries = Integer.valueOf(String.valueOf(editTextNumSeries.getText()));
        Integer segundosDescanso = segundosDescansoEstablecidos();
        Integer segundosEjercicio = segundosEjercicioEstablecidos();

        if (numeroSeries.equals(0)) { // Si las series son 0
            pasaComprobaciones = false;
            Snackbar.make(root, "Debe añadir alguna serie", Snackbar.LENGTH_LONG).show();
        } else if (segundosDescanso.equals(0)) {
            pasaComprobaciones = false;
            Snackbar.make(root, "Debe añadir tiempo de descanso", Snackbar.LENGTH_LONG).show();
        } else if (segundosEjercicio.equals(0)) {
            pasaComprobaciones = false;
            Snackbar.make(root, "Debe añadir tiempo de ejercicio", Snackbar.LENGTH_LONG).show();
        }

        return pasaComprobaciones;
    }

    private Integer numeroSeriesEstablecidas() {
        return Integer.valueOf(String.valueOf(editTextNumSeries.getText()));
    }

    private Integer segundosDescansoEstablecidos() {
        return Integer.valueOf(String.valueOf(editTextNum1Descanso.getText())) * 60 + Integer.valueOf(String.valueOf(editTextNum2Descanso.getText()));
    }

    private Integer segundosEjercicioEstablecidos () {
        return Integer.valueOf(String.valueOf(editTextNum1Ejercicio.getText())) * 60 + Integer.valueOf(String.valueOf(editTextNum2Ejercicio.getText()));
    }

    private void funcionalidadBotones() {
        botonSuma1Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sumaEditText(editTextNum1Descanso);
            }
        });

        botonSuma2Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sumaEditText(editTextNum2Descanso);
            }
        });

        botonResta1Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restaEditText(editTextNum1Descanso);
            }
        });

        botonResta2Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restaEditText(editTextNum2Descanso);
            }
        });

        botonSuma1Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sumaEditText(editTextNum1Ejercicio);
            }
        });

        botonSuma2Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sumaEditText(editTextNum2Ejercicio);
            }
        });

        botonResta1Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restaEditText(editTextNum1Ejercicio);
            }
        });

        botonResta2Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restaEditText(editTextNum2Ejercicio);
            }
        });

        botonSumaSeries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sumaEditText(editTextNumSeries);
            }
        });

        botonRestaSeries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restaEditText(editTextNumSeries);
            }
        });
    }

    private void sumaEditText(@NotNull EditText editText) {
        Integer valorActual;
        String editTextContenido = String.valueOf(editText.getText());
        if (editTextContenido.matches("")) {
            editText.setText("1");
        } else {
            editText.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
        }
    }

    private void restaEditText(@NotNull EditText editText) {
        Integer valorActual;
        String editTextContenido = String.valueOf(editText.getText());
        if (editTextContenido.matches("")) {
            editText.setText("0");
        } else {
            valorActual = Integer.valueOf(editTextContenido);
            if (valorActual > 0) {
                editText.setText(String.valueOf(valorActual - 1));
            }
        }
    }


}