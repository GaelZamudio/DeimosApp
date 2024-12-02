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

public class PlanetasFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private ItemAdapter adapter;
    private List<Item> lista;
    Button btnVolver;

    public PlanetasFragment() {
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
        lista = new ArrayList<>();
        lista.add(new Item("Júpiter", R.drawable.jupiter, "Júpiter es el planeta más grande de nuestro sistema solar. Es un gigante gaseoso compuesto principalmente de hidrógeno y helio. Tiene una famosa gran mancha roja, una tormenta masiva que ha estado ocurriendo durante siglos."));
        lista.add(new Item("Saturno", R.drawable.saturno, "Saturno es conocido por sus impresionantes anillos, que están formados por partículas de hielo y roca. Es el segundo planeta más grande del sistema solar y un gigante gaseoso, similar a Júpiter."));
        lista.add(new Item("Venus", R.drawable.venus, "Saturno es conocido por sus impresionantes anillos, que están formados por partículas de hielo y roca. Es el segundo planeta más grande del sistema solar y un gigante gaseoso, similar a Júpiter."));

        // Crea un adaptador con la lista de estrellas y se lo asigna al ListView
        adapter = new ItemAdapter(getActivity(), lista);
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
