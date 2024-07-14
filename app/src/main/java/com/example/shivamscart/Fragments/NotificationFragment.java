package com.example.shivamscart.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shivamscart.Adapters.NotificationAdapter;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.databinding.FragmentNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {
    FragmentNotificationBinding binding;
    FirebaseDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        database = FirebaseDatabase.getInstance();
        binding.shimmerFrameLayout.startShimmer();

        ArrayList<ProductsModel> list = new ArrayList<>();
        NotificationAdapter adapter = new NotificationAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.recycler.setLayoutManager(linearLayoutManager);
        binding.recycler.setAdapter(adapter);

        database.getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String id = dataSnapshot.getKey().toString();
                        ProductsModel model = dataSnapshot.getValue(ProductsModel.class);
                        model.setProductId(id);
                        list.add(model);
                        binding.shimmerFrameLayout.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    binding.nothing.setVisibility(View.VISIBLE);
                    binding.shimmerFrameLayout.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}