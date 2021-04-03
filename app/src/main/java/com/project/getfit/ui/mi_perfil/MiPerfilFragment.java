package com.project.getfit.ui.mi_perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.MainActivity;
import com.project.getfit.R;

public class MiPerfilFragment extends Fragment {

    private Button configurar;
    private Button guardar;
    private Button cancelar;
    private TextView textoTituloConfiguracion;
    private TextView textoTituloPerfil;
    private EditText editNombre;
    private EditText editEstatura;
    private EditText editPeso;
    private EditText editEdad;
    private RadioGroup editSexo;
    private TextView mostrarClasificacion;
    private TextView mostrarIMC;
    private TextView mostrarEstatura;
    private TextView mostrarPeso;
    private TextView mostrarSexo;
    private TextView mostrarEdad;
    private TextView mostrarTasaMetabolica;
    private TextView mostrarFrecuencia;
    private LinearLayout linearMiPerfil;
    private LinearLayout linearConfiguracion;

    private TextView TextoPesoPerfil;
    private TextView TextoEstaturaPerfil;
    private TextView TextoImcPerfil;
    private TextView TextoResultadoPerfil;
    private TextView TextoSexoPerfil;
    private TextView TextoEdadPerfil;
    private TextView TextoTasaMetabolicaPerfil;
    private TextView TextoFrecuenciaPerfil;
    private ImageView imagenMiPerfil;
    private ImageView imagenMiPerfilConfiguracion;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mi_perfil, container, false);


        editNombre = root.findViewById(R.id.edittext_nombre_perfil);
        editEstatura = root.findViewById(R.id.edittext_estatura);
        editPeso = root.findViewById(R.id.edittext_peso);
        editEdad = root.findViewById(R.id.edittext_edad_perfil);
        editSexo = root.findViewById(R.id.radioSexo);
        mostrarEstatura = root.findViewById(R.id.mostrarEstaturaPerfil);
        mostrarPeso = root.findViewById(R.id.mostrarPesoPerfil);
        mostrarIMC = root.findViewById(R.id.mostrarIMCPerfil);
        mostrarSexo = root.findViewById(R.id.mostrarSexo);
        mostrarEdad = root.findViewById(R.id.mostrarEdadPerfil);
        mostrarTasaMetabolica = root.findViewById(R.id.mostrarTasaMetabolicaPerfil);
        mostrarFrecuencia = root.findViewById(R.id.mostrarFrecuenciaPerfil);
        mostrarClasificacion = root.findViewById(R.id.mostrarClasificacionPerfil);

        configurar = root.findViewById(R.id.botonIrAConfiguracion);
        guardar = root.findViewById(R.id.botonGuardarConfiguracionPerfil);
        cancelar = root.findViewById(R.id.botonCancelarConfiguracionPerfil);
        linearMiPerfil = root.findViewById(R.id.linear_mi_perfil);
        linearConfiguracion = root.findViewById(R.id.linearConfigurarPerfil);
        textoTituloConfiguracion = root.findViewById(R.id.tituloConfiguracionPerfil);
        textoTituloPerfil = root.findViewById(R.id.tituloPerfil);

        TextoPesoPerfil = root.findViewById(R.id.text_peso_perfil);
        TextoEstaturaPerfil = root.findViewById(R.id.text_estatura_perfil);
        TextoImcPerfil = root.findViewById(R.id.text_imc_perfil);
        TextoResultadoPerfil = root.findViewById(R.id.text_resultado_perfil);
        TextoEdadPerfil = root.findViewById(R.id.text_edad_perfil);
        TextoSexoPerfil = root.findViewById(R.id.text_sexo_perfil);
        TextoFrecuenciaPerfil = root.findViewById(R.id.text_frecuencia_perfil);
        TextoTasaMetabolicaPerfil = root.findViewById(R.id.text_tasa_metabolica_perfil);
        imagenMiPerfil = root.findViewById(R.id.imagen_mi_perfil);
        imagenMiPerfilConfiguracion = root.findViewById(R.id.imagen_mi_perfil_configuracion);

        mostrarInfo();

        // Codigo para el boton de pulsar atras
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        root.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    if (linearMiPerfil.getVisibility() == View.GONE) {
                        linearMiPerfil.setVisibility(View.VISIBLE);
                        linearConfiguracion.setVisibility(View.GONE);
                        return true; // Si devuelve true captura el boton
                    } else {
                        return false; // si devuelve false no, y volveria hacia atras
                    }

                }
                return false;
            }
        } );

        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombre = datos.getString("nombrePerfil", "");
        if (nombre != "") {
            textoTituloConfiguracion.setText("Perfil de " + nombre);
            textoTituloPerfil.setText("Perfil de " + nombre);
        }

        configurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearMiPerfil.setVisibility(View.GONE);
                linearConfiguracion.setVisibility(View.VISIBLE);
                mostrarInfo();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInfo();
                linearMiPerfil.setVisibility(View.VISIBLE);
                linearConfiguracion.setVisibility(View.GONE);
                mostrarInfo();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearMiPerfil.setVisibility(View.VISIBLE);
                linearConfiguracion.setVisibility(View.GONE);
            }
        });

        animacionArriba(textoTituloConfiguracion);
        animacionArriba(imagenMiPerfil);
        animacionArriba(imagenMiPerfilConfiguracion);
        animacionArriba(textoTituloPerfil);
        /*
        animacionDerecha(mostrarClasificacion);
        animacionDerecha(mostrarIMC);
        animacionDerecha(mostrarEstatura);
        animacionDerecha(mostrarPeso);
        animacionIzquierda(TextoPesoPerfil);
        animacionIzquierda(TextoImcPerfil);
        animacionIzquierda(TextoEstaturaPerfil);
        animacionIzquierda(TextoResultadoPerfil);
         */
        animacionAbajo(configurar);
        animacionAbajo(guardar);
        animacionAbajo(cancelar);


        return root;
    }

    // Creación de información de Usuarios.

    private void guardarInfo() {
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editar = datos.edit();
        editar.putString("nombrePerfil", editNombre.getText().toString());
        editar.putString("estaturaPerfil", editEstatura.getText().toString());
        editar.putString("pesoPerfil", editPeso.getText().toString());
        editar.putString("edadPerfil", editEdad.getText().toString());
        RadioButton femenino = root.findViewById(R.id.Femenino);
        RadioButton masculino = root.findViewById(R.id.Masculino);
        if (femenino.isChecked()){
            editar.putString("sexoPerfil", "Femenino");
        }else if(masculino.isChecked()){
            editar.putString("sexoPerfil", "Masculino");
        }
        editar.commit();

    }

    private void mostrarInfo() {
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombre = datos.getString("nombrePerfil", "");
        editNombre.setText(nombre);
        String estatura = datos.getString("estaturaPerfil", "");
        editEstatura.setText(estatura);
        mostrarEstatura.setText(estatura + " cm");
        String edad = datos.getString("edadPerfil", "");
        editEdad.setText(edad);
        mostrarEdad.setText(edad + " años");
        String sexo = datos.getString("sexoPerfil", "");
        if(sexo == "Femenino"){
            editSexo.check(R.id.Femenino);
        }else{
            editSexo.check(R.id.Masculino);
        }
        mostrarSexo.setText(sexo);
        String peso = datos.getString("pesoPerfil", "");
        editPeso.setText(peso);
        mostrarPeso.setText(peso + " kg");
        if(!peso.equals("") && !estatura.equals("")) {
            Double formula = (Double.parseDouble(peso) / Math.pow(Double.parseDouble(estatura) / 100, 2));
            mostrarIMC.setText(formula.toString().substring(0, 6));
            if(formula < 18.5) {
                mostrarClasificacion.setText("Peso insuficiente");
            } else if(formula < 24.9) {
                mostrarClasificacion.setText("Normopeso");

            }else if(formula < 26.9) {
                mostrarClasificacion.setText("Sobrepeso grado I");

            }else if(formula < 29.9) {
                mostrarClasificacion.setText("Sobrepeso grado II (preobesidad)");

            }else if(formula < 34.9) {
                mostrarClasificacion.setText("Obesidad tipo I");

            }else if(formula < 39.9) {
                mostrarClasificacion.setText("Obesidad tipo II");

            }else if(formula < 49.9) {
                mostrarClasificacion.setText("Obesidad tipo III (mórbida)");

            }else {
                mostrarClasificacion.setText("Obesidad tipo IV (Extrema)");

            }
        }else {
            mostrarPeso.setText("-- kg");
            mostrarEstatura.setText("-- cm");
            mostrarIMC.setText("--");
            mostrarClasificacion.setText("Desconocido");
        }

        if(!edad.equals("")) {
            int frecuencia = 220 - Integer.valueOf(edad);
            mostrarFrecuencia.setText(frecuencia + " pulsaciones");
        } else {
            mostrarEdad.setText("-- años");
            mostrarFrecuencia.setText("-- pulsaciones");
        }

        if(!peso.equals("") && !estatura.equals("") && !sexo.equals("") && !edad.equals("")){
            if(sexo.equals("Femenino")){
                Double calorias = 10 * Double.parseDouble(peso) + 6.5 * Double.parseDouble(estatura) - 5 * Double.parseDouble(edad) - 161;
                int caloriasInt = (int) Math.round(calorias);
                mostrarTasaMetabolica.setText(caloriasInt + " kcal");
            }else{
                Double calorias = 10 * Double.parseDouble(peso) + 6.5 * Double.parseDouble(estatura) - 5 * Double.parseDouble(edad) + 5;
                int caloriasInt = (int) Math.round(calorias);
                mostrarTasaMetabolica.setText(caloriasInt + " kcal");
            }

        }else{
            mostrarPeso.setText("-- kg");
            mostrarEstatura.setText("-- cm");
            mostrarEdad.setText("-- años");
            mostrarSexo.setText("Desconocido");
            mostrarTasaMetabolica.setText("-- kcal");
        }
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
}