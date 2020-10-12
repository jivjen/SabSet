package com.example.test1;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class FirebaseUploadService extends IntentService {


    public static final String BROADCAST_ACTION = "com.example.test1:NETWORK_CALL_BROADCAST";
    public static final String RESULT = "com.example.test1:NETWORK_CALL_RESULT";

    public FirebaseUploadService(){
        super(FirebaseUploadService.class.getSimpleName());
    }

    public FirebaseUploadService(String name) {

            super(FirebaseUploadService.class.getSimpleName());
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String processed = intent.getExtras().getString("processed");
        String store = intent.getExtras().getString("storeName");
        String purchaseDate = intent.getExtras().getString("purchaseDate").replace("/","-");
        String month = purchaseDate.substring(3,purchaseDate.length());
        String ph = intent.getExtras().getString("ph");
        String billName = intent.getExtras().getString("billName");
        ArrayList<String> products = intent.getStringArrayListExtra("products");
        ArrayList<String> quantities = intent.getStringArrayListExtra("quantity");


        Intent newintent = new Intent(BROADCAST_ACTION);
         DatabaseReference billStore = FirebaseDatabase.getInstance().getReference().child("BillsSortedByMonth/" + ph + "/" + month + "/" + billName);
         DatabaseReference storeStore = FirebaseDatabase.getInstance().getReference().child("BillsSortedByStore/"+ph+"/"+store+"/"+billName);
         Bill nb = new Bill(processed,billName, ph, store, purchaseDate, products, quantities);
         billStore.setValue(nb);
         storeStore.setValue(nb);
         DatabaseReference bills = FirebaseDatabase.getInstance().getReference().child("BillsOfUsers/"+ph+ "/"+billName);
         bills.setValue(nb);

        newintent.putExtra(RESULT, "some_result");
        LocalBroadcastManager.getInstance(this).sendBroadcast(newintent);
    }
}
