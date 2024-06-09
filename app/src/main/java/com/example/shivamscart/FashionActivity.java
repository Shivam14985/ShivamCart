package com.example.shivamscart;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shivamscart.Adapters.FashionAdapter;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityFashionBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FashionActivity extends AppCompatActivity {
    ActivityFashionBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFashionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        //Men Shirt & Tshirts REcycler
        ArrayList<ProductsModel> Menlist = new ArrayList<>();
        FashionAdapter Menadapter = new FashionAdapter(Menlist, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.ShirtsREcycler.setLayoutManager(linearLayoutManager);
        binding.ShirtsREcycler.setAdapter(Menadapter);
//        binding.shirttshirts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LinearLayoutManager expand=new GridLayoutManager(FashionActivity.this,2);
//                binding.ShirtsREcycler.setLayoutManager(expand);
//            }
//        });
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Menlist.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Fashion")) {
                            String type1 = snapshot1.child("fashionFor").getValue().toString();
                            if (type1.equals("Men") || type1.equals("Boys")) {
                                String category = snapshot1.child("model").getValue().toString();
                                if (category.equals("Shirt") || category.equals("T-Shirt") || category.equals("Polo Shirt")) {
                                    ProductsModel fashionModel = snapshot1.getValue(ProductsModel.class);
                                    Menlist.add(fashionModel);
                                    binding.progressBarShirts.setVisibility(View.GONE);
                                } else {
                                }

                            } else {
                            }
                        } else {
                        }
                    }
                } else {
                }

                Menadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//Men bottomwear
        ArrayList<ProductsModel> MenBottomlist = new ArrayList<>();
        FashionAdapter MenBottomadapter = new FashionAdapter(MenBottomlist, this);
        LinearLayoutManager linearLayoutMa = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setStackFromEnd(true);
        binding.bottomwearmenrecycler.setLayoutManager(linearLayoutMa);
        binding.bottomwearmenrecycler.setAdapter(MenBottomadapter);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    MenBottomlist.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Fashion")) {
                            String type1 = snapshot1.child("fashionFor").getValue().toString();
                            if (type1.equals("Men") || type1.equals("Boys")) {
                                String category = snapshot1.child("model").getValue().toString();
                                if (category.equals("Jeans") || category.equals("Pants") || category.equals("Trouser") || category.equals("Cargo Pants") || category.equals("Chinos")) {
                                    ProductsModel fashionModel = snapshot1.getValue(ProductsModel.class);
                                    MenBottomlist.add(fashionModel);
                                    binding.progressbottomwearmen.setVisibility(View.GONE);
                                } else {
                                }
                            } else {
                            }
                        } else {
                        }
                    }
                } else {
                }

                MenBottomadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Men Winterwear
        ArrayList<ProductsModel> MenWinterlist = new ArrayList<>();
        FashionAdapter MenWinteradapter = new FashionAdapter(MenWinterlist, this);
        LinearLayoutManager linearLayoutMad = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setStackFromEnd(true);
        binding.WinterWearREcycler.setLayoutManager(linearLayoutMad);
        binding.WinterWearREcycler.setAdapter(MenWinteradapter);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    MenWinterlist.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Fashion")) {
                            String type1 = snapshot1.child("fashionFor").getValue().toString();
                            if (type1.equals("Men") || type1.equals("Boys")) {
                                String category = snapshot1.child("model").getValue().toString();
                                if (category.equals("Jackets") || category.equals("Hooddie") || category.equals("Sweater")) {
                                    ProductsModel fashionModel = snapshot1.getValue(ProductsModel.class);
                                    MenWinterlist.add(fashionModel);
                                    binding.WinterPRogressbar.setVisibility(View.GONE);
                                } else {
                                }
                            } else {
                            }
                        } else {
                        }
                    }
                } else {
                }

                MenWinteradapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Women
        ArrayList<ProductsModel> WomenEthicList = new ArrayList<>();
        FashionAdapter WomenEthicAdapter = new FashionAdapter(WomenEthicList, this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager1.setStackFromEnd(true);
        binding.WomenFAshionREcycler.setAdapter(WomenEthicAdapter);
        binding.WomenFAshionREcycler.setLayoutManager(linearLayoutManager1);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    WomenEthicList.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Fashion")) {
                            String type1 = snapshot1.child("fashionFor").getValue().toString();
                            if (type1.equals("Women") || type1.equals("Girls")) {
                                String caategory = snapshot1.child("model").getValue().toString();
                                if (caategory.equals("Saree") || caategory.equals("Kurta") || caategory.equals("Salwar Kameez") || caategory.equals("Lehenga") || caategory.equals("Ghagra")) {
                                    ProductsModel fashionModel = snapshot1.getValue(ProductsModel.class);
                                    WomenEthicList.add(fashionModel);
                                    binding.progressBarWOMen.setVisibility(View.GONE);
                                } else {
                                }
                            } else {
                            }
                        } else {
                        }
                    }
                } else {
                }
                WomenEthicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //WomenEthic
        ArrayList<ProductsModel> WomenwesternList = new ArrayList<>();
        FashionAdapter WomenWesternAdapter = new FashionAdapter(WomenwesternList, this);
        LinearLayoutManager WesternLAyoutMAnager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        WesternLAyoutMAnager.setStackFromEnd(true);
        binding.WomenWesternFAshionREcycler.setLayoutManager(WesternLAyoutMAnager);
        binding.WomenWesternFAshionREcycler.setAdapter(WomenWesternAdapter);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    WomenwesternList.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Fashion")) {
                            String type1 = snapshot1.child("fashionFor").getValue().toString();
                            if (type1.equals("Women") || type1.equals("Girls")) {
                                String caategory = snapshot1.child("model").getValue().toString();
                                if (caategory.equals("Dress") || caategory.equals("Skirt") || caategory.equals("Shift Dress") || caategory.equals("Crop Top") || caategory.equals("Shirt Dress")|| caategory.equals("Frock")) {
                                    ProductsModel fashionModel = snapshot1.getValue(ProductsModel.class);
                                    WomenwesternList.add(fashionModel);
                                    binding.progressBarWesternWOMen.setVisibility(View.GONE);
                                } else {
                                }
                            } else {
                            }
                        } else {
                        }
                    }
                } else {
                }
                WomenWesternAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        String Gender = auth.getCurrentUser().getUid().toString();
//        database.getReference().child("Users").child(Gender).child("gender").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(FashionActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
//                if (snapshot.getValue().toString().equals("Male")){
//                    RelativeLayout.LayoutParams rela=new RelativeLayout.LayoutParams(
//                            binding.MenLayout.addR
//                    )
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}