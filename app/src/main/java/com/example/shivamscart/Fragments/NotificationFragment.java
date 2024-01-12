package com.example.shivamscart.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivamscart.R;
import com.example.shivamscart.databinding.FragmentCartBinding;
import com.example.shivamscart.databinding.FragmentNotificationBinding;
import com.facebook.shimmer.ShimmerFrameLayout;


public class NotificationFragment extends Fragment {
    FragmentNotificationBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentNotificationBinding.inflate(inflater, container, false);
        View view=binding.getRoot();
        binding.shimmerFrameLayout.startShimmerAnimation();
        return view;
    }
}