package com.example.shivamscart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + mobileProduct.getActualPrice() + "</s>"));
            binding.mobilePrice.setText(mobileProduct.getSellingPrice());
            binding.color.setText(mobileProduct.getColor());

            binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PurchasingWhat=mobileProduct.getCompany()+" "+mobileProduct.getModel();
                    String ammount=mobileProduct.getSellingPrice();
                    payUsingUPI("Shivam,s Cart", "recipient@upi", "Payment for "+PurchasingWhat, ammount);
                }
            });
        }



        final Object fashionObject = getIntent().getSerializableExtra("fashionProducts");
        if (fashionObject instanceof ProductsModel) {
            fashionProduct = (ProductsModel) fashionObject;
        }
        if (fashionProduct != null) {
            Picasso.get().load(fashionProduct.getImage()).placeholder(R.drawable.gallery).into(binding.image);
            binding.company.setText(fashionProduct.getCompany() + fashionProduct.getModel());
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + fashionProduct.getActualPrice() + "</s>"));
            binding.mobilePrice.setText(fashionProduct.getSellingPrice());
            binding.color.setText(fashionProduct.getColor());

            binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PurchasingWhat=fashionProduct.getCompany()+" "+fashionProduct.getModel();
                    String ammount=fashionProduct.getSellingPrice();
                    payUsingUPI("Shivam,s Cart", "recipient@upi", "Payment for "+PurchasingWhat, ammount);
                }
            });
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

            binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PurchasingWhat=elctronicsProducts.getCompany()+" "+elctronicsProducts.getModel();
                    String ammount=elctronicsProducts.getSellingPrice();
                    payUsingUPI("Shivam,s Cart", "recipient@upi", "Payment for "+PurchasingWhat, ammount);
                }
            });
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

            binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PurchasingWhat=HomeAppliancePRoducts.getCompany()+" "+HomeAppliancePRoducts.getModel();
                    String ammount=HomeAppliancePRoducts.getSellingPrice();
                    payUsingUPI("Shivam,s Cart", "recipient@upi", "Payment for "+PurchasingWhat, ammount);
                }
            });
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

            binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PurchasingWhat=AppliancesPRoducts.getCompany()+" "+AppliancesPRoducts.getModel();
                    String ammount=AppliancesPRoducts.getSellingPrice();
                    payUsingUPI("Shivam,s Cart", "recipient@upi", "Payment for "+PurchasingWhat, ammount);
                }
            });
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // Method to trigger UPI Intent
    private void payUsingUPI(String name, String upiId, String note, String amount) {
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", "bhardwajshivam667@okaxis") // UPI ID
                .appendQueryParameter("pn", name)  // Payee Name
                .appendQueryParameter("tn", note)  // Transaction Note
                .appendQueryParameter("am", amount) // Amount
                .appendQueryParameter("cu", "INR")  // Currency
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with...");
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, 123);  // Request Code for Activity Result
        } else {
            Toast.makeText(PaymentActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle the UPI Intent Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if ((resultCode == RESULT_OK) || (resultCode == 11)) {
                if (data != null) {
                    String response = data.getStringExtra("response");
                    processUPIResponse(response);
                } else {
                    Toast.makeText(this, "Transaction failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Payment canceled by user", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void processUPIResponse(String response) {
        // Process the UPI response string to extract the status
        if (response != null && response.toLowerCase().contains("success")) {
            Toast.makeText(this, "Transaction successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Transaction failed", Toast.LENGTH_SHORT).show();
        }
    }
}