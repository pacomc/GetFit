package com.project.getfit.ui.medidor_tiempo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

public class MedidorTiempoFragment extends Fragment {

    private MedidorTiempoViewModel medidorTiempoViewModel;
    private CountDownTimer t2;
    private Integer tiempoContador = 0;
    private Integer segundo = 0;
    private Integer minuto = 0;
    private Integer hora = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        medidorTiempoViewModel = new ViewModelProvider(this).get(MedidorTiempoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medidor_tiempo, container, false);
        TextView textView = root.findViewById(R.id.text_medidor_tiempo);

        Integer definirTiempo = 10000000*10000000;
        t2 = new CountDownTimer(definirTiempo, 1000) { // antes cada 1 segundos
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
                Toast.makeText(getContext(), "Se ha producido un error, inicio la marcha de nuevo, si quiere continuar", Toast.LENGTH_LONG).show();
            }
        }.start();

        return root;
    }
}