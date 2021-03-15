package com.project.getfit.ui.medidor_tiempo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.project.getfit.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedidorTiempoFragment extends Fragment {
    private MedidorTiempoViewModel medidorTiempoViewModel;
    private View root;

    private CountDownTimer countDownTimer;

    private Integer tiempoContadorTotal = 0;
    private Integer tiempoContadorActual = 0;
    private Integer segundoTotal = 0;
    private Integer minutoTotal = 0;
    private Integer horaTotal = 0;
    private Integer segundoActual = 0;
    private Integer minutoActual = 0;
    private Integer horaActual = 0;

    private TextView textViewRelojTotal;
    private TextView textViewRelojActual;
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
    private Button botonComenzar;
    private Button botonPausar;
    private Button botonParar;
    private EditText editTextNum1Descanso;
    private EditText editTextNum2Descanso;
    private EditText editTextNum1Ejercicio;
    private EditText editTextNum2Ejercicio;
    private EditText editTextNumSeries;
    private TextView textViewNum1Descanso;
    private TextView textViewNum2Descanso;
    private TextView textViewNum1Ejercicio;
    private TextView textViewNum2Ejercicio;
    private TextView textViewNumSeries;
    private TextView textViewSerieActual;
    private List<EditText> editTextsMedidorTiempo = new ArrayList<>();

    private Integer seriesRestantes; // Numero de series que le quedan
    private Boolean estaEjercicio = true; // True si esta en tiempo de ejercicio; false si esta en descanso


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        medidorTiempoViewModel = new ViewModelProvider(this).get(MedidorTiempoViewModel.class);
        root = inflater.inflate(R.layout.fragment_medidor_tiempo, container, false);

        //  --- INICIO BOTONES DE MEDIDOR DE TIEMPO PARA SUMAR Y RESTAR VALORES ---
        // Inicializacion textView
        textViewRelojActual = root.findViewById(R.id.text_medidor_tiempo_actual);
        textViewRelojTotal = root.findViewById(R.id.text_medidor_tiempo_total);
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
        botonComenzar = root.findViewById(R.id.boton_comenzar_crono);
        botonPausar = root.findViewById(R.id.boton_pausar_crono);
        botonParar = root.findViewById(R.id.boton_stop_crono);
        // Inicializacion textviewas
        editTextNum1Descanso = root.findViewById(R.id.edittext_num1_descanso);
        editTextNum2Descanso = root.findViewById(R.id.edittext_num2_descanso);
        editTextNum1Ejercicio = root.findViewById(R.id.edittext_num1_ejercicio);
        editTextNum2Ejercicio = root.findViewById(R.id.edittext_num2_ejercicio);
        editTextNumSeries = root.findViewById(R.id.edittext_num_series);
        // Inicializacion edittexts
        textViewNum1Descanso = root.findViewById(R.id.text_num1_descanso);
        textViewNum2Descanso = root.findViewById(R.id.text_num2_descanso);
        textViewNum1Ejercicio = root.findViewById(R.id.text_num1_ejercicio);
        textViewNum2Ejercicio = root.findViewById(R.id.text_num2_ejercicio);
        textViewNumSeries = root.findViewById(R.id.text_num_series);
        textViewSerieActual = root.findViewById(R.id.text_serie_actual);
        // Inicializacion lista con edittexts (para poder recorrerlos facilmente)
        editTextsMedidorTiempo.add(editTextNum1Descanso);
        editTextsMedidorTiempo.add(editTextNum2Descanso);
        editTextsMedidorTiempo.add(editTextNum1Ejercicio);
        editTextsMedidorTiempo.add(editTextNum2Ejercicio);
        editTextsMedidorTiempo.add(editTextNumSeries);



        funcionalidadBotones(); // Se inserta funcionalidad a los botones

        botonComenzar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                accionComenzar();
            }

        });

        botonPausar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                accionPausar();
            }

        });



        botonParar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                accionParar();
            }

        });

        return root;
    }

    private void accionPausar() {
        countDownTimer.cancel();

        botonParar.setVisibility(View.GONE);
        botonPausar.setVisibility(View.GONE);
        botonComenzar.setVisibility(View.VISIBLE);

    }

    private void accionParar() {
        tiempoContadorActual = 0;
        tiempoContadorTotal = 0;
        String tiempoActualFormateado = String.format("%02d:%02d:%02d", 0, 0, 0);
        String tiempoTotalFormateado = String.format("%02d:%02d:%02d", 0, 0, 0);
        textViewRelojActual.setText(tiempoActualFormateado);
        textViewRelojTotal.setText(tiempoTotalFormateado);
        textViewSerieActual.setText("0");
        estaEjercicio = true;

        countDownTimer.cancel();
        hacerEditable();

        botonParar.setVisibility(View.GONE);
        botonPausar.setVisibility(View.GONE);
        botonComenzar.setVisibility(View.VISIBLE);
    }

    private void accionComenzar() {
        Integer definirTiempo = 10000000*10000000;
        MediaPlayer sonidoTerminarSerie = MediaPlayer.create(getContext(), R.raw.nokia_sms_tone);


        if (comprobacionValoresEditText()) { // Si pasa las comprobaciones

            hacerFijo();
            botonParar.setVisibility(View.VISIBLE);
            botonPausar.setVisibility(View.VISIBLE);
            botonComenzar.setVisibility(View.GONE);

            seriesRestantes = numeroSeriesEstablecidas();
            seriesRestantes = seriesRestantes - 1;

            Integer segundosTotalesDescanso = segundosDescansoEstablecidos();
            Integer segundosTotalesEjercicio = segundosEjercicioEstablecidos();

            countDownTimer = new CountDownTimer(definirTiempo, 1000) { // antes cada 1 segundos
                @Override
                public void onTick(long l) {
                    tiempoContadorActual = tiempoContadorActual + 1;
                    tiempoContadorTotal = tiempoContadorTotal + 1;
                    textViewRelojTotal.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                    segundoTotal = tiempoContadorTotal % 60;
                    minutoTotal = (tiempoContadorTotal / 60) % 60;
                    horaTotal = tiempoContadorTotal / 3600;

                    segundoActual = tiempoContadorActual % 60;
                    minutoActual = (tiempoContadorActual / 60) % 60;
                    horaActual = tiempoContadorActual / 3600;


                    if (estaEjercicio) { // Si esta en tiempo de ejercicio
                        if (tiempoContadorActual == segundosTotalesEjercicio) { // Si termina el ejercicio
                            tiempoContadorActual = 0;
                            estaEjercicio = false;
                        }

                    } else { // si esta en tiempo de descanso
                        if (tiempoContadorActual == segundosTotalesDescanso) { // Si termina el descanso
                            tiempoContadorActual = 0;
                            estaEjercicio = true;

                            if (seriesRestantes == 0) { // Si se han acabado las series, es decir, ha terminado la duracion total del ejercicio
                                textViewRelojTotal.setTextColor(ContextCompat.getColor(getContext(), R.color.color_reloj_terminar_serie));
                                countDownTimer.cancel();
                                tiempoContadorActual = 0;
                                tiempoContadorTotal = 0;
                                hacerEditable();
                                botonParar.setVisibility(View.GONE);
                                botonPausar.setVisibility(View.GONE);
                                botonComenzar.setVisibility(View.VISIBLE);
                            } else {
                                textViewSerieActual.setText(String.valueOf(numeroSeriesEstablecidas() - seriesRestantes));
                                seriesRestantes--;
                                textViewRelojTotal.setTextColor(ContextCompat.getColor(getContext(), R.color.color_reloj_serie));
                            }
                        }
                    }


                    String tiempoActualFormateado = String.format("%02d:%02d:%02d", horaActual, minutoActual, segundoActual);
                    String tiempoTotalFormateado = String.format("%02d:%02d:%02d", horaTotal, minutoTotal, segundoTotal);
                    textViewRelojActual.setText(tiempoActualFormateado);
                    textViewRelojTotal.setText(tiempoTotalFormateado);

                }

                @Override
                public void onFinish() {
                    tiempoContadorActual = 0;
                    tiempoContadorTotal = 0;
                    Toast.makeText(getContext(), "Se ha producido un error, inicie de nuevo", Toast.LENGTH_LONG).show();
                }
            }.start();




        }
    }

    private void hacerFijo() {
        // Se cambian los edittext por textview cuando comienza
        editTextNum1Descanso.setVisibility(View.GONE);
        editTextNum2Descanso.setVisibility(View.GONE);
        editTextNum1Ejercicio.setVisibility(View.GONE);
        editTextNum2Ejercicio.setVisibility(View.GONE);
        editTextNumSeries.setVisibility(View.GONE);

        textViewNum1Descanso.setText(String.valueOf(editTextNum1Descanso.getText()));
        textViewNum2Descanso.setText(String.valueOf(editTextNum2Descanso.getText()));
        textViewNum1Ejercicio.setText(String.valueOf(editTextNum1Ejercicio.getText()));
        textViewNum2Ejercicio.setText(String.valueOf(editTextNum2Ejercicio.getText()));
        textViewNumSeries.setText(String.valueOf(editTextNumSeries.getText()));
        textViewNum1Descanso.setVisibility(View.VISIBLE);
        textViewNum2Descanso.setVisibility(View.VISIBLE);
        textViewNum1Ejercicio.setVisibility(View.VISIBLE);
        textViewNum2Ejercicio.setVisibility(View.VISIBLE);
        textViewNumSeries.setVisibility(View.VISIBLE);
        textViewSerieActual.setVisibility(View.VISIBLE);



        // Se quitan los botones
        botonResta1Descanso.setVisibility(View.GONE);
        botonResta2Descanso.setVisibility(View.GONE);
        botonResta1Ejercicio.setVisibility(View.GONE);
        botonResta2Ejercicio.setVisibility(View.GONE);
        botonSuma1Descanso.setVisibility(View.GONE);
        botonSuma2Descanso.setVisibility(View.GONE);
        botonSuma1Ejercicio.setVisibility(View.GONE);
        botonSuma2Ejercicio.setVisibility(View.GONE);
        botonSumaSeries.setVisibility(View.GONE);
        botonRestaSeries.setVisibility(View.GONE);
    }

    private void hacerEditable() {
         // Se cambian los edittext por textview cuando comienza
        editTextNum1Descanso.setVisibility(View.VISIBLE);
        editTextNum2Descanso.setVisibility(View.VISIBLE);
        editTextNum1Ejercicio.setVisibility(View.VISIBLE);
        editTextNum2Ejercicio.setVisibility(View.VISIBLE);
        editTextNumSeries.setVisibility(View.VISIBLE);

        textViewNum1Descanso.setVisibility(View.GONE);
        textViewNum2Descanso.setVisibility(View.GONE);
        textViewNum1Ejercicio.setVisibility(View.GONE);
        textViewNum2Ejercicio.setVisibility(View.GONE);
        textViewNumSeries.setVisibility(View.GONE);
        textViewSerieActual.setVisibility(View.GONE);



        // Se quitan los botones
        botonResta1Descanso.setVisibility(View.VISIBLE);
        botonResta2Descanso.setVisibility(View.VISIBLE);
        botonResta1Ejercicio.setVisibility(View.VISIBLE);
        botonResta2Ejercicio.setVisibility(View.VISIBLE);
        botonSuma1Descanso.setVisibility(View.VISIBLE);
        botonSuma2Descanso.setVisibility(View.VISIBLE);
        botonSuma1Ejercicio.setVisibility(View.VISIBLE);
        botonSuma2Ejercicio.setVisibility(View.VISIBLE);
        botonSumaSeries.setVisibility(View.VISIBLE);
        botonRestaSeries.setVisibility(View.VISIBLE);
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