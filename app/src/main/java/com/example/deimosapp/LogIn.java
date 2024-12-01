package com.example.deimosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    Button btnRegister;
    Button btnSignIn;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnRegister = findViewById(R.id.btnCreateAccount);
        btnSignIn = findViewById(R.id.btnSignIn);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String cadenita = ((Button) v).getText().toString();
        if (cadenita.equals("Crear Cuenta")) {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }

        if (cadenita.equals("Iniciar Sesión")) {
            if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Base admin = new Base(this, "administracion", null, 1);
                SQLiteDatabase base = admin.getWritableDatabase();

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                Cursor cursor = base.rawQuery("SELECT idUsuario FROM Usuario WHERE nombre = ? AND contrasena = ?", new String[]{username, password});
                if (cursor.moveToFirst()) {
                    int idUsuario = cursor.getInt(0);

                    SharedPreferences preferences = getSharedPreferences("InvenPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("idUsuario", idUsuario);
                    editor.apply();

                    Toast.makeText(this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();

                    base.close();
                    cursor.close();

                    Intent intent = new Intent(this, Principal.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    base.close();
                }
            }
        }
    }
}
