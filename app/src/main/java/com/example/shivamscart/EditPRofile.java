package com.example.shivamscart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.shivamscart.Models.Users;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditPRofile extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);
        final Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.bonceexit);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(EditPRofile.this);

        database.getReference().child("Users")
                .child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        binding.NAme.setText(users.getName());
                        binding.Email.setText(users.getEmail());
                        binding.Phone.setText(users.getNumber());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        database.getReference().child("Users")
                .child(auth.getUid()).child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Address=snapshot.getValue().toString();
                        binding.Address.setText(Address);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        database.getReference().child("Users")
                .child(auth.getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            binding.EnterEmail.setVisibility(View.GONE);

                            binding.cantemail.setVisibility(View.VISIBLE);
                        }
                        else{
                            binding.EnterEmail.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        database.getReference().child("Users")
                .child(auth.getUid()).child("gender").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            binding.RadioGroupup.setVisibility(View.GONE);
                        }else{
                            binding.RadioGroupup.setVisibility(View.VISIBLE);
                            binding.gender.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        binding.ChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ChangeName.startAnimation(animation);
                binding.ChangeName.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.ChangeName.startAnimation(animation1);
                    }
                }, 0);
                binding.NAme.getText().toString();
                database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("name").setValue(binding.NAme.getText().toString());
                Toast.makeText(EditPRofile.this, "Name Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });
        binding.EnterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.EnterEmail.startAnimation(animation);
                binding.EnterEmail.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.EnterEmail.startAnimation(animation1);
                    }
                }, 0);
                database.getReference().child("Users")
                        .child(auth.getUid()).child("email").setValue(binding.Email.getText().toString());
                Toast.makeText(EditPRofile.this, "Email Updates Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        binding.gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.gender.startAnimation(animation);
                binding.gender.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.gender.startAnimation(animation1);
                    }
                }, 0);
                RadioGroup rdbgender = findViewById(R.id.RadioGroupup);
                RadioButton rdbselectedgen = findViewById(rdbgender.getCheckedRadioButtonId());
                String gender=rdbselectedgen.getText().toString();
                database.getReference().child("Users")
                        .child(auth.getUid()).child("gender").setValue(gender);
                Toast.makeText(EditPRofile.this, "Gender Updated Successfuly", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ChangAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Address=binding.Address.getText().toString();
                binding.ChangAddress.startAnimation(animation);
                binding.ChangAddress.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.ChangAddress.startAnimation(animation1);
                    }
                }, 0);if (Address.isEmpty()){
                    Toast.makeText(EditPRofile.this, "Please fill first ", Toast.LENGTH_SHORT).show();
                }else {
                database.getReference().child("Users")
                        .child(auth.getUid()).child("Address").setValue(binding.Address.getText().toString());
                Toast.makeText(EditPRofile.this, "Address Updated", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }}
        });
        binding.Address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                binding.ChangAddress.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.serch_bar_bg));
                binding.ChangAddress.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.ChangAddress.setBackgroundDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.dicount_bg));
                binding.ChangAddress.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                binding.ChangAddress.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}