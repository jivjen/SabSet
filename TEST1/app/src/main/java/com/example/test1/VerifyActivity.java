package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {
    private String verificationCode;
    private EditText otpField;
    private String otp;
    private Button verify;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private String name;
    private String phone;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgress = new ProgressDialog(VerifyActivity.this);
        mProgress.setTitle("Verifying...");
        mProgress.setMessage("Please Wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
        setContentView(R.layout.activity_verify);
         phone = getIntent().getExtras().getString("phone");
         name = getIntent().getExtras().getString("name");
        sendVerificationCode(phone);
        verify = findViewById(R.id.verifyButton);



        verify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i("Clicked","yes");
                otpField = findViewById(R.id.otp);
                otp = otpField.getText().toString();
                mProgress.show();
                verifyCode(otp);
            }
        });

    }

    private void sendVerificationCode(String number){
        Log.i("Number", number);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+number,        // Phone number to verify
                90,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;
            Log.i("Verification Code",s);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    } ;

    private void verifyCode(String userCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,userCode );
        signInUser(credential);
    }

    private void signInUser(PhoneAuthCredential credential){
        final FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            reference = FirebaseDatabase.getInstance().getReference();
//                            Log.i("ADDED","Added");
//                            User user = new User(phone,name);
//                            reference.child("users").child(phone).setValue(user);
                            Query checkUser = reference.equalTo(phone);
                            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists())
                                    {}
                                    else{
                                        Log.i("ADDED","Added");
                                        User user = new User(phone,name);
                                        reference.child("users").child(phone).setValue(user);
                                    }
                                    mProgress.dismiss();
                                    Toast.makeText(VerifyActivity.this, "Verification Successful", Toast.LENGTH_SHORT ).show();
                                    Intent homepage = new Intent(VerifyActivity.this, HomePage.class);
                                    homepage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(homepage);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                        else{
                            mProgress.dismiss();
                            Toast.makeText(VerifyActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
    }
}