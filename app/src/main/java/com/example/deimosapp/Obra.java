package com.example.deimosapp;

import android.net.Uri;

public class Obra {
    private Uri imageUri;
    private String titulo;
    private String autor;
    private String descripcion;

    public Obra(Uri imageUri, String titulo, String autor, String descripcion) {
        this.imageUri = imageUri;
        this.titulo = titulo;
        this.autor = autor;
        this.descripcion = descripcion;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getText() {
        return "\"" + titulo + "\" - " + autor + "\n" + descripcion;
    }
}
