package com.example.shivamscart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.databinding.ActivityPaymentBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;
    FirebaseDatabase database;
    ProductsModel mobileProduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        final Object mobileProductObject=getIntent().getSerializableExtra("mobileProduct");
        if (mobileProductObject instanceof ProductsModel){
            mobileProduct=(ProductsModel) mobileProductObject;
        }
        if (mobileProduct!=null){
            Picasso.get().load(mobileProduct.getImage()).placeholder(R.drawable.gallery).into(binding.image);
            binding.company.setText(mobileProduct.getCompany()+mobileProduct.getModel());
            binding.mobileActualPrice.setText(mobileProduct.getActualPrice());
            binding.mobilePrice.setText(mobileProduct.getSellingPrice());
            binding.color.setText(mobileProduct.getColor());
        }

    }
}