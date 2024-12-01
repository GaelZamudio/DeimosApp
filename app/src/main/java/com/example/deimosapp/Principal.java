package com.example.deimosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import com.example.deimosapp.Base;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Base admin = new Base(this, "administracion", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();


        SharedPreferences preferences = getSharedPreferences("DeimosPrefs", MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", -1);

        if (idUsuario != -1){
            Cursor cursor = base.rawQuery("SELECT nombre FROM Usuario WHERE idUsuario = ?", new String[]{String.valueOf(idUsuario)});
            if (cursor.moveToFirst()){
                String nombreUsuario = cursor.getString(0);
                TextView saludo = findViewById(R.id.saludo);
                saludo.setText("Tu usuario es: "+nombreUsuario);
            }
            cursor.close();
        }
        base.close();

    }
}