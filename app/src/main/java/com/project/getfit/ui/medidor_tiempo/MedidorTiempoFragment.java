package com.project.getfit.ui.medidor_tiempo;

import android.graphics.Color;
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

import com.project.getfit.R;

public class MedidorTiempoFragment extends Fragment {
    private Boolean estadoMedidorTiempo = false; // Para controlar si el cronometro está activo

    private MedidorTiempoViewModel medidorTiempoViewModel;
    private CountDownTimer countDownTimer;
    private Integer tiempoContador = 0;
    private Integer segundo = 0;
    private Integer minuto = 0;
    private Integer hora = 0;

    private Button botonComenzar;

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
    private EditText editTextNum1Descanso;
    private EditText editTextNum2Descanso;
    private EditText editTextNum1Ejercicio;
    private EditText editTextNum2Ejercicio;
    private EditText editTextNumSeries;

    private Integer seriesRestantes;






    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        medidorTiempoViewModel = new ViewModelProvider(this).get(MedidorTiempoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medidor_tiempo, container, false);
        TextView textViewReloj = root.findViewById(R.id.text_medidor_tiempo);

        Integer definirTiempo = 10000000*10000000;

        botonComenzar = root.findViewById(R.id.boton_comenzar_crono);

        //  --- INICIO BOTONES DE MEDIDOR DE TIEMPO PARA SUMAR Y RESTAR VALORES ---

        botonSuma1Descanso = root.findViewById(R.id.boton_suma1_descanso);
        botonSuma2Descanso = root.findViewById(R.id.boton_suma2_descanso);
        botonResta1Descanso = root.findViewById(R.id.boton_resta1_descanso);
        botonResta2Descanso = root.findViewById(R.id.boton_resta2_descanso);
        editTextNum1Descanso = root.findViewById(R.id.text_num1_descanso);
        editTextNum2Descanso = root.findViewById(R.id.text_num2_descanso);

        botonSuma1Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                suma1Descanso();
            }
        });

        botonSuma2Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                suma2Descanso();
            }
        });

        botonResta1Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resta1Descanso();
            }
        });

        botonResta2Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resta2Descanso();
            }
        });

        botonSuma1Ejercicio = root.findViewById(R.id.boton_suma1_ejercicio);
        botonSuma2Ejercicio = root.findViewById(R.id.boton_suma2_ejercicio);
        botonResta1Ejercicio = root.findViewById(R.id.boton_resta1_ejercicio);
        botonResta2Ejercicio = root.findViewById(R.id.boton_resta2_ejercicio);
        editTextNum1Ejercicio = root.findViewById(R.id.text_num1_ejercicio);
        editTextNum2Ejercicio = root.findViewById(R.id.text_num2_ejercicio);

        botonSuma1Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                suma1Ejercicio();
            }
        });

        botonSuma2Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                suma2Ejercicio();
            }
        });

        botonResta1Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resta1Ejercicio();
            }
        });

        botonResta2Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resta2Ejercicio();
            }
        });

        botonSumaSeries = root.findViewById(R.id.boton_suma_series);
        botonRestaSeries = root.findViewById(R.id.boton_resta_series);
        editTextNumSeries = root.findViewById(R.id.text_num_series);

        botonSumaSeries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sumaSerie();
            }
        });

        botonRestaSeries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restaSerie();
            }
        });

        //  --- FIN BOTONES DE MEDIDOR DE TIEMPO PARA SUMAR Y RESTAR VALORES ---

        // Boton de comenzar y parar

        Button botonMedidorTiempo = root.findViewById(R.id.boton_comenzar_crono);

        botonMedidorTiempo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (estadoMedidorTiempo) {
                    estadoMedidorTiempo = false; // Para llevar control de pause y comienzo
                    botonComenzar.setText("Comenzar");

                    tiempoContador = 0;
                    if (countDownTimer != null) { // Porque se puede pausar y el countDownTime no estar inicializado (si las series son 0)
                        countDownTimer.cancel();
                    }

                } else {
                    estadoMedidorTiempo = true;
                    botonComenzar.setText("Pausar");

                    // TODO: Comprobar y mostrar mensaje si los valores estan a 0 o incluso si estan vacios. IMPORTANTE.
                    seriesRestantes = Integer.valueOf(String.valueOf(editTextNumSeries.getText()));
                    if (seriesRestantes > 0) { // Si hay series que contar
                        seriesRestantes = seriesRestantes - 1;

                        Integer segundosTotalesDescanso = Integer.valueOf(String.valueOf(editTextNum1Descanso.getText()))*60 + Integer.valueOf(String.valueOf(editTextNum2Descanso.getText()));
                        Integer segundosTotalesEjercicio = Integer.valueOf(String.valueOf(editTextNum1Ejercicio.getText()))*60 + Integer.valueOf(String.valueOf(editTextNum2Ejercicio.getText()));

                        countDownTimer = new CountDownTimer(definirTiempo, 1000) { // antes cada 1 segundos
                            @Override
                            public void onTick(long l) {
                                tiempoContador = tiempoContador + 1;
                                textViewReloj.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

                                segundo = tiempoContador % 60;
                                minuto = (tiempoContador / 60) % 60;
                                hora = tiempoContador / 3600;

                                if (tiempoContador % (segundosTotalesEjercicio + segundosTotalesDescanso) == 0) {
                                    if (seriesRestantes == 0) { // Si se han acabado las series para el cronómetro
                                        countDownTimer.cancel();
                                        tiempoContador = 0;
                                        textViewReloj.setTextColor(ContextCompat.getColor(getContext(), R.color.color_reloj_terminar_serie));
                                        estadoMedidorTiempo = false;
                                        botonComenzar.setText("Comenzar");

                                    } else {
                                        seriesRestantes = seriesRestantes - 1;
                                        textViewReloj.setTextColor(ContextCompat.getColor(getContext(), R.color.color_reloj_serie));
                                    }

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
        });

        return root;
    }


    public void suma1Descanso() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum1Descanso.getText());
        if (editTextContenido.matches("")) {
            editTextNum1Descanso.setText(String.valueOf(1));
        } else {
            editTextNum1Descanso.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
        }
    }

    public void suma2Descanso() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum2Descanso.getText());
        if (editTextContenido.matches("")) {
            editTextNum1Descanso.setText(String.valueOf(1));
        } else {
            editTextNum2Descanso.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
        }
    }

    public void resta1Descanso() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum1Descanso.getText());
        if (editTextContenido.matches("")) {
            editTextNum1Descanso.setText(String.valueOf(0));
        } else {
            valorActual = Integer.valueOf(editTextContenido);
            if (valorActual > 0) {
                editTextNum1Descanso.setText(String.valueOf(valorActual - 1));
            }
        }
    }

    public void resta2Descanso() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum2Descanso.getText());
        if (editTextContenido.matches("")) {
            editTextNum1Descanso.setText(String.valueOf(0));
        } else {
            valorActual = Integer.valueOf(editTextContenido);
            if (valorActual > 0) {
                editTextNum2Descanso.setText(String.valueOf(valorActual - 1));
            }
        }
    }

    public void suma1Ejercicio() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum1Ejercicio.getText());
        if (editTextContenido.matches("")) {
            editTextNum1Ejercicio.setText(String.valueOf(1));
        } else {
            editTextNum1Ejercicio.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
        }
    }

    public void suma2Ejercicio() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum2Ejercicio.getText());
        if (editTextContenido.matches("")) {
            editTextNum2Ejercicio.setText(String.valueOf(1));
        } else {
            editTextNum2Ejercicio.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
        }
    }

    public void resta1Ejercicio() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum1Ejercicio.getText());
        if (editTextContenido.matches("")) {
            editTextNum1Ejercicio.setText(String.valueOf(0));
        } else {
            valorActual = Integer.valueOf(editTextContenido);
            if (valorActual > 0) {
                editTextNum1Ejercicio.setText(String.valueOf(valorActual - 1));
            }
        }
    }

    public void resta2Ejercicio() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNum2Ejercicio.getText());
        if (editTextContenido.matches("")) {
            editTextNum2Ejercicio.setText(String.valueOf(0));
        } else {
            valorActual = Integer.valueOf(editTextContenido);
            if (valorActual > 0) {
                editTextNum2Ejercicio.setText(String.valueOf(valorActual - 1));
            }
        }
    }


    public void sumaSerie() {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNumSeries.getText());
        if (editTextContenido.matches("")) {
            editTextNumSeries.setText(String.valueOf(1));
        } else {
            editTextNumSeries.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
        }
    }

    public void restaSerie () {
        Integer valorActual;
        String editTextContenido = String.valueOf(editTextNumSeries.getText());
        if (editTextContenido.matches("")) {
            editTextNumSeries.setText(String.valueOf(0));
        } else {
            valorActual = Integer.valueOf(editTextContenido);
            if (valorActual > 0) {
                editTextNumSeries.setText(String.valueOf(valorActual - 1));
            }
        }
    }




}