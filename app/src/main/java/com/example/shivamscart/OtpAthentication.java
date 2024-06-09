package com.example.shivamscart;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shivamscart.Models.Users;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityOtpAthenticationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OtpAthentication extends AppCompatActivity {
    ActivityOtpAthenticationBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    String phoneNumber, otp;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpAthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering");

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        StartFirebaseLogin();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.SingUpUsingEmil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.SingUpUsingEmil.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.SingUpUsingEmil.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.SingUpUsingEmil.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(OtpAthentication.this, SignUp.class);
                startActivity(intent);
            }
        });
        binding.SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(OtpAthentication.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(OtpAthentication.this, R.anim.bonceexit);
                binding.SendOtp.startAnimation(animation);
                binding.SendOtp.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        binding.SendOtp.startAnimation(animation1);
                    }
                }, 0);
                binding.Success.setVisibility(View.GONE);
                binding.Failed.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                phoneNumber = binding.Phone.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phoneNumber,                // Phone number to verify
                        60,                           // Timeout duration
                        TimeUnit.SECONDS,                // Unit of timeout
                        OtpAthentication.this,        // Activity (for callback binding)
                        mCallback);                      // OnVerificationStateChangedCallbacks
            }
        });

        binding.SubmitOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                otp = binding.otpsubmit.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                SigninWithPhone(credential);
            }
        });
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    binding.registeredsuccess.setVisibility(View.VISIBLE);
                    binding.layout.setVisibility(View.GONE);
                    binding.registeredsuccess.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OtpAthentication.this, "Welcome" + binding.EtName.getText().toString(), Toast.LENGTH_LONG).show();
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("number").setValue(phoneNumber);
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(binding.EtName.getText().toString());
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("seller").setValue("false");
                            Intent intent = new Intent(OtpAthentication.this, MainActivity.class);
                            startActivity(intent);
                        }
                    },4500);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(OtpAthentication.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(OtpAthentication.this, "verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OtpAthentication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
                binding.Failed.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                binding.progressBar.setVisibility(View.GONE);
                binding.Success.setVisibility(View.VISIBLE);
                binding.otpsubmitt.setVisibility(View.VISIBLE);
                binding.SubmitOtp.setVisibility(View.VISIBLE);
                Toast.makeText(OtpAthentication.this, "Code sent to " + phoneNumber, Toast.LENGTH_LONG).show();
            }
        };
    }
}