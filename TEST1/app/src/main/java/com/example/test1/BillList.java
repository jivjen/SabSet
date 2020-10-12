package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class BillList extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        listView = findViewById(R.id.listView);


        Item i1 = new Item("Rice",1.00);
        Item i2 = new Item("Rice",1.00);
        Item i3 = new Item("Rice",1.00);
        Item i4 = new Item("Rice",1.00);
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(i1);
        itemList.add(i2);
        itemList.add(i3);
        itemList.add(i4);
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.adapter_view_layout, itemList);
        listView.setAdapter(adapter);
    }
}