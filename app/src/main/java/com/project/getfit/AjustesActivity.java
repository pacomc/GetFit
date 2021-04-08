package com.project.getfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.getfit.ui.rutina.AppDatabase;
import com.project.getfit.ui.rutina.Rutina;
import com.project.getfit.ui.rutina.RutinaDao;

import java.io.File;

public class AjustesActivity extends AppCompatActivity {

    private Button botonResetearDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        botonResetearDatos = findViewById(R.id.boton_resetear_datos);


        botonResetearDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences datosPerfil = getSharedPreferences("DatosPerfil", Context.MODE_PRIVATE);
                SharedPreferences.Editor editarPerfil = datosPerfil.edit();
                editarPerfil.clear();
                editarPerfil.commit();

                SharedPreferences datosCalendario = getSharedPreferences("DatosCalendario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editarCalendario = datosCalendario.edit();
                editarCalendario.clear();
                editarCalendario.commit();

                new ResetearRutinas().execute();
            }
        });
    }

    private class ResetearRutinas extends AsyncTask<Rutina, Void, String> {

        protected String doInBackground(Rutina... rutina) {
            // Código válido para almacenar las rutinas en una base de datos creadas con Room
            AppDatabase db = Room.databaseBuilder(AjustesActivity.this, AppDatabase.class, "basedatos-rutinas")
                    .fallbackToDestructiveMigration()
                    .build();;
            RutinaDao rd = db.rutinaDao();

            rd.resetearRutinas();

            return rd.obtenerTodas().toString();
        }


        protected void onPostExecute(String peticionBaseDatos) {
            Log.d("Se han borrado todos los datos de rutinas:", peticionBaseDatos);
        }
    }


}