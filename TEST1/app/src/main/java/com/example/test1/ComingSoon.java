package com.example.test1;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ComingSoon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Intent hom = new Intent(ComingSoon.this,HomePage.class);

                hom.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(hom);
                finish();

            }
        };

        this.getOnBackPressedDispatcher().addCallback(this, callback);

    }


}