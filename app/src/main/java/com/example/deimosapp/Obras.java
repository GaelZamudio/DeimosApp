package com.example.deimosapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Obras extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private List<Obra> Obras = new ArrayList<>();
    private ObrasAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obras, container, false);

        ListView listView = view.findViewById(R.id.listViewObras);

        adapter = new ObrasAdapter(getContext(), Obras);
        listView.setAdapter(adapter);

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
        final EditText editText = new EditText(getContext());
        new AlertDialog.Builder(getActivity())
                .setTitle("Añadir texto")
                .setView(editText)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString();
                        Obras.add(new Obra(imageUri, text));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
