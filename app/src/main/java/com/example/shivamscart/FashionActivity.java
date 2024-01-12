package com.example.shivamscart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityFashionBinding;

public class FashionActivity extends AppCompatActivity {
    ActivityFashionBinding binding;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFashionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}