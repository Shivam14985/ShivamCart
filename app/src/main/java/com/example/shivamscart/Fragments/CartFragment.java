package com.example.shivamscart.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shivamscart.Adapters.CartAdapter;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.FragmentCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    FragmentCartBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
//        dialog.show();

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ArrayList<ProductsModel> list = new ArrayList<>();
        CartAdapter adapters = new CartAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.CartRecycler.setLayoutManager(linearLayoutManager);
        binding.CartRecycler.setAdapter(adapters);

        database.getReference().child("Users").child(auth.getUid()).child("Cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductsModel model = dataSnapshot.getValue(ProductsModel.class);
                    list.add(model);
                    dialog.dismiss();
                }
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}