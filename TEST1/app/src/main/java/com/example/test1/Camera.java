package com.example.test1;
import java.sql.Timestamp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Camera extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String text;
    String image_uri;
    Uri image;
    ImageView imageView;
    Button proceed;
    ArrayList<Rect> boxes;
    ArrayList<String> texts;
    StorageReference folder;
    FirebaseAuth mAuth;
    ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mProgress = new ProgressDialog(Camera.this);
        mProgress.setTitle("Uploading...");
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        //text = getIntent().getExtras().getString("Text");
        image_uri = getIntent().getExtras().getString("URI");
        image = Uri.parse(image_uri);
        //boxes = (ArrayList<Rect>)getIntent().getSerializableExtra("Box");
        imageView = findViewById(R.id.imageView);
        imageView.setImageURI(image);
        //texts = (ArrayList<String>)getIntent().getSerializableExtra("TextArray");
//        for(Rect box : boxes){
//            Log.e("BOXXZZ:",box.toString());
//        }
        proceed = findViewById(R.id.proceedButton);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                processText(text, texts);
//                uploadPic(image);
            }
        });



    }

    private void uploadPic(Uri image)
    {
        mProgress.show();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        FirebaseUser user= mAuth.getInstance().getCurrentUser();
        String ph = user.getPhoneNumber();
        folder = FirebaseStorage.getInstance().getReference().child("ImageFolders/"+ph);
        String t = String.valueOf(timestamp.getTime());
        StorageReference name = folder.child(ph+t);
        name.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference imageStore = FirebaseDatabase.getInstance().getReference().child("BillImages/"+ph);
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("imageurl",String.valueOf(uri));
                        imageStore.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mProgress.dismiss();
                                Toast.makeText(Camera.this, "Uploaded",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

    }

    private String processText(String text, ArrayList<String> textArray){
//        int i = text.indexOf("QTY");
//        if(i==-1)
//        {
//            i = text.indexOf("QUANTITY");
//        }
//        if(i==-1)
//        {
//            i = text.indexOf("Qty");
//        }
//        Log.e("SB",text.toString());
//        Log.e("Position of I QTY",String.valueOf(i));

        Log.e("ARRAY",textArray.toString());
        int j = textArray.indexOf("QTY");
        if(j==-1)
        {
            j = textArray.indexOf("QUANTITY");
        }
        if(j==-1)
        {
            j = textArray.indexOf("Qty");
        }
        Log.e("ARRAY --",textArray.toString());
        Log.e("Position of J QTY",String.valueOf(j));
        return "Hello";
    }
}