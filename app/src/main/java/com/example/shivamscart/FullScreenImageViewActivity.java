package com.example.shivamscart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shivamscart.databinding.ActivityFullScreenImageViewBinding;
import com.google.firebase.database.FirebaseDatabase;

public class FullScreenImageViewActivity extends AppCompatActivity {
ActivityFullScreenImageViewBinding binding;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFullScreenImageViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();


    }
}