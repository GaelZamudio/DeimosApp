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

public class GalaxiasFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private ItemAdapter adapter;
    private List<Item> lista;
    Button btnVolver;

    public GalaxiasFragment() {
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
        lista.add(new Item("Vía Láctea", R.drawable.vialactea, "La Vía Láctea es la galaxia en la que se encuentra nuestro sistema solar. Es una galaxia espiral gigante con un núcleo denso y varios brazos que se extienden desde su centro. Se estima que contiene entre 100 y 400 mil millones de estrellas."));
        lista.add(new Item("Andrómeda", R.drawable.andromeda, "La galaxia de Andrómeda es la más cercana a la Vía Láctea y es la galaxia espiral más grande del Grupo Local, un grupo de galaxias que incluye a la Vía Láctea. Se encuentra a aproximadamente 2.5 millones de años luz de distancia y es un destino futuro para nuestra galaxia, ya que se espera que ambas colisionen en unos 4.5 mil millones de años."));
        lista.add(new Item("Galaxia del Sombrero (M104)", R.drawable.m104, "La Galaxia del Sombrero es una galaxia espiral ubicada en la constelación de Virgo. Su forma peculiar, con un brillante anillo de polvo oscuro alrededor de su núcleo, le da la apariencia de un sombrero. Es una de las galaxias más estudiadas debido a su apariencia única."));

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
