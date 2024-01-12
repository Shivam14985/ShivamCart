package com.example.shivamscart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.example.shivamscart.Adapters.MobileAdapter;
import com.example.shivamscart.Adapters.ProductSuugestionAdapter;
import com.example.shivamscart.Fragments.CartFragment;
import com.example.shivamscart.Models.MobileModel;
import com.example.shivamscart.Models.Users;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityProductViewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProductView extends AppCompatActivity {
    ActivityProductViewBinding binding;
    FirebaseDatabase database;
    MobileModel mobileModel = null;
    private BroadcastReceiver broadcastReceiver;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        database = FirebaseDatabase.getInstance();
        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        ArrayList<MobileModel> list = new ArrayList<>();

        ProductSuugestionAdapter adapter = new ProductSuugestionAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.Suggestion.setLayoutManager(linearLayoutManager);
        binding.Suggestion.setAdapter(adapter);

        database.getReference().child("Users")
                .child(FirebaseAuth.getInstance().getUid()).child("Address")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String add=snapshot.getValue().toString();
                            binding.Adress.setText(add);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        database.getReference().child("Products").child("Mobiles")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            MobileModel mobileModel = dataSnapshot.getValue(MobileModel.class);
                            list.add(mobileModel);

                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ProductView.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        final Object object = getIntent().getSerializableExtra("mobileImage");
        if (object instanceof MobileModel) {
            mobileModel = (MobileModel) object;
        }

        if (mobileModel != null) {
            Picasso.get().load(mobileModel.getMobileImage()).into(binding.mobileViewImage);
            binding.mobileRam.setText(mobileModel.getMobileRam());
            binding.mobilePrice.setText(mobileModel.getMobilePrice());
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + mobileModel.getMobileActualPrice() + "</s>"));
            binding.mobileDiscount.setText(mobileModel.getMobileDicount());
            binding.productColor.setText(mobileModel.getMobileColor());
            binding.mobileStorage.setText(mobileModel.getMobileStorage());
            binding.mobileCompany.setText(mobileModel.getMobileCompany());
            binding.mobileModel.setText(mobileModel.getMobileDescription());
            binding.productcolor.setText(mobileModel.getMobileColor());
            binding.Storage.setText(mobileModel.getMobileStorage());
            binding.Ram.setText(mobileModel.getMobileRam());
            binding.viewMore.setText("View more from " + mobileModel.getMobileCompany());
            binding.rupay.setText(Html.fromHtml("<s>" + "₹40" + "</s>"));
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("LLL dd");
            dateTime = simpleDateFormat.format(calendar.getTime()).toString();
            binding.date.setText("Delivery By " + dateTime);
        }
//        //click event on cart
//        binding.shopingCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(ProductView.this, CartFragment.class);
//                startActivity(intent);
//            }
//        });

        binding.Changeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProductView.this, EditPRofile.class);
                startActivity(intent);

            }
        });
        //click event on add to cart
        binding.addProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}