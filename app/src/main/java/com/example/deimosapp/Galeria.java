package com.example.deimosapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.Context.MODE_PRIVATE;

public class Galeria extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_galeria, container, false);
        Base admin = new Base(getContext(), "administracion", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();


        SharedPreferences preferences = getContext().getSharedPreferences("DeimosPrefs", MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", -1);
        String nombre = preferences.getString("nombre", "");
        String contrasena = preferences.getString("contrasena", "");
        String correo = preferences.getString("correo", "");
        TextView saludo = view.findViewById(R.id.saludo);
        saludo.setText("Tu usuario es: "+nombre + ", tu correo es: "+correo+" y tu contraseña es: "+contrasena);

        // Inflate the layout for this fragment
        return view;
    }
}