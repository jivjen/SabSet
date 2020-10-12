package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayBill extends AppCompatActivity {
    LinearLayout displayList;
    ArrayList<String> products;
    ArrayList<String> quantities;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_bill);

        Bill bill = (Bill) getIntent().getSerializableExtra("SelectedBill");

        TextView store = findViewById(R.id.displayStoreName);
        TextView date = findViewById(R.id.displayPurchaseDate);
        store.setText("Store Name : " + bill.getStoreName());
        date.setText("Purchase Date : "+bill.getDate());

        displayList = findViewById(R.id.displayList);
        products = bill.getProducts();
        quantities = bill.getQuantities();

        mProgress = new ProgressDialog(DisplayBill.this);
        mProgress.setTitle("Uploading Bill...");
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        mProgress.show();

        for(int i = 0;i < products.size();i++)
        {

            fillRow((String)products.get(i), (String) quantities.get(i));
        }

        mProgress.dismiss();
    }

    void fillRow(String prod, String qty)
    {
        final View itemView = getLayoutInflater().inflate(R.layout.display_row,null,false);

        TextView editText = (TextView) itemView.findViewById(R.id.itemDisplayName);
        TextView qtay= (TextView)itemView.findViewById(R.id.displayQty);

        editText.setText(prod.toUpperCase());
        qtay.setText(qty);



        displayList.addView(itemView);

    }
}