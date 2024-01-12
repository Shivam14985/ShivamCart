package com.example.shivamscart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.shivamscart.Adapters.MobileAdapter;
import com.example.shivamscart.Models.MobileModel;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityMobilesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MobilesActivity extends AppCompatActivity {
    ActivityMobilesBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Dialog dialog;
    private BroadcastReceiver broadcastReceiver;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMobilesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        ArrayList<MobileModel> list = new ArrayList<>();

        MobileAdapter adapter = new MobileAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.mobileRecylerView.setLayoutManager(linearLayoutManager);
        binding.mobileRecylerView.setAdapter(adapter);


        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        database.getReference().child("Products").child("Mobiles")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            MobileModel mobileModel=dataSnapshot.getValue(MobileModel.class);
                            list.add(mobileModel);
                            dialog.dismiss();
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//        //Get Current User id
//        database.getReference().child("Users")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child("seller").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String value=snapshot.getValue().toString();
//                        String targetUid = "true";
//                        //Add Mobile Button
//                        if (value.equals(targetUid)) {
//                            binding.AddMobiles.setVisibility(View.VISIBLE);
//                        }
//                        else {}
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
        //the user i want to show button his Uid


    }

}