package com.project.getfit.ui.models;

import java.util.List;
import java.util.Objects;

public class Receta {
    private String titulo;
    private String linkImagen;
    private Integer kcalorias;
    private Integer numRaciones;
    private List<String> ingredientes;


    public Receta(String titulo, String linkImagen, Integer kcalorias, Integer numRaciones, List<String> ingredientes) {
        this.titulo = titulo;
        this.linkImagen = linkImagen;
        this.kcalorias = kcalorias;
        this.numRaciones = numRaciones;
        this.ingredientes = ingredientes;
    }


    public String getTitulo() {
        return titulo;
    }

    public String getLinkImagen() {
        return linkImagen;
    }

    public Integer getKcalorias() {
        return kcalorias;
    }

    public Integer getNumRaciones() {
        return numRaciones;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setLinkImagen(String linkImagen) {
        this.linkImagen = linkImagen;
    }

    public void setKcalorias(Integer kcalorias) {
        this.kcalorias = kcalorias;
    }

    public void setNumRaciones(Integer numRaciones) {
        this.numRaciones = numRaciones;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receta)) return false;
        Receta receta = (Receta) o;
        return Objects.equals(getTitulo(), receta.getTitulo()) &&
                Objects.equals(getLinkImagen(), receta.getLinkImagen()) &&
                Objects.equals(getKcalorias(), receta.getKcalorias()) &&
                Objects.equals(getNumRaciones(), receta.getNumRaciones()) &&
                Objects.equals(getIngredientes(), receta.getIngredientes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getLinkImagen(), getKcalorias(), getNumRaciones(), getIngredientes());
    }

    @Override
    public String toString() {
        return "Receta{" +
                "titulo='" + titulo + '\'' +
                ", linkImagen='" + linkImagen + '\'' +
                ", kcalorias=" + kcalorias +
                ", numRaciones=" + numRaciones +
                ", ingredientes=" + ingredientes +
                '}';
    }
}
