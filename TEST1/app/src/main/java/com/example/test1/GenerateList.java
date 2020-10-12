package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class GenerateList extends AppCompatActivity implements BillAdapter.OnBillListener {
    FirebaseAuth mAuth;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    ArrayList<Bill> billList = new ArrayList<>();
    ProgressDialog mProgress;
    RecyclerView recyclerBills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_purchased);
        mProgress = new ProgressDialog(GenerateList.this);
        mProgress.setTitle("Loading");
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        recyclerBills = findViewById(R.id.recycler_bills);
        mProgress.show();
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        String ph = user.getPhoneNumber();
//         = new Date();
        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        int month = (cal.get(Calendar.MONTH)+1) - 1;
        int year = cal.get(Calendar.YEAR);
        Log.e("CALENDAR MONTH",String.valueOf(month));
        if(month == 0)
        {
            month = 12;
            year = year - 1;
        }

        String mon = String.valueOf(month);
        String actmon;
        actmon = mon;
        if(mon.length()==1)
        {
            actmon = "0"+mon;
        }
        String actyear = String.valueOf(year).substring(2,4);




        DatabaseReference ref = db.getReference().child("BillsSortedByMonth/" + ph +"/"+ actmon+"-"+actyear);
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
                            Bill asd = new Bill((String)billData.get("processed"),(String)billData.get("billName"),(String)billData.get("ph"),(String) billData.get("storeName"), (String) billData.get("date"), aa, bb);
                            if(asd.getProcessed().equals("yes")) {
                                billList.add(asd);
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
//                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
//                    recyclerBills.setLayoutManager(layoutManager);
//                    BillAdapter bb = new BillAdapter(billList,GenerateList.this);
//                    recyclerBills.setAdapter(bb);

                    mProgress.dismiss();
                     if(billList.size()==0)
                    {
                        Intent g = new Intent(GenerateList.this, ComingSoon.class);
                        startActivity(g);
                    }
                    else {
                        Intent show = new Intent(GenerateList.this, DisplayGeneratedBill.class);
                        Bundle args = new Bundle();
                        args.putSerializable("BillList", (Serializable) billList);
                        show.putExtra("Bundle", args);
                        startActivity(show);
                    }
                }
                else{
                    Intent g = new Intent(GenerateList.this, ComingSoon.class);
                    startActivity(g);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    @Override
    public void onBillClick(int position) {
        Bill selectedBill =  billList.get(position);
        Log.e("SELECETEDEEE",selectedBill.getStoreName());
        Intent ii = new Intent(GenerateList.this, DisplayBill.class);
        ii.putExtra("SelectedBill",selectedBill);
        startActivity(ii);
    }

    @Override
    public void onDeleteClick(int position) {
        Bill selectedBill =  billList.get(position);
        Log.e("SELECTED BILL", selectedBill.getStoreName());
    }
}
