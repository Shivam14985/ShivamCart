package com.example.shivamscart;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shivamscart.Models.Users;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    private BroadcastReceiver broadcastReceiver;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setMessage("Registering");


        //Date Picker
        binding.EtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// on below line we are adding click listener
                // for our pick date button
                binding.EtDOB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // on below line we are getting
                        // the instance of our calendar.
                        final Calendar c = Calendar.getInstance();

                        // on below line we are getting
                        // our day, month and year.
                        int year = c.get(Calendar.YEAR)-18;
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // on below line we are creating a variable for date picker dialog.
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                // on below line we are passing context.
                                SignUp.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // on below line we are setting date to our edit text.
                                        binding.EtDOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                    }
                                },
                                // on below line we are passing year,
                                // month and day for selected date in our date picker.
                                year, month, day);
                        // at last we are calling show to
                        // display our date picker dialog.
                        datePickerDialog.show();
                    }
                });
            }
        });

        //Sig Up button
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(SignUp.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(SignUp.this, R.anim.bonceexit);

                binding.signup.startAnimation(animation);
                binding.signup.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.signup.startAnimation(animation1);
                    }
                }, 0);
                String Email = binding.EtEmail.getText().toString();
                String Number = binding.EtPhone.getText().toString();
                String Dob = binding.EtDOB.getText().toString();
                String Name = binding.EtName.getText().toString();
                String Password = binding.EtPassword.getText().toString();
                String Seller = binding.Seller.getText().toString();
                RadioGroup rdbgender = findViewById(R.id.RadioGroup);
                RadioButton rdbselectedgen = findViewById(rdbgender.getCheckedRadioButtonId());
                String gender = rdbselectedgen.getText().toString();
                if (binding.EtEmail.getText().toString().isEmpty() && binding.EtPhone.getText().toString().isEmpty()&& binding.EtName.getText().toString().isEmpty() && binding.EtPassword.getText().toString().isEmpty()&&binding.EtDOB.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this, "Fill all details", Toast.LENGTH_SHORT).show();}
                else {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        binding.LayoutForEmailAuthentication.setVisibility(View.GONE);
                                        binding.registeredsuccess.setVisibility(View.VISIBLE);
                                        binding.registeredsuccess.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Users users = new Users(Email, Number, Name, Password, Dob, gender, Seller);
                                                String id = task.getResult().getUser().getUid();
                                                database.getReference().child("Users").child(id).setValue(users);
                                                Toast.makeText(SignUp.this, "Registered", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        },4500);
                                    } else {
                                        Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        //signUp using Otp
        //SigIn activity
        binding.logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(SignUp.this, LoginActivity.class);
                        startActivity(intent);
            }
        });

        //Sign Up USing Phone
        binding.AuthPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(SignUp.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(SignUp.this, R.anim.bonceexit);
                binding.AuthPhone.startAnimation(animation);
                binding.AuthPhone.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AuthPhone.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent(SignUp.this, OtpAthentication.class);
                startActivity(intent);
            }
        });
    }


    public void onBackPressed() {
        finishAffinity();
    }

}