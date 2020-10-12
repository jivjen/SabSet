package com.example.test1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ItemListAdapter extends ArrayAdapter<Item> {

    private Context context;
    int resource;
    public ItemListAdapter(Context context, int resource, ArrayList<Item> items)
    {
        super(context,resource,items);
        context = context;
        resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        Double quantity = getItem(position).getQuantity();

        Item item = new Item(name, quantity);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        EditText ename = (EditText) convertView.findViewById(R.id.name);
        EditText eqty  = (EditText) convertView.findViewById(R.id.quantity);

        ename.setText(name);
        ename.setText(quantity.toString());

        return convertView;
    }
}
