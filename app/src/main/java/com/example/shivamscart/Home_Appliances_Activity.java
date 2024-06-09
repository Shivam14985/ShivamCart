package com.example.shivamscart;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shivamscart.Adapters.HomeAppliancesAdapter;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.databinding.ActivityHomeAppliancesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_Appliances_Activity extends AppCompatActivity {
    ActivityHomeAppliancesBinding binding;
    FirebaseDatabase database;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeAppliancesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        ArrayList<ProductsModel> list = new ArrayList<>();
        HomeAppliancesAdapter adapter = new HomeAppliancesAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        binding.appliacerecycle.setLayoutManager(linearLayoutManager);
        binding.appliacerecycle.setAdapter(adapter);

        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Home")) {
                            ProductsModel model = snapshot1.getValue(ProductsModel.class);
                            list.add(model);
                            dialog.dismiss();
                        } else {
                        }
                    }
                } else {
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}