package com.example.deimosapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Base extends SQLiteOpenHelper {

    public Base(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Usuario (" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo TEXT UNIQUE NOT NULL, " +
                "contrasena TEXT NOT NULL" +
                ");");

        // Crear la tabla de Obras con la columna urlImagen
        db.execSQL("CREATE TABLE Obra (" +
                "idObra INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "autor TEXT NOT NULL, " +
                "descripcion TEXT NOT NULL, " +
                "urlImagen TEXT" + // Agregar la columna de la URL de la imagen
                ");");

        // Crear la tabla de relación UsuarioObra
        db.execSQL("CREATE TABLE UsuarioObra (" +
                "idUsuario INTEGER, " +
                "idObra INTEGER, " +
                "FOREIGN KEY(idUsuario) REFERENCES Usuario(idUsuario), " +
                "FOREIGN KEY(idObra) REFERENCES Obra(idObra)" +
                ");");

        insertarObra("obra 1", "Juanito", "bonito no?", "https://i0.wp.com/puppis.blog/wp-content/uploads/2022/02/abc-cuidado-de-los-gatos-min.jpg?resize=521%2C346&ssl=1");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si se cambia la versión, eliminamos las tablas y las recreamos
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        db.execSQL("DROP TABLE IF EXISTS Obra");
        db.execSQL("DROP TABLE IF EXISTS UsuarioObra");
        onCreate(db);
    }

    public void insertarObra(String titulo, String autor, String descripcion, String urlImagen) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insertar los datos en la tabla de Obras
        db.execSQL("INSERT INTO Obra (titulo, autor, descripcion, urlImagen) VALUES (?, ?, ?, ?)",
                new Object[]{titulo, autor, descripcion, urlImagen});

        db.close();
    }

}
