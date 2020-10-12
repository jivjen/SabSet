package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ItemsPurchased extends AppCompatActivity implements BillAdapter.OnBillListener {
    ProgressDialog mProgress;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    ArrayList<Bill> billList = new ArrayList<Bill>();
    RecyclerView recyclerBills;
    FirebaseUser user;
    String ph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_purchased);
        mProgress = new ProgressDialog(ItemsPurchased.this);
        mProgress.setTitle("Loading");
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        recyclerBills = findViewById(R.id.recycler_bills);
        mProgress.show();

        user = mAuth.getInstance().getCurrentUser();
        ph = user.getPhoneNumber();
        DatabaseReference ref = db.getReference().child("BillsOfUsers/" + ph);
        Query q = ref.orderByValue();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    HashMap<String, Object> resultMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String date : resultMap.keySet()) {

                        Object data = resultMap.get(date);
                        try {

                            HashMap<String, Object> billData = (HashMap<String, Object>) data;

                            ArrayList<String> aa = (ArrayList<String>) billData.get("products");

                            ArrayList<String> bb = (ArrayList<String>) billData.get("quantities");
                            Bill asd = new Bill((String) billData.get("processed"), (String) billData.get("billName"), (String) billData.get("ph"), (String) billData.get("storeName"), (String) billData.get("date"), aa, bb);
                            billList.add(asd);

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                    recyclerBills.setLayoutManager(layoutManager);
                    Collections.sort(billList,(b1,b2)->{return b1.getBillName().substring(13).compareTo(b2.getBillName().substring(13));});
                    Collections.reverse(billList);
                    BillAdapter bb = new BillAdapter(billList, ItemsPurchased.this);
                    recyclerBills.setAdapter(bb);
                    mProgress.dismiss();
                } else {
                    mProgress.dismiss();
                    //Toast.makeText(ItemsPurchased.this, "No bills uploaded", Toast.LENGTH_LONG).show();
                    Intent hom = new Intent(ItemsPurchased.this, no_bills.class);
                    startActivity(hom);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBillClick(int position) {
        Bill selectedBill = billList.get(position);
        if (selectedBill.getProcessed().equals("yes")) {
            Intent ii = new Intent(ItemsPurchased.this, ShowBill.class);
            ii.putExtra("SelectedBill", selectedBill);
            startActivity(ii);
        } else {
            Toast.makeText(ItemsPurchased.this, "Bill not yet processed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteClick(int position) {


        new AlertDialog.Builder(this)
                .setTitle("Delete bill")
                .setMessage("Are you sure you want to delete this bill?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mProgress.show();
                        Bill selectedBill = billList.get(position);
                        DatabaseReference ref = db.getReference().child("BillsOfUsers/" + ph + "/" + selectedBill.getBillName());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ref.removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        DatabaseReference ref1 = db.getReference().child("BillImages/" + ph + "/" + selectedBill.getBillName());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ref1.removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference ref2 = db.getReference().child("BillsSortedByMonth/" + ph + "/" + selectedBill.getDate().substring(3) + "/" + selectedBill.getBillName());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ref2.removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference ref3 = db.getReference().child("BillsSortedByStore/" + ph + "/" + selectedBill.getStoreName() + "/" + selectedBill.getBillName());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ref3.removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        mProgress.dismiss();
                        Intent j = new Intent(ItemsPurchased.this, ItemsPurchased.class);
                        j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(j);
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

//    class BillComparator<Bill> implements Comparator<com.example.test1.Bill>{
//
//
//
//
//
//        @Override
//        public int compare(com.example.test1.Bill bill, com.example.test1.Bill t1) {
//            return Integer.parseInt(t1.getBillName().substring(13)) - Integer.parseInt(bill.getBillName().substring(13));
//        }
//    }
}

