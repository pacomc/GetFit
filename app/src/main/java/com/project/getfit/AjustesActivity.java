package com.project.getfit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                alertDialog();

            }

            private void alertDialog() {
                AlertDialog.Builder dialog=new AlertDialog.Builder(AjustesActivity.this);
                dialog.setMessage("¿De verdad quieres borrar todos tus datos?");
                dialog.setTitle("¡ATENCIÓN!");
                dialog.setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SharedPreferences datosPerfil = getSharedPreferences("DatosPerfil", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editarPerfil = datosPerfil.edit();
                                editarPerfil.clear();
                                editarPerfil.commit();

                                SharedPreferences datosCalendario = getSharedPreferences("DatosCalendario", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editarCalendario = datosCalendario.edit();
                                editarCalendario.clear();
                                editarCalendario.commit();

                                new ResetearRutinas().execute();

                                Toast.makeText(AjustesActivity.this, "Datos reseteados, reinicia la app.", Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(AjustesActivity.this, "¡Uf! Casi se pierden todos tus datos.", Toast.LENGTH_SHORT).show();
                            }
                        });

                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
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