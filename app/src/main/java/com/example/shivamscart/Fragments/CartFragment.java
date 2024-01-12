package com.example.shivamscart.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivamscart.Adapters.CartAdapter;
import com.example.shivamscart.Adapters.MobileAdapter;
import com.example.shivamscart.Models.MobileModel;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {

FragmentCartBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding= FragmentCartBinding.inflate(inflater, container, false);
        View view=binding.getRoot();
        return view;
    }
}