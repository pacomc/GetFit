package com.project.getfit.ui.medidor_tiempo;

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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        medidorTiempoViewModel = new ViewModelProvider(this).get(MedidorTiempoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medidor_tiempo, container, false);
        TextView textView = root.findViewById(R.id.text_medidor_tiempo);

        Integer definirTiempo = 10000000*10000000;


        //  --- INICIO BOTONES DE MEDIDOR DE TIEMPO PARA SUMAR Y RESTAR VALORES ---

        Button botonSuma1Descanso = root.findViewById(R.id.boton_suma1_descanso);
        Button botonSuma2Descanso = root.findViewById(R.id.boton_suma2_descanso);
        Button botonResta1Descanso = root.findViewById(R.id.boton_resta1_descanso);
        Button botonResta2Descanso = root.findViewById(R.id.boton_resta2_descanso);
        EditText editTextNum1Descanso = root.findViewById(R.id.text_num1_descanso);
        EditText editTextNum2Descanso = root.findViewById(R.id.text_num2_descanso);

        botonSuma1Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer valorActual;
                String editTextContenido = String.valueOf(editTextNum1Descanso.getText());
                if (editTextContenido.matches("")) {
                    editTextNum1Descanso.setText(String.valueOf(1));
                } else {
                    editTextNum1Descanso.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
                }
            }
        });

        botonSuma2Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer valorActual;
                String editTextContenido = String.valueOf(editTextNum2Descanso.getText());
                if (editTextContenido.matches("")) {
                    editTextNum1Descanso.setText(String.valueOf(1));
                } else {
                    editTextNum2Descanso.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
                }
            }
        });

        botonResta1Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        });

        botonResta2Descanso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        });

        Button botonSuma1Ejercicio = root.findViewById(R.id.boton_suma1_ejercicio);
        Button botonSuma2Ejercicio = root.findViewById(R.id.boton_suma2_ejercicio);
        Button botonResta1Ejercicio = root.findViewById(R.id.boton_resta1_ejercicio);
        Button botonResta2Ejercicio = root.findViewById(R.id.boton_resta2_ejercicio);
        EditText editTextNum1Ejercicio = root.findViewById(R.id.text_num1_ejercicio);
        EditText editTextNum2Ejercicio = root.findViewById(R.id.text_num2_ejercicio);

        botonSuma1Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer valorActual;
                String editTextContenido = String.valueOf(editTextNum1Ejercicio.getText());
                if (editTextContenido.matches("")) {
                    editTextNum1Ejercicio.setText(String.valueOf(1));
                } else {
                    editTextNum1Ejercicio.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
                }
            }
        });

        botonSuma2Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer valorActual;
                String editTextContenido = String.valueOf(editTextNum2Ejercicio.getText());
                if (editTextContenido.matches("")) {
                    editTextNum2Ejercicio.setText(String.valueOf(1));
                } else {
                    editTextNum2Ejercicio.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
                }
            }
        });

        botonResta1Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        });

        botonResta2Ejercicio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        });

        Button botonSumaSeries = root.findViewById(R.id.boton_suma_series);
        Button botonRestaSeries = root.findViewById(R.id.boton_resta_series);
        EditText editTextNumSeries = root.findViewById(R.id.text_num_series);

        botonSumaSeries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer valorActual;
                String editTextContenido = String.valueOf(editTextNumSeries.getText());
                if (editTextContenido.matches("")) {
                    editTextNumSeries.setText(String.valueOf(1));
                } else {
                    editTextNumSeries.setText(String.valueOf(Integer.valueOf(editTextContenido) + 1));
                }
            }
        });



        botonRestaSeries.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        });

        //  --- FIN BOTONES DE MEDIDOR DE TIEMPO PARA SUMAR Y RESTAR VALORES ---

        // Boton de comenzar y parar

        Button botonMedidorTiempo = root.findViewById(R.id.boton_comenzar_crono);

        botonMedidorTiempo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (estadoMedidorTiempo) {
                    tiempoContador = 0;
                    countDownTimer.cancel();
                    estadoMedidorTiempo = false;

                } else {
                    countDownTimer = new CountDownTimer(definirTiempo, 1000) { // antes cada 1 segundos
                        @Override
                        public void onTick(long l) {
                            tiempoContador = tiempoContador + 1;

                            segundo = tiempoContador % 60;
                            minuto = (tiempoContador / 60) % 60;
                            hora = tiempoContador / 3600;

                            String tiempoFormateado = String.format("%02d:%02d:%02d", hora, minuto, segundo);
                            textView.setText(tiempoFormateado);

                        }

                        @Override
                        public void onFinish() {
                            tiempoContador = 0;
                            Toast.makeText(getContext(), "Se ha producido un error, inicie de nuevo", Toast.LENGTH_LONG).show();
                        }
                    }.start();

                    estadoMedidorTiempo = true;
                }

            }
        });

        return root;
    }




}