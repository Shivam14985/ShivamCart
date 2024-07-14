package com.example.shivamscart;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.databinding.ActivityPaymentBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;
    FirebaseDatabase database;
    ProductsModel mobileProduct = null;
    ProductsModel fashionProduct = null;
    ProductsModel elctronicsProducts = null;
    ProductsModel HomeAppliancePRoducts = null;
    ProductsModel AppliancesPRoducts = null;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        final Object mobileProductObject = getIntent().getSerializableExtra("mobileProduct");
        if (mobileProductObject instanceof ProductsModel) {
            mobileProduct = (ProductsModel) mobileProductObject;
        }
        if (mobileProduct != null) {
            Picasso.get().load(mobileProduct.getImage()).placeholder(R.drawable.gallery).into(binding.image);
            binding.company.setText(mobileProduct.getCompany() + mobileProduct.getModel());
            binding.mobileActualPrice.setText(mobileProduct.getActualPrice());
            binding.mobilePrice.setText(mobileProduct.getSellingPrice());
            binding.color.setText(mobileProduct.getColor());
        }


        final Object fashionObject = getIntent().getSerializableExtra("fashionProducts");
        if (fashionObject instanceof ProductsModel) {
            fashionProduct = (ProductsModel) fashionObject;
        }
        if (fashionProduct != null) {
            Picasso.get().load(fashionProduct.getImage()).placeholder(R.drawable.gallery).into(binding.image);
            binding.company.setText(fashionProduct.getCompany() + fashionProduct.getModel());
            binding.mobileActualPrice.setText(fashionProduct.getActualPrice());
            binding.mobilePrice.setText(fashionProduct.getSellingPrice());
            binding.color.setText(fashionProduct.getColor());
        }

        final Object electronicsObjects = getIntent().getSerializableExtra("electronicsProducts");
        if (electronicsObjects instanceof ProductsModel) {
            elctronicsProducts = (ProductsModel) electronicsObjects;
        }
        if (elctronicsProducts != null) {
            Picasso.get().load(elctronicsProducts.getImage()).placeholder(R.drawable.gallery).into(binding.image);
            binding.company.setText(elctronicsProducts.getCompany() + elctronicsProducts.getModel());
            binding.mobileActualPrice.setText(elctronicsProducts.getActualPrice());
            binding.mobilePrice.setText(elctronicsProducts.getSellingPrice());
            binding.color.setText(elctronicsProducts.getColor());
        }

        final Object HomeAppliancesObjetc = getIntent().getSerializableExtra("HomeAppliancePRoducts");
        if (HomeAppliancesObjetc instanceof ProductsModel) {
            HomeAppliancePRoducts = (ProductsModel) HomeAppliancesObjetc;
        }
        if (HomeAppliancePRoducts != null) {
            Picasso.get().load(HomeAppliancePRoducts.getImage()).placeholder(R.drawable.gallery).into(binding.image);
            binding.company.setText(HomeAppliancePRoducts.getCompany() + HomeAppliancePRoducts.getModel());
            binding.mobileActualPrice.setText(HomeAppliancePRoducts.getActualPrice());
            binding.mobilePrice.setText(HomeAppliancePRoducts.getSellingPrice());
            binding.color.setText(HomeAppliancePRoducts.getColor());
        }

        final Object AppliancesPRoductsObject = getIntent().getSerializableExtra("AppliancesPRoducts");
        if (AppliancesPRoductsObject instanceof ProductsModel) {
            AppliancesPRoducts = (ProductsModel) AppliancesPRoductsObject;
        }
        if (AppliancesPRoducts != null) {
            Picasso.get().load(AppliancesPRoducts.getImage()).placeholder(R.drawable.gallery).into(binding.image);
            binding.company.setText(AppliancesPRoducts.getCompany() + AppliancesPRoducts.getModel());
            binding.mobileActualPrice.setText(AppliancesPRoducts.getActualPrice());
            binding.mobilePrice.setText(AppliancesPRoducts.getSellingPrice());
            binding.color.setText(AppliancesPRoducts.getColor());
        }

        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "This is Testing App Can't Buy Products", Toast.LENGTH_SHORT).show();
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