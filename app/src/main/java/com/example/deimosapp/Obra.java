package com.example.deimosapp;

import android.net.Uri;

public class Obra {
    private Uri imageUri;
    private String text;

    public Obra(Uri imageUri, String text) {
        this.imageUri = imageUri;
        this.text = text;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getText() {
        return text;
    }
}
