package com.example.shivamscart;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shivamscart.Adapters.MyProductsAdapters;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivitySellerBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SellerActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    ActivitySellerBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    private GoogleApiClient googleApiClient;
    private BroadcastReceiver broadcastReceiver;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.bonceexit);

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fseller_banner.png?alt=media&token=e83a47ff-8c02-4a83-9e47-4132551e246f").placeholder(R.drawable.gallery).into(binding.banner);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fdelivery_guy.jpg?alt=media&token=f1e7499a-363d-42df-bd14-9d60aa2aeb27").placeholder(R.drawable.gallery).into(binding.deliveryGuy);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Ffast.webp?alt=media&token=7883038a-2ea7-4ba9-9d1a-d95cb765802f").placeholder(R.drawable.gallery).into(binding.fast);
        ArrayList<ProductsModel> list = new ArrayList<>();
        MyProductsAdapters adapters = new MyProductsAdapters(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.MyProducts.setAdapter(adapters);
        binding.MyProducts.setLayoutManager(linearLayoutManager);

        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (auth.getCurrentUser().getUid().equals(snapshot1.child("addedBy").getValue().toString())) {
                        ProductsModel model = snapshot1.getValue(ProductsModel.class);
                        String id= snapshot1.getKey();
                        model.setProductId(id);
                        database.getReference().child("Products").child(id).setValue(model);
                        list.add(model);
                        binding.progress.setVisibility(View.GONE);
                    }
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this)
                //.enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        binding.VerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("seller").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue().toString();
                String targetUid = "true";
                //Add Mobile Button
                if (value.equals(targetUid)) {
                    binding.linearSeller.setVisibility(View.GONE);
                    binding.uploading.setVisibility(View.VISIBLE);
                    binding.tootext.setText("My Products");
                    binding.checkingseller.setVisibility(View.GONE);

                } else {
                    binding.linearSeller.setVisibility(View.VISIBLE);
                    binding.uploading.setVisibility(View.GONE);
                    binding.tootext.setText("Seller");
                    binding.checkingseller.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.BeSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.BeSeller.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.VerifyEmail.setVisibility(View.VISIBLE);
                        binding.progresssell.setVisibility(View.GONE);
                    }
                }, 1500);
                binding.progresssell.setVisibility(View.VISIBLE);
                binding.VerifyEmail.setVisibility(View.GONE);
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
                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("seller").addListenerForSingleValueEvent(new ValueEventListener() {
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
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            Toast.makeText(this, "You are Seller Now", Toast.LENGTH_SHORT).show();
            //database.getReference().child("Users").child(auth.getUid()).
            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("seller").setValue("true");
            binding.linearSeller.setVisibility(View.GONE);
            binding.uploading.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }

}