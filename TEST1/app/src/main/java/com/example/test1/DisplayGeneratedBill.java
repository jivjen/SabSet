package com.example.test1;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayGeneratedBill extends AppCompatActivity {
    ArrayList<Bill> billList = new ArrayList<>();
    HashMap<String,Double> ultimateBill = new HashMap<>();
    ProgressDialog mProgress;
    LinearLayout displayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_generated_bill);
        displayList = findViewById(R.id.generatedList);
        mProgress = new ProgressDialog(DisplayGeneratedBill.this);
        mProgress.setTitle("Setting up your bill");
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        Intent received = getIntent();
        Bundle args = received.getBundleExtra("Bundle");
        billList = (ArrayList<Bill>) args.getSerializable("BillList");
        mProgress.show();
        for (Bill bill : billList) {

            ArrayList<String> products = bill.getProducts();
            ArrayList<String> quantities = bill.getQuantities();
            for (int i = 0; i < products.size(); i++) {
                if (!ultimateBill.containsKey(products.get(i).toUpperCase())) {
                    ultimateBill.put(products.get(i).toUpperCase(), Double.parseDouble(quantities.get(i)));
                } else {
                    ultimateBill.put(products.get(i).toUpperCase(), ultimateBill.get(products.get(i).toUpperCase()) + Double.parseDouble(quantities.get(i)));

                }
            }
        }

            for (Map.Entry mapElement : ultimateBill.entrySet()) {
                String key = (String) mapElement.getKey();

                // Add some bonus marks
                // to all the students and print it
                double value = (double) mapElement.getValue();
                fillRow(key, value);
            }
            mProgress.dismiss();
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                @Override
                public void handleOnBackPressed() {

                    Intent hom = new Intent(DisplayGeneratedBill.this, HomePage.class);
                    hom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(hom);
                    finish();
                }
            };
            this.getOnBackPressedDispatcher().addCallback(this, callback);
        }

        void fillRow(String prod, double qty)
        {
            final View itemView = getLayoutInflater().inflate(R.layout.display_row,null,false);

            TextView editText = (TextView) itemView.findViewById(R.id.itemDisplayName);
            TextView qtay= (TextView)itemView.findViewById(R.id.displayQty);

            editText.setText(prod);
            qtay.setText(String.valueOf(qty));

            displayList.addView(itemView);

        }







}