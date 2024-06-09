package com.example.shivamscart.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shivamscart.AppliancesActivity;
import com.example.shivamscart.ElectronicsActivity;
import com.example.shivamscart.FashionActivity;
import com.example.shivamscart.Home_Appliances_Activity;
import com.example.shivamscart.MobilesActivity;
import com.example.shivamscart.Models.AboutAppModel;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.FragmentCategoriesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CategoriesFragment extends Fragment {
    FragmentCategoriesBinding binding;
    FirebaseDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        database = FirebaseDatabase.getInstance();
        binding.mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.mobiles.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.mobiles.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.mobiles.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), MobilesActivity.class);
                startActivity(intent);
            }
        });
        binding.HomeAppliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.HomeAppliance.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.HomeAppliance.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.HomeAppliance.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), Home_Appliances_Activity.class);
                startActivity(intent);
            }
        });
        binding.fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.fashion.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.fashion.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.fashion.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), FashionActivity.class);
                startActivity(intent);
            }
        });
        binding.Electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.Electronics.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.Electronics.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Electronics.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), ElectronicsActivity.class);
                startActivity(intent);
            }
        });
        binding.Appliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AppliancesActivity.class);
                startActivity(intent);
            }
        });
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578062421?alt=media&token=6493af4f-7eff-4fdb-bd89-0be5a106332a").placeholder(R.drawable.gallery).into(binding.oneplusimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578124089?alt=media&token=b465d6a5-c563-4293-8282-d06fe74a9f80").placeholder(R.drawable.gallery).into(binding.fashionimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578217602?alt=media&token=7b18a09b-a506-4be2-8a7e-38b60b24c0db").placeholder(R.drawable.gallery).into(binding.applianceimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578297063?alt=media&token=dd332003-fc59-4f06-adbe-3193016dbec7").placeholder(R.drawable.gallery).into(binding.Homeimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578356583?alt=media&token=0809a987-01c5-47d8-997c-6f541f9d08cf").placeholder(R.drawable.gallery).into(binding.ElectImg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578560791?alt=media&token=b1468480-c0ba-4681-8249-3a8d19513b02").placeholder(R.drawable.gallery).into(binding.groceryimg);
        return view;
    }
}