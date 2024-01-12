package com.example.shivamscart;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.auth.api.Auth;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shivamscart.Models.Users;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivitySellerBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;


public class SellerActivity extends AppCompatActivity {
    ActivitySellerBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    private BroadcastReceiver broadcastReceiver;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.bonceexit);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                //.enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        binding.VerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
        database.getReference().child("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("seller").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue().toString();
                        String targetUid = "true";
                        //Add Mobile Button
                        if (value.equals(targetUid)) {
                            binding.linearSeller.setVisibility(View.GONE);
                            binding.uploading.setVisibility(View.VISIBLE);

                        } else {
                            binding.linearSeller.setVisibility(View.VISIBLE);
                            binding.uploading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.BeSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.VerifyEmail.setVisibility(View.VISIBLE);
                //binding.mobileNumber.setVisibility(View.VISIBLE);
                //binding.SendOtp.setVisibility(View.VISIBLE);
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.BeSeller.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.BeSeller.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 100);
                database.getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("seller").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String value = snapshot.getValue().toString();
                                String targetUid = "true";
                                //Add Mobile Button
                                if (value.equals(targetUid)) {
                                    Toast.makeText(SellerActivity.this, "You are Already a Seller", Toast.LENGTH_SHORT).show();

                                } else {
                                    binding.BeSeller.setBackgroundColor(originalColor);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

            }
        });

        binding.AddMobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerActivity.this, AddProuctsActivity.class);
                startActivity(intent);
                binding.AddMobiles.startAnimation(animation);
                binding.AddMobiles.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.BeSeller.startAnimation(animation1);
                    }
                }, 0);

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            Toast.makeText(this, "You are Seller Now", Toast.LENGTH_SHORT).show();
            //database.getReference().child("Users").child(auth.getUid()).
            database.getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getUid())
                    .child("seller")
                    .setValue("true");
            binding.linearSeller.setVisibility(View.GONE);
            binding.uploading.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

}