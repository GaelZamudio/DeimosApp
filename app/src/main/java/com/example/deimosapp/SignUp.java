package com.example.deimosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    Button btnRegister;
    EditText etUsername;
    EditText etPassword;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnLogin = findViewById(R.id.btnIrAIniciarSesion);
        btnRegister = findViewById(R.id.btnCrearCuenta);
        etUsername = findViewById(R.id.nombreSignUp);
        etPassword = findViewById(R.id.contrasenaSignUp);
        etEmail = findViewById(R.id.correoSignUp);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String cadenita = ((Button) v).getText().toString();
        if (cadenita.equals("Iniciar Sesión")) {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        }

        if (cadenita.equals("Crear Cuenta")) {
            if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                String email = etEmail.getText().toString().trim();
                if (!validarCorreo(email)) {
                    Toast.makeText(this, "Por favor, ingresa un correo válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                Base dbHelper = new Base(this, "administracion", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                ContentValues newUser = new ContentValues();
                newUser.put("nombre", username);
                newUser.put("correo", email);
                newUser.put("contrasena", password);

                Cursor usernameCursor = db.rawQuery("SELECT nombre FROM Usuario WHERE nombre = ?", new String[]{username});
                if (usernameCursor.moveToFirst()) {
                    Toast.makeText(this, "Este nombre está ocupado", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor emailCursor = db.rawQuery("SELECT correo FROM Usuario WHERE correo = ?", new String[]{email});
                    if (emailCursor.moveToFirst()) {
                        Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                    } else {
                        db.insert("Usuario", null, newUser);

                        Toast.makeText(this, "Se creó la cuenta", Toast.LENGTH_SHORT).show();

                        Cursor userCursor = db.rawQuery("SELECT idUsuario FROM Usuario WHERE correo = ?", new String[]{email});
                        if (userCursor.moveToFirst()) {
                            int idUsuario = userCursor.getInt(0);

                            SharedPreferences prefs = getSharedPreferences("DeimosPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("idUsuario", idUsuario);
                            editor.apply();
                        }
                        userCursor.close();

                        db.close();

                        Intent mainIntent = new Intent(this, Principal.class);
                        startActivity(mainIntent);
                    }
                }
            }
        }
    }

    private boolean validarCorreo(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,}$";
        return email.matches(emailPattern);
    }
}
