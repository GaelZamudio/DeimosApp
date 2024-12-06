package com.example.deimosapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.Context.MODE_PRIVATE;

public class Galeria extends Fragment implements View.OnClickListener {
Button btnEstrellas, btnPlanetas, btnGalaxias;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_galeria, container, false);
        Base admin = new Base(getContext(), "administracion", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        btnEstrellas = view.findViewById(R.id.btnEstrellas);
        btnPlanetas = view.findViewById(R.id.btnPlanetas);
        btnGalaxias = view.findViewById(R.id.btnGalaxias);

        btnPlanetas.setOnClickListener(this);
        btnEstrellas.setOnClickListener(this);
        btnGalaxias.setOnClickListener(this);

        SharedPreferences preferences = getContext().getSharedPreferences("DeimosPrefs", MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", -1);
        String nombre = preferences.getString("nombre", "");
        String contrasena = preferences.getString("contrasena", "");
        String correo = preferences.getString("correo", "");
        TextView saludo = view.findViewById(R.id.saludo);
        //saludo.setText("Tu usuario es: "+nombre + ", tu correo es: "+correo+" y tu contraseña es: "+contrasena);
        saludo.setText("");

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnEstrellas) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EstrellasFragment()).commit();
        } else if (v.getId() == R.id.btnPlanetas) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PlanetasFragment()).commit();
        } else if (v.getId() == R.id.btnGalaxias){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GalaxiasFragment()).commit();
        }
    }
}