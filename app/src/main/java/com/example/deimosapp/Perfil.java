package com.example.deimosapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Perfil extends Fragment {

    private EditText etNombre, etCorreo, etContrasena;
    private Button btnGuardar;

    private Base base; // Instancia de la base de datos

    // Interfaz para la comunicación con la actividad
    public interface OnPerfilUpdatedListener {
        void onPerfilUpdated(String nuevoNombre, String nuevoCorreo);
    }

    private OnPerfilUpdatedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPerfilUpdatedListener) {
            listener = (OnPerfilUpdatedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPerfilUpdatedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar los EditText y el Button
        etNombre = view.findViewById(R.id.etNombre);
        etCorreo = view.findViewById(R.id.etCorreo);
        etContrasena = view.findViewById(R.id.etContrasena);
        btnGuardar = view.findViewById(R.id.btnGuardar);

        // Inicializar la instancia de la base de datos
        base = new Base(getActivity(), "administracion", null, 1);  // Asegúrate de que "Base" esté configurado correctamente

        // Cargar datos actuales en los campos
        cargarDatos();

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener(v -> guardarCambios());

        return view;
    }

    private void cargarDatos() {
        // Obtener los datos de SharedPreferences
        SharedPreferences prefs = getActivity().getSharedPreferences("DeimosPrefs", Context.MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Usuario");
        String correo = prefs.getString("correo", "correo@example.com");
        String contrasena = prefs.getString("contrasena", "");

        // Cargar los datos en los campos EditText
        etNombre.setText(nombre);
        etCorreo.setText(correo);
        etContrasena.setText(contrasena);
    }

    private void guardarCambios() {
        SharedPreferences prefs = getActivity().getSharedPreferences("DeimosPrefs", Context.MODE_PRIVATE);

        // Obtener los nuevos valores de los EditText
        String nuevoNombre = etNombre.getText().toString();
        String nuevoCorreo = etCorreo.getText().toString();
        String nuevaContrasena = etContrasena.getText().toString();

        // Validar que los campos no estén vacíos
        if (nuevoNombre.isEmpty() || nuevoCorreo.isEmpty() || nuevaContrasena.isEmpty()) {
            Toast.makeText(getActivity(), "Todos los campos deben ser completos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener la base de datos
        SQLiteDatabase db = base.getWritableDatabase();

        // Crear un ContentValues para almacenar los nuevos datos
        ContentValues values = new ContentValues();
        values.put("nombre", nuevoNombre);
        values.put("correo", nuevoCorreo);
        values.put("contrasena", nuevaContrasena);

        // Obtener el idUsuario desde SharedPreferences
        int idUsuario = prefs.getInt("idUsuario", -1);
        if (idUsuario == -1) {
            Toast.makeText(getActivity(), "Error al obtener el ID de usuario", Toast.LENGTH_SHORT).show();
            db.close();
            return;
        }

        // Actualizar la tabla Usuario en la base de datos
        int filasActualizadas = db.update("Usuario", values, "idUsuario = ?", new String[]{String.valueOf(idUsuario)});

        // Verificar si la actualización fue exitosa
        if (filasActualizadas > 0) {
            // Actualizar los datos en SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre", nuevoNombre);
            editor.putString("correo", nuevoCorreo);
            editor.putString("contrasena", nuevaContrasena);
            editor.apply();

            // Mostrar un mensaje de éxito
            Toast.makeText(getActivity(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();

        } else {
            // Mostrar un mensaje de error si no se pudo actualizar
            Toast.makeText(getActivity(), "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
        }

        // Cerrar la base de datos
        db.close();

        if (listener != null) {
            listener.onPerfilUpdated(nuevoNombre, nuevoCorreo);
        }
    }
}
