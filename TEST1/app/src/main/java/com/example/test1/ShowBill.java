package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ShowBill extends AppCompatActivity implements View.OnClickListener{
    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;
    String storeName;
    String ph;
    String billName;
    String purchaseDate;
    ArrayList<String> products = new ArrayList<>();
    ArrayList<String> qty = new ArrayList<>();
    ScrollView sc;
    private ProgressDialog mProgress;
    boolean manual;
    TextView dd;
    TextView stto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        mProgress = new ProgressDialog(ShowBill.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mProgress.setTitle("Uploading Bill...");
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        layoutList = findViewById(R.id.layout_list);
       sc = findViewById(R.id.scroll);
        stto = findViewById(R.id.manualStoreName);
        dd = findViewById(R.id.manualDate);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);
        manual = getIntent().getExtras().getBoolean("manual");
        if(!manual) {
            fillView();

        }
        else{
            storeName = getIntent().getExtras().getString("storeName");
            purchaseDate = getIntent().getExtras().getString("billDate");
            stto.setText("Store Name : "+storeName);
            dd.setText("Purchase Date : "+purchaseDate);
            addView();
        }
        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);


    }

    private void fillView(){
        Bill bill = (Bill) getIntent().getSerializableExtra("SelectedBill");
        products = bill.getProducts();
        qty = bill.getQuantities();
        storeName = bill.getStoreName();
        purchaseDate = bill.getDate();
        if(products == null || qty == null)
        {
            Log.e("CHAGGG","CHAGA");
            Toast.makeText(ShowBill.this,"Bill yet to be processed", Toast.LENGTH_LONG).show();
            finish();
        }
        billName = bill.getBillName();
        ph = bill.getPh();

        stto.setText("Store Name : "+storeName);
        dd.setText("Purchase Date : "+purchaseDate);
        for( int i = 0 ;i<products.size();i++)
        {
            initView(products.get(i), qty.get(i));
        }
    }

    private void initView(String pdt, String qt){
        final View itemView = getLayoutInflater().inflate(R.layout.row_add,null,false);

        EditText editText = (EditText)itemView.findViewById(R.id.edit_itemName);
        EditText qtay= (EditText)itemView.findViewById(R.id.edit_qty);

        ImageView imageClose = (ImageView)itemView.findViewById(R.id.image_remove);

        editText.setText(pdt);
        qtay.setText(qt);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                products.remove(editText.getText().toString());
                qty.remove(qtay.getText().toString());
                layoutList.removeView(itemView);
            }
        });

        layoutList.addView(itemView);

        sc.post(new Runnable() {
            @Override
            public void run() {
                sc.fullScroll(ScrollView.FOCUS_DOWN);
                editText.setFocusable(true);
                editText.requestFocus();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.button_add:

                addView();
                break;

            case R.id.button_submit_list:
                ArrayList<String> refinedPdt = new ArrayList<>();
                ArrayList<String> refinedQty = new ArrayList<>();
                for(int i = 0 ;i<layoutList.getChildCount();i++)
                {
                    View itm = layoutList.getChildAt(i);

                    EditText a = itm.findViewById(R.id.edit_itemName);
                    EditText b = itm.findViewById(R.id.edit_qty);
                    if(!a.getText().toString().equals("") && !b.getText().toString().equals(""))
                    {
                        refinedPdt.add(a.getText().toString().trim());
                        refinedQty.add(b.getText().toString().trim());
                    }
                }
                if(manual)
                {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                    ph = user.getPhoneNumber();
                    String t = String.valueOf(timestamp.getTime());
                    billName = ph+t;
                    uploadDetails("yes",billName,ph,storeName,purchaseDate,refinedPdt,refinedQty);
                }
                else {
                    uploadDetails("yes",billName, ph, storeName, purchaseDate, refinedPdt, refinedQty);
                }
                break;

        }

    }



    private void addView() {

        final View itemView = getLayoutInflater().inflate(R.layout.row_add,null,false);

        EditText editText = (EditText)itemView.findViewById(R.id.edit_itemName);
        EditText qtay= (EditText)itemView.findViewById(R.id.edit_qty);

        ImageView imageClose = (ImageView)itemView.findViewById(R.id.image_remove);



        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("REMOVER",editText.getText().toString());
                Log.e("REMOVER",qtay.getText().toString());
                Log.e("REMOVER","BEFORE"+String.valueOf(products.size()));
                products.remove(editText.getText().toString());
                qty.remove(qtay.getText().toString());
                Log.e("REMOVER","AFTER"+String.valueOf(products.size()));
                layoutList.removeView(itemView);
            }
        });

//        editText.setFocusableInTouchMode(true);
//        editText.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        layoutList.addView(itemView);
        sc.post(new Runnable() {
            @Override
            public void run() {
                sc.fullScroll(ScrollView.FOCUS_DOWN);
                editText.setFocusable(true);
                editText.requestFocus();
            }
        });



    }



    private void uploadDetails(String processed,String billName, String ph,String store, String purchaseDate, ArrayList<String> products, ArrayList<String> qties){
        if(products.size()>0) {
            Intent serviceIntent = new Intent(this, FirebaseUploadService.class);
            Log.e("JIGA", store + " " + purchaseDate);
            serviceIntent.putExtra("processed", processed);
            serviceIntent.putExtra("billName", billName);
            serviceIntent.putExtra("ph", ph);
            serviceIntent.putExtra("storeName", store);
            serviceIntent.putExtra("purchaseDate", purchaseDate);
            serviceIntent.putStringArrayListExtra("products", products);
            serviceIntent.putStringArrayListExtra("quantity", qties);
            startService(serviceIntent);
            mProgress.show();
        }
        else{
            new android.app.AlertDialog.Builder(ShowBill.this)
                    .setTitle("Empty Bill")
                    .setMessage("Please enter at least 1 item!")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    private BroadcastReceiver mNetworkCallReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            mProgress.dismiss();

            String result = intent.getStringExtra(AWSIntentService.RESULT);


            //process results and continue program(go to next screen, show error message etc.)
            Toast.makeText(ShowBill.this,"Successfully uploaded", Toast.LENGTH_LONG).show();
            Intent ii = new Intent(ShowBill.this, HomePage.class);
            ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ii);
            finish();
    }};

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mNetworkCallReceiver,
                new IntentFilter(AWSIntentService.BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mNetworkCallReceiver);
    }

}