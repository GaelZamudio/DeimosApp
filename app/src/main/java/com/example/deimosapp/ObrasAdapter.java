package com.example.deimosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.deimosapp.Obra;

import java.util.List;

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

        Obra item = items.get(position);

        Glide.with(context).load(item.getImageUri()).into(imageView);
        textView.setText(item.getText());

        return convertView;
    }
}