package com.example.shivamscart.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivamscart.FashionActivity;
import com.example.shivamscart.MobilesActivity;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.ActivityMobilesBinding;
import com.example.shivamscart.databinding.FragmentCategoriesBinding;
import com.example.shivamscart.databinding.FragmentHomeBinding;

public class CategoriesFragment extends Fragment {
    FragmentCategoriesBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCategoriesBinding.inflate(inflater, container, false);
        View view=binding.getRoot();

        binding.mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MobilesActivity.class);
                startActivity(intent);
            }
        });

        binding.fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), FashionActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}