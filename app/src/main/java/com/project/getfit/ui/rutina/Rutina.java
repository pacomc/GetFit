package com.project.getfit.ui.rutina;

import androidx.room.*;

import com.project.getfit.ui.ejercicios.Ejercicio;

import java.util.List;

@Entity
public class Rutina {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "nombre_rutina")
    public String nombreRutina;

    @ColumnInfo(name = "ejercicios_rutina")
    public List<Ejercicio> ejerciciosRutina;
}

