package com.example.deimosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        Item item = items.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(item.getImagenId());

        TextView nameText = convertView.findViewById(R.id.nameText);
        nameText.setText(item.getNombre());

        TextView descriptionText = convertView.findViewById(R.id.descriptionText);
        descriptionText.setText(item.getDescripcion());

        return convertView;
    }
}
