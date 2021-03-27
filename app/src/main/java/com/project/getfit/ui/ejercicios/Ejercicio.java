package com.project.getfit.ui.ejercicios;


public class Ejercicio {
    private String nombre;
    private String linkEjercicio;
    private String linkImagen;
    private String parteCuerpo;








    public Ejercicio(String nombre, String linkEjercicio, String linkImagen, String parteCuerpo) {
        super();
        this.nombre = nombre;
        this.linkEjercicio = linkEjercicio;
        this.linkImagen = linkImagen;
        this.parteCuerpo = parteCuerpo;
    }





    public String getNombre() {
        return nombre;
    }





    public String getLinkImagen() {
        return linkImagen;
    }





    public String getLinkEjercicio() {
        return linkEjercicio;
    }







    public String getParteCuerpo() {
        return parteCuerpo;
    }





    @Override
    public String toString() {
        return "Ejercicio [nombre=" + nombre + ", linkImagen=" + linkImagen + ", parteCuerpo=" + parteCuerpo
                + ", linkEjercicio=" + linkEjercicio + "]";
    }





    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((linkEjercicio == null) ? 0 : linkEjercicio.hashCode());
        result = prime * result + ((linkImagen == null) ? 0 : linkImagen.hashCode());
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((parteCuerpo == null) ? 0 : parteCuerpo.hashCode());
        return result;
    }





    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ejercicio other = (Ejercicio) obj;
        if (linkEjercicio == null) {
            if (other.linkEjercicio != null)
                return false;
        } else if (!linkEjercicio.equals(other.linkEjercicio))
            return false;
        if (linkImagen == null) {
            if (other.linkImagen != null)
                return false;
        } else if (!linkImagen.equals(other.linkImagen))
            return false;
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (parteCuerpo == null) {
            if (other.parteCuerpo != null)
                return false;
        } else if (!parteCuerpo.equals(other.parteCuerpo))
            return false;
        return true;
    }






}
