package com.project.getfit.ui.rutina;

import androidx.room.*;

import com.project.getfit.ui.ejercicios.Ejercicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Rutina {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "nombre_rutina")
    private String nombreRutina;

    @ColumnInfo(name = "ejercicios")
    private ArrayList<Ejercicio> ejercicios;



    public Rutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
        this.ejercicios = new ArrayList<>();
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public String getNombreRutina() {
        return nombreRutina;
    }

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setNombreRutina(String nombreRutina) {
        this.nombreRutina = nombreRutina;
    }

    public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    @Override
    public String toString() {
        return "Rutina{" +
                "uid=" + uid +
                ", nombreRutina='" + nombreRutina + '\'' +
                ", ejercicios=" + ejercicios +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rutina)) return false;
        Rutina rutina = (Rutina) o;
        return getUid() == rutina.getUid() &&
                Objects.equals(getNombreRutina(), rutina.getNombreRutina()) &&
                Objects.equals(getEjercicios(), rutina.getEjercicios());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid(), getNombreRutina(), getEjercicios());
    }
}

