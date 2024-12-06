package com.example.deimosapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class Obras extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private List<Obra> Obras = new ArrayList<>();
    private ObrasAdapter adapter;
    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obras, container, false);

        listView = view.findViewById(R.id.listViewObras);
        adapter = new ObrasAdapter(getContext(), Obras);
        listView.setAdapter(adapter);

        // Cargar obras desde la base de datos
        cargarObrasDesdeBase();

        // Botón para seleccionar imagen desde la galería
        Button selectImageButton = view.findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        // Botón para subir imagen desde URL
        Button uploadFromUrlButton = view.findViewById(R.id.uploadFromUrlButton);
        uploadFromUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrlDialog();
            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {

    }

    // Método para manejar la imagen seleccionada de la galería
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            promptForTextAndAddItem(imageUri);
        }
    }

    // Método para abrir el diálogo para ingresar la URL
    private void openUrlDialog() {
        final EditText urlInput = new EditText(getActivity());
        urlInput.setHint("https://example.com/image.jpg");

        new AlertDialog.Builder(getActivity())
                .setTitle("Ingresar URL de la imagen")
                .setView(urlInput)
                .setPositiveButton("Cargar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = urlInput.getText().toString();
                        Uri imageUri = Uri.parse(url);
                        promptForTextAndAddItem(imageUri);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // Método para solicitar texto y agregar el ítem a la lista
    private void promptForTextAndAddItem(final Uri imageUri) {
        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_obra, null);
        final EditText tituloInput = dialogView.findViewById(R.id.tituloInput);
        final EditText descripcionInput = dialogView.findViewById(R.id.descripcionInput);

        new AlertDialog.Builder(getActivity())
                .setTitle("Añadir obra")
                .setView(dialogView)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String titulo = tituloInput.getText().toString();
                        SharedPreferences prefs = getActivity().getSharedPreferences("DeimosPrefs", Context.MODE_PRIVATE);
                        String autor = prefs.getString("nombre","Usuario");
                        String descripcion = descripcionInput.getText().toString();
                        String urlImagen = imageUri.toString();

                        // Insertar en la base de datos
                        Base dbHelper = new Base(getContext(), "administracion", null, 1);
                        try {
                            dbHelper.insertarObra(titulo, autor, descripcion, urlImagen);

                            // Recargar las obras desde la base
                            cargarObrasDesdeBase();
                        } catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    private void cargarObrasDesdeBase() {
        Obras.clear(); // Limpia la lista antes de cargar

        Base dbHelper = new Base(getContext(), "administracion", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String query = "SELECT titulo, autor, descripcion, urlImagen FROM Obra";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    String titulo = cursor.getString(0);
                    String autor = cursor.getString(1);
                    String descripcion = cursor.getString(2);
                    String urlImagen = cursor.getString(3);

                    // Crea una nueva obra y la agrega a la lista
                    Obras.add(new Obra(Uri.parse(urlImagen), titulo, autor, descripcion));
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error para el diagnóstico
        } finally {
            db.close();
        }

        adapter.notifyDataSetChanged(); // Refresca la lista
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarObrasDesdeBase(); // Llama al método para cargar las obras desde la base de datos
    }


}
