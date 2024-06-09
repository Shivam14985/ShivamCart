package com.example.shivamscart;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;
    ProgressDialog progressDialog;
    private BroadcastReceiver broadcastReceiver;
    String phoneNumber, otp;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        StartFirebaseLogin();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Signing In");
        binding.GoToOTpSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.SignInLAyout.setVisibility(View.GONE);
                binding.SignInWithOtpLayout.setVisibility(View.VISIBLE);
            }
        });
        binding.GOBAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.SignInLAyout.setVisibility(View.VISIBLE);
                binding.SignInWithOtpLayout.setVisibility(View.GONE);
            }
        });
        //SignIn button
        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bonceexit);
                binding.signInBtn.startAnimation(animation);
                binding.signInBtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.signInBtn.startAnimation(animation1);
                    }
                }, 0);
                String Email = binding.EtEmail.getText().toString();
                String Password = binding.EtPassword.getText().toString();

                if (Email.isEmpty() && Password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Above details", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        //SigUp activity
        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
        binding.ForGotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.SignInLAyout.setVisibility(View.GONE);
                binding.ForgotPAssWordLAyout.setVisibility(View.VISIBLE);
            }
        });
        binding.GOBAck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.SignInLAyout.setVisibility(View.VISIBLE);
                binding.ForgotPAssWordLAyout.setVisibility(View.GONE);
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.SignInWithOtpLayout.setVisibility(View.GONE);
                binding.SignInLAyout.setVisibility(View.VISIBLE);
            }
        });
        binding.ResetPAssword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = binding.EtEmailForgot.getText().toString();
                if (Email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter Email First", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(LoginActivity.this, "Reset password link has send to your Email ", Toast.LENGTH_LONG).show();
                            binding.SignInLAyout.setVisibility(View.VISIBLE);
                            binding.ForgotPAssWordLAyout.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        binding.SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.bonceexit);
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
                        LoginActivity.this,        // Activity (for callback binding)
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
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(LoginActivity.this, "verification completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, "Code sent to " + phoneNumber, Toast.LENGTH_LONG).show();
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (user != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onBackPressed() {
        finishAffinity();
    }
}