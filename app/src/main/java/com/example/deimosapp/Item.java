package com.example.deimosapp;

public class Item {
    private String nombre;
    private int imagenId;
    private String descripcion;

    public Item(String nombre, int imagenId, String descripcion) {
        this.nombre = nombre;
        this.imagenId = imagenId;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagenId() {
        return imagenId;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
