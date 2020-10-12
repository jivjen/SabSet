package com.example.test1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.textract.AmazonTextractClient;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.services.textract.model.Geometry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class AWSIntentService extends IntentService {
    FirebaseAuth mAuth;
    StorageReference folder;
    public static final String BROADCAST_ACTION = "com.example.test1:NETWORK_CALL_BROADCAST";
    public static final String RESULT = "com.example.test1:NETWORK_CALL_RESULT";
    HashMap<String, String> productPrice = new HashMap<String, String>();
    ArrayList<String> products = new ArrayList<String>();
    ArrayList<String> quantities = new ArrayList<String>();
    public AWSIntentService() {
        super(AWSIntentService.class.getSimpleName());
    }

    public AWSIntentService(String name) {
        super(AWSIntentService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //byte[] dataa = intent.getByteArrayExtra("dataa");
        String iimage = intent.getExtras().getString("image");
        Intent newintent = new Intent(BROADCAST_ACTION);
        String storeName = intent.getExtras().getString("storeName");
        String purchaseDate = intent.getExtras().getString("purchaseDate");

        newintent.putExtra(RESULT, "some_result");//and more results

        Uri image = Uri.parse(iimage);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String ph = user.getPhoneNumber();
        String t = String.valueOf(timestamp.getTime());
        folder = FirebaseStorage.getInstance().getReference().child("ImageFolders/" + ph);
        StorageReference name = folder.child(ph + t);

        String billName = ph + t;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

        name.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                            DatabaseReference imageStore = FirebaseDatabase.getInstance().getReference().child("BillImages/" + ph + "/" + billName);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("imageurl", String.valueOf(uri));
                            imageStore.setValue(hashMap);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AlertDialog alertDialog = new AlertDialog.Builder(AWSIntentService.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("An error occurred during uploading. Kindly connect to the internet and try again");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(AWSIntentService.this, HomePage.class);
                                        dialog.dismiss();
                                        startActivity(i);

                                    }
                                });
                        alertDialog.show();
                    }
                });

            }
        });
        String month = purchaseDate.substring(3,purchaseDate.length());
        DatabaseReference billStore = FirebaseDatabase.getInstance().getReference().child("BillsSortedByMonth/" + ph + "/" + month + "/" + billName);
        DatabaseReference storeStore = FirebaseDatabase.getInstance().getReference().child("BillsSortedByStore/"+ph+"/"+storeName+"/"+billName);
        Bill nb = new Bill("no",billName,ph,storeName, purchaseDate, products, quantities);
        billStore.setValue(nb);
        storeStore.setValue(nb);
        DatabaseReference bills = FirebaseDatabase.getInstance().getReference().child("BillsOfUsers/"+ph+ "/"+billName);
        bills.setValue(nb);
        LocalBroadcastManager.getInstance(this).sendBroadcast(newintent);

    }

//
//    private class Storage extends AsyncTask<String, Integer, String> {
//        FirebaseAuth mAuth;
//        ProgressDialog mProgress;
//        StorageReference folder;
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String iimage = strings[0];
//            String store = strings[1];
//            String purchaseDate = strings[2].replace("/","-");
//
//            Uri image = Uri.parse(iimage);
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            FirebaseUser user = mAuth.getInstance().getCurrentUser();
//            String ph = user.getPhoneNumber();
//            String t = String.valueOf(timestamp.getTime());
//            folder = FirebaseStorage.getInstance().getReference().child("ImageFolders/" + ph);
//            StorageReference name = folder.child(ph + t);
//
//            String billName = ph + t;
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
//            Date date = new Date();
//            String datestr = formatter.format(date);
//            DatabaseReference imageStore = FirebaseDatabase.getInstance().getReference().child("Bill-Sorted-By-Dates/" + ph + "/" + datestr);
//
//
//            name.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
////                            DatabaseReference imageStore = FirebaseDatabase.getInstance().getReference().child("BillImages/" + ph + "/" + billName);
////                            HashMap<String, String> hashMap = new HashMap<>();
////                            hashMap.put("imageurl", String.valueOf(uri));
////                            imageStore.setValue(hashMap);
////
////                            DatabaseReference billStore = FirebaseDatabase.getInstance().getReference().child("BillsSortedByDate/" + ph + "/" + purchaseDate + "/" + billName);
////                            DatabaseReference storeStore = FirebaseDatabase.getInstance().getReference().child("BillsSortedByStore/"+ph+"/"+store+"/"+billName);
////
////
////                            Bill nb = new Bill(store, purchaseDate, products, prices);
////                            billStore.setValue(nb);
////                            storeStore.setValue(nb);
////                            DatabaseReference bills = FirebaseDatabase.getInstance().getReference().child("BillsOfUsers/"+ph+ "/"+billName);
////                            bills.setValue(nb);
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            AlertDialog alertDialog = new AlertDialog.Builder(AWSIntentService.this).create();
//                            alertDialog.setTitle("Error");
//                            alertDialog.setMessage("An error occurred during uploading. Kindly connect to the internet and try again");
//                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent i = new Intent(AWSIntentService.this, HomePage.class);
//                                            dialog.dismiss();
//                                            startActivity(i);
//
//                                        }
//                                    });
//                            alertDialog.show();
//                        }
//                    });
//
//                }
//            });
//
//
//            return "null";
//        }
//    }
}
