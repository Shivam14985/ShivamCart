package com.example.shivamscart.Fragments;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shivamscart.AboutApp;
import com.example.shivamscart.ChatBotActivity;
import com.example.shivamscart.EditPRofile;
import com.example.shivamscart.LoginActivity;
import com.example.shivamscart.Models.Users;
import com.example.shivamscart.R;
import com.example.shivamscart.SellerActivity;
import com.example.shivamscart.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class PRofileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;
    // creating a variable for
    // our volley request queue.
    private RequestQueue mRequestQueue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

            binding.SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.bonceexit);

                binding.SignOut.startAnimation(animation);
                binding.SignOut.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.SignOut.startAnimation(animation1);
                    }
                }, 0);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
  binding.HElpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.bonceexit);

                binding.HElpCenter.startAnimation(animation);
                binding.HElpCenter.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.HElpCenter.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent(getContext(), ChatBotActivity.class);
                startActivity(intent);
            }
        });

        //Sell options
        binding.SellProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.SellProduct.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.SellProduct.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.SellProduct.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), SellerActivity.class);
                startActivity(intent);
            }
        });

        //Edit PRofile
        binding.EditPRofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.EditPRofile.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.EditPRofile.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.EditPRofile.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), EditPRofile.class);
                startActivity(intent);
            }
        });
        // Showing Email, Number
        database.getReference().child("Users")
                .child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        if (snapshot.exists()) {
                            binding.MobileNo.setText(users.getNumber());
                            binding.Email.setText(users.getEmail());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //About App
        binding.AboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.AboutApp.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.AboutApp.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.AboutApp.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), AboutApp.class);
                startActivity(intent);
            }
        });

        return view;
    }

}