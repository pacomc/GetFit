package com.project.getfit.ui.recetas;

import java.util.List;
import java.util.Objects;

public class Receta {
    private String titulo;
    private String linkImagen;
    private String kcalorias;
    private String numPorciones;
    private List<String> ingredientes;


    public Receta(String titulo, String linkImagen, String kcalorias, String numPorciones, List<String> ingredientes) {
        this.titulo = titulo;
        this.linkImagen = linkImagen;
        this.kcalorias = kcalorias;
        this.numPorciones = numPorciones;
        this.ingredientes = ingredientes;
    }


    public String getTitulo() {
        return titulo;
    }

    public String getLinkImagen() {
        return linkImagen;
    }

    public String getKcalorias() {
        return kcalorias;
    }

    public String getNumPorciones() {
        return numPorciones;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receta)) return false;
        Receta receta = (Receta) o;
        return Objects.equals(getTitulo(), receta.getTitulo()) &&
                Objects.equals(getLinkImagen(), receta.getLinkImagen()) &&
                Objects.equals(getKcalorias(), receta.getKcalorias()) &&
                Objects.equals(getNumPorciones(), receta.getNumPorciones()) &&
                Objects.equals(getIngredientes(), receta.getIngredientes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getLinkImagen(), getKcalorias(), getNumPorciones(), getIngredientes());
    }

    @Override
    public String toString() {
        return "Receta{" +
                "titulo='" + titulo + '\'' +
                ", linkImagen='" + linkImagen + '\'' +
                ", kcalorias=" + kcalorias +
                ", numPorciones=" + numPorciones +
                ", ingredientes=" + ingredientes +
                '}';
    }
}
