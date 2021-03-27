package com.project.getfit.ui.mi_perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.project.getfit.R;

public class MiPerfilFragment extends Fragment {

    private Button configurar;
    private Button guardar;
    private Button cancelar;
    private TextView textoTituloConfiguracion;
    private EditText editNombre;
    private EditText editEstatura;
    private EditText editPeso;
    private LinearLayout linearMiPerfil;
    private LinearLayout linearConfiguracion;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mi_perfil, container, false);


        editNombre = root.findViewById(R.id.edittext_nombre_perfil);
        editEstatura = root.findViewById(R.id.edittext_estatura);
        editPeso = root.findViewById(R.id.edittext_peso);
        configurar = root.findViewById(R.id.botonIrAConfiguracion);
        guardar = root.findViewById(R.id.botonGuardarConfiguracionPerfil);
        cancelar = root.findViewById(R.id.botonCancelarConfiguracionPerfil);
        linearMiPerfil = root.findViewById(R.id.linear_mi_perfil);
        linearConfiguracion = root.findViewById(R.id.linearConfigurarPerfil);
        textoTituloConfiguracion = root.findViewById(R.id.tituloConfiguracionPerfil);



        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombre = datos.getString("nombrePerfil", "");
        if (nombre != "") {
            textoTituloConfiguracion.setText("Perfil de " + nombre);
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
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearMiPerfil.setVisibility(View.VISIBLE);
                linearConfiguracion.setVisibility(View.GONE);
            }
        });

        return root;
    }

    // Creación de información de Usuarios.

    private void guardarInfo() {
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editar = datos.edit();
        editar.putString("nombrePerfil", editNombre.getText().toString());
        editar.putString("estaturaPerfil", editEstatura.getText().toString());
        editar.putString("pesoPerfil", editPeso.getText().toString());
        editar.commit();


        /* TODO: Mirar esto Dani, no se puede cambiar la info de un text de otro fragment, puedes acceder a la info guardada y
        modificarlo desde inicio. Un saludo Dani.

        if (editNombre.getText().toString() != "") {
            textoBienvenida.setText("BIENVENIDO " + editNombre.getText().toString().toUpperCase() + "!");
        }

        */
    }

    private void mostrarInfo() {
        SharedPreferences datos = getContext().getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nombre = datos.getString("nombrePerfil", "");
        editNombre.setText(nombre);
        String estatura = datos.getString("estaturaPerfil", "");
        editEstatura.setText(estatura);
        String peso = datos.getString("pesoPerfil", "");
        editPeso.setText(peso);
    }
}