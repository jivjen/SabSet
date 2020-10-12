package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private String verificationCode;
    private Button login;
    private EditText name;
    private EditText number;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        name = findViewById(R.id.otp);
        number = findViewById(R.id.mobileNumber);
        login = findViewById(R.id.verifyButton);
        CheckBox ch=(CheckBox)findViewById(R.id.check);
        FirebaseUser user= mAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Log.e("USER INFO",user.getPhoneNumber());
            Intent intent = new Intent(LoginActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }
        TextView termsAndConditionText;
        termsAndConditionText=(TextView) findViewById(R.id.termsandconditiontext);
        //termsAndConditionText.setText(Html.fromHtml(String.format(getString(R.string.tandcString))));
        login.setVisibility(View.INVISIBLE);
        //termsAndConditionText.setPaintFlags(termsAndConditionText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        termsAndConditionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://drive.google.com/file/d/1BpIwkJpvYpKsNRMhi1-cimPj_ozE9Mil/view?usp=sharing"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Terms and Condition agreed", Toast.LENGTH_LONG).show();
                if (ch.isChecked()) {
                    login.setVisibility(View.VISIBLE);

                } else {
                    login.setVisibility(View.INVISIBLE);
                }
            }});

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String phone = number.getText().toString();

                //TO-DO Validation

                Intent verify= new Intent(LoginActivity.this,VerifyActivity.class);
                verify.putExtra("name", username);
                verify.putExtra("phone", phone);
                startActivity(verify);
            }
        });
    }



}