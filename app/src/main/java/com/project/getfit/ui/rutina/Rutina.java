package com.project.getfit.ui.rutina;

import androidx.room.*;

import com.project.getfit.ui.ejercicios.Ejercicio;

import java.util.List;

@Entity
public class Rutina {
    public Rutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    @PrimaryKey
    public int uid;

    @Override
    public String toString() {
        return "Rutina{" +
                "uid=" + uid +
                ", nombreRutina='" + nombreRutina + '\'' +
                '}';
    }

    @ColumnInfo(name = "nombre_rutina")
    public String nombreRutina;


}

