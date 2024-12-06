package com.example.deimosapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.example.deimosapp.Obra;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class ObrasAdapter extends BaseAdapter {
    private Context context;
    private List<Obra> items;

    public ObrasAdapter(Context context, List<Obra> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_obra, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imagenObra);
        TextView textView = convertView.findViewById(R.id.tituloObra);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminar);

        Obra item = items.get(position);

        Glide.with(context).load(item.getImageUri()).into(imageView);
        textView.setText(item.getText());

        // Obtener el usuario actual
        SharedPreferences prefs = context.getSharedPreferences("DeimosPrefs", Context.MODE_PRIVATE);
        String usuarioActual = prefs.getString("nombre", "Usuario");

        // Mostrar el botón de eliminar solo si el usuario actual es el autor
        if (usuarioActual.equals(item.getAutor())) {
            btnEliminar.setVisibility(View.VISIBLE); // Muestra el botón
        } else {
            btnEliminar.setVisibility(View.GONE); // Oculta el botón
        }

        btnEliminar.setOnClickListener(v -> {
            if (usuarioActual.equals(item.getAutor())) {
                // Eliminar obra de la base de datos
                Base dbHelper = new Base(context, "administracion", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                try {
                    // Eliminar la obra de la base de datos
                    int rowsDeleted = db.delete("Obra", "titulo=? AND autor=?", new String[]{item.getTitulo(), usuarioActual});

                    if (rowsDeleted > 0) {
                        // Solo eliminamos del ArrayList si la eliminación fue exitosa en la BD
                        items.remove(position); // Elimina del ArrayList
                        notifyDataSetChanged(); // Refresca la lista

                        // Recargar la lista de obras desde la base de datos
                        cargarObrasDesdeBase();

                        Toast.makeText(context, "Obra eliminada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al eliminar: No se encontró la obra", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    db.close();
                }
            } else {
                Toast.makeText(context, "No puedes eliminar esta obra", Toast.LENGTH_SHORT).show();
            }
        });




        return convertView;
    }

    public void cargarObrasDesdeBase() {
        Base dbHelper = new Base(context, "administracion", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Obra> obras = new ArrayList<>();

        Cursor cursor = db.query("Obra", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String titulo = cursor.getString(0);
                String autor = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String urlImagen = cursor.getString(3);

                // Crea una nueva obra y la agrega a la lista
                obras.add(new Obra(Uri.parse(urlImagen), titulo, autor, descripcion));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Actualiza el ArrayList de obras
        this.items.clear();
        this.items.addAll(obras);
        notifyDataSetChanged(); // Refresca la lista
    }


}