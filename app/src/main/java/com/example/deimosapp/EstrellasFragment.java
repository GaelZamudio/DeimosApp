package com.example.deimosapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class EstrellasFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private ItemAdapter adapter;
    private List<Item> listaEstrellas;
    Button btnVolver;

    public EstrellasFragment() {
        // Requiere un constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla la vista del fragmento
        View rootView = inflater.inflate(R.layout.fragment_estrellas, container, false);

        // Inicializa el ListView
        listView = rootView.findViewById(R.id.listView);

        btnVolver = rootView.findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(this);

        // Crea una lista de objetos Estrella
        listaEstrellas = new ArrayList<>();
        listaEstrellas.add(new Item("Sirius", R.drawable.sirius, "Sirius es la estrella más brillante del cielo nocturno y se encuentra en la constelación del Can Mayor. Conocida también como la \"Estrella del Perro\", su brillantez es tan intensa que ha sido un símbolo de guía y luz a lo largo de la historia en diversas culturas."));
        listaEstrellas.add(new Item("Betelgeuse", R.drawable.betelgeuse, "Betelgeuse es una supergigante roja ubicada en la constelación de Orión. A pesar de ser una estrella distante, su tamaño masivo y su tono rojizo la hacen muy visible. Es famosa por ser una de las estrellas que podría explotar en una supernova en el futuro cercano."));
        listaEstrellas.add(new Item("Antares", R.drawable.antares, "Antares es una supergigante roja situada en la constelación de Escorpio. Es una de las estrellas más brillantes en su región del cielo y se la conoce como \"el corazón de Escorpio\" debido a su ubicación en la constelación."));

        // Crea un adaptador con la lista de estrellas y se lo asigna al ListView
        adapter = new ItemAdapter(getActivity(), listaEstrellas);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnVolver){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Galeria()).commit();
        }
    }
}
