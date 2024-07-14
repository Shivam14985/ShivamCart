package com.example.shivamscart;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.shivamscart.Adapters.ProductSuugestionAdapter;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityProductViewBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    ProductsModel mobileModel = null;
    ProductsModel fashion = null;
    ProductsModel Electronics = null;
    ProductsModel HomeAppliance = null;
    ProductsModel Appliances = null;
    private BroadcastReceiver broadcastReceiver;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        database = FirebaseDatabase.getInstance();
        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        loadBannerAds();

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fram.png?alt=media&token=79e14473-e658-47d5-9e08-1ba3dfa50dd4").placeholder(R.drawable.gallery).into(binding.Ramimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fprocessor.png?alt=media&token=71021cf1-1469-4042-82dc-105f6348645d").placeholder(R.drawable.gallery).into(binding.pROCESSORimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fcamera%20(1).png?alt=media&token=baf198f3-fb63-4ffa-80f0-aa092ee86c09").placeholder(R.drawable.gallery).into(binding.Cameraimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fdisplay.png?alt=media&token=77ba83e2-db47-43c7-9970-961f38e71146").placeholder(R.drawable.gallery).into(binding.Displayimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fcharging-battery.png?alt=media&token=ff2e5381-3d42-4f24-8f68-0b0ef9980fde").placeholder(R.drawable.gallery).into(binding.Batteryimg);
        ArrayList<ProductsModel> list = new ArrayList<>();

        ProductSuugestionAdapter adapter = new ProductSuugestionAdapter(list, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.Suggestion.setLayoutManager(linearLayoutManager);
        binding.Suggestion.setAdapter(adapter);


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String add = snapshot.getValue().toString();
                    binding.Adress.setText(add);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                    list.add(mobileModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductView.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//Fashion
        final Object fashionOBject = getIntent().getSerializableExtra("fashion");
        if (fashionOBject instanceof ProductsModel) {
            fashion = (ProductsModel) fashionOBject;
        }
        if (fashion != null) {
            final ArrayList<SlideModel> Poster = new ArrayList<>();
            if (fashion.getImage() != null) {
                Poster.add(new SlideModel(fashion.getImage(), ScaleTypes.CENTER_INSIDE));
            }
            if (fashion.getImage2() != null) {
                Poster.add(new SlideModel(fashion.getImage2(), ScaleTypes.CENTER_INSIDE));
            }
            if (fashion.getImage3() != null) {
                Poster.add(new SlideModel(fashion.getImage3(), ScaleTypes.CENTER_INSIDE));
            }
            if (fashion.getImage4() != null) {
                Poster.add(new SlideModel(fashion.getImage4(), ScaleTypes.CENTER_INSIDE));
            }
            if (fashion.getImage5() != null) {
                Poster.add(new SlideModel(fashion.getImage5(), ScaleTypes.CENTER_INSIDE));
            }

            binding.imageSlider.setImageList(Poster, ScaleTypes.CENTER_INSIDE);

            binding.fashionLayout.setVisibility(View.VISIBLE);
//            Picasso.get().load(fashion.getImage()).placeholder(R.drawable.gallery).into(binding.mobileViewImage);
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + fashion.getActualPrice() + "</s>"));
            binding.mobileDiscount.setText(fashion.getDicount());
            binding.mobilePrice.setText(fashion.getSellingPrice());
            binding.specification.setText(fashion.getFashionSpecification());
            binding.fashioncompany.setText(fashion.getCompany());
            binding.type.setText(fashion.getModel() + " for " + fashion.getFashionFor());
        }

        //electronics
        final Object electronicsObjects = getIntent().getSerializableExtra("Electronics");
        if (electronicsObjects instanceof ProductsModel) {
            Electronics = (ProductsModel) electronicsObjects;
        }
        if (Electronics != null) {
            binding.ElectronicsLayout.setVisibility(View.VISIBLE);
            final ArrayList<SlideModel> Poster = new ArrayList<>();
            if (Electronics.getImage() != null) {
                Poster.add(new SlideModel(Electronics.getImage(), ScaleTypes.CENTER_INSIDE));
            }
            if (Electronics.getImage2() != null) {
                Poster.add(new SlideModel(Electronics.getImage2(), ScaleTypes.CENTER_INSIDE));
            }
            if (Electronics.getImage3() != null) {
                Poster.add(new SlideModel(Electronics.getImage3(), ScaleTypes.CENTER_INSIDE));
            }
            if (Electronics.getImage4() != null) {
                Poster.add(new SlideModel(Electronics.getImage4(), ScaleTypes.CENTER_INSIDE));
            }
            if (Electronics.getImage5() != null) {
                Poster.add(new SlideModel(Electronics.getImage5(), ScaleTypes.CENTER_INSIDE));
            }

            binding.imageSlider.setImageList(Poster, ScaleTypes.CENTER_INSIDE);

//            Picasso.get().load(Electronics.getImage()).placeholder(R.drawable.gallery).into(binding.mobileViewImage);
            binding.companyElec.setText(Electronics.getCompany() + " " + Electronics.getElectronicsCategory());
            String category = Electronics.getElectronicsCategory().toString();
            if (category.equals("Laptops")) {
                binding.ramelec.setVisibility(View.VISIBLE);
                binding.ramelec.setText("(" + Electronics.getElectronicsRam() + "GB Ram, " + Electronics.getElectronicsStorage() + "GB Storage)");
            } else {
                binding.ramelec.setVisibility(View.GONE);
            }
            binding.colorelect.setText(Electronics.getColor());
            binding.specselec.setText(Electronics.getElectronicsSpecs());
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + Electronics.getActualPrice() + "</s>"));
            binding.mobilePrice.setText(Electronics.getSellingPrice());
            binding.mobileDiscount.setText(Electronics.getDicount());
        }

        //mobies
        final Object object = getIntent().getSerializableExtra("mobileImage");
        if (object instanceof ProductsModel) {
            mobileModel = (ProductsModel) object;
        }

        if (mobileModel != null) {
            final ArrayList<SlideModel> Poster = new ArrayList<>();
            if (mobileModel.getImage() != null) {
                Poster.add(new SlideModel(mobileModel.getImage(), ScaleTypes.CENTER_INSIDE));
            }
            if (mobileModel.getImage2() != null) {
                Poster.add(new SlideModel(mobileModel.getImage2(), ScaleTypes.CENTER_INSIDE));
            }
            if (mobileModel.getImage3() != null) {
                Poster.add(new SlideModel(mobileModel.getImage3(), ScaleTypes.CENTER_INSIDE));
            }
            if (mobileModel.getImage4() != null) {
                Poster.add(new SlideModel(mobileModel.getImage4(), ScaleTypes.CENTER_INSIDE));
            }
            if (mobileModel.getImage5() != null) {
                Poster.add(new SlideModel(mobileModel.getImage5(), ScaleTypes.CENTER_INSIDE));
            }

            binding.imageSlider.setImageList(Poster, ScaleTypes.CENTER_INSIDE);
            binding.mobile.setVisibility(View.VISIBLE);
            binding.mobiledescriptionmore.setVisibility(View.VISIBLE);
            binding.mobileRam.setText(mobileModel.getMobileRam());
            binding.mobilePrice.setText(mobileModel.getSellingPrice());
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + mobileModel.getActualPrice() + "</s>"));
            binding.mobileDiscount.setText(mobileModel.getDicount());
            binding.productColor.setText(mobileModel.getColor());
            binding.mobileStorage.setText(mobileModel.getMobileStorage());
            binding.mobileCompany.setText(Html.fromHtml("<b>" + mobileModel.getCompany() + "</b>" + " " + mobileModel.getModel()));
            binding.productcolor.setText(mobileModel.getColor());
            binding.Storage.setText(mobileModel.getMobileStorage());
            binding.Ramdes.setText(mobileModel.getMobileRam() + " GB RAM | " + mobileModel.getMobileStorage() + " GB ROM");
            binding.PROdes.setText(mobileModel.getMobileProcessor());
            binding.Camdes.setText(mobileModel.getMobileRearCamera() + "MP");
            binding.Displaydes.setText(mobileModel.getMobileDisplay() + "inch");
            binding.Batterydes.setText(mobileModel.getMobileBAttery() + "mAh");
            binding.Ram.setText(mobileModel.getMobileRam());
            binding.viewMore.setText("View more from " + mobileModel.getCompany());
        }
        //homeAppliance
        final Object homeObject = getIntent().getSerializableExtra("HomeAppliance");
        if (homeObject instanceof ProductsModel) {
            HomeAppliance = (ProductsModel) homeObject;
        }
        if (HomeAppliance != null) {
            final ArrayList<SlideModel> Poster = new ArrayList<>();
            if (HomeAppliance.getImage() != null) {
                Poster.add(new SlideModel(HomeAppliance.getImage(), ScaleTypes.CENTER_INSIDE));
            }
            if (HomeAppliance.getImage2() != null) {
                Poster.add(new SlideModel(HomeAppliance.getImage2(), ScaleTypes.CENTER_INSIDE));
            }
            if (HomeAppliance.getImage3() != null) {
                Poster.add(new SlideModel(HomeAppliance.getImage3(), ScaleTypes.CENTER_INSIDE));
            }
            if (HomeAppliance.getImage4() != null) {
                Poster.add(new SlideModel(HomeAppliance.getImage4(), ScaleTypes.CENTER_INSIDE));
            }
            if (HomeAppliance.getImage5() != null) {
                Poster.add(new SlideModel(HomeAppliance.getImage5(), ScaleTypes.CENTER_INSIDE));
            }

            binding.imageSlider.setImageList(Poster, ScaleTypes.CENTER_INSIDE);
            binding.homeLayout.setVisibility(View.VISIBLE);
            binding.homecompany.setText(HomeAppliance.getCompany() + " " + HomeAppliance.getModel());
            binding.homespecifications.setText(HomeAppliance.getHomeApplianceSpecs());
            binding.homecolor.setText(HomeAppliance.getColor());
            binding.mobilePrice.setText(HomeAppliance.getSellingPrice());
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + HomeAppliance.getActualPrice() + "</s>"));
            binding.mobileDiscount.setText(HomeAppliance.getDicount());
//            Picasso.get().load(HomeAppliance.getImage()).placeholder(R.drawable.gallery).into(binding.mobileViewImage);
        }

        //Appliances
        final Object AppliancesObjects = getIntent().getSerializableExtra("Appliances");
        if (AppliancesObjects instanceof ProductsModel) {
            Appliances = (ProductsModel) AppliancesObjects;
        }
        if (Appliances != null) {
            final ArrayList<SlideModel> Poster = new ArrayList<>();
            if (Appliances.getImage() != null) {
                Poster.add(new SlideModel(Appliances.getImage(), ScaleTypes.CENTER_INSIDE));
            }
            if (Appliances.getImage2() != null) {
                Poster.add(new SlideModel(Appliances.getImage2(), ScaleTypes.CENTER_INSIDE));
            }
            if (Appliances.getImage3() != null) {
                Poster.add(new SlideModel(Appliances.getImage3(), ScaleTypes.CENTER_INSIDE));
            }
            if (Appliances.getImage4() != null) {
                Poster.add(new SlideModel(Appliances.getImage4(), ScaleTypes.CENTER_INSIDE));
            }
            if (Appliances.getImage5() != null) {
                Poster.add(new SlideModel(Appliances.getImage5(), ScaleTypes.CENTER_INSIDE));
            }

            binding.imageSlider.setImageList(Poster, ScaleTypes.CENTER_INSIDE);
            binding.mobileActualPrice.setText(Html.fromHtml("<s>" + Appliances.getActualPrice() + "</s>"));
            binding.mobilePrice.setText(Appliances.getSellingPrice());
            binding.mobileDiscount.setText(Appliances.getDicount());
            binding.ApplianceLayout.setVisibility(View.VISIBLE);
            binding.AppliancesCompany.setText(Appliances.getCompany());
            binding.AppliancesCategory.setText(Appliances.getModel());
            binding.AppliancesColor.setText(Appliances.getColor());
            binding.Appliancesspecs.setText(Appliances.getAppliaceSpecification());
//            Picasso.get().load(Appliances.getImage()).placeholder(R.drawable.gallery).into(binding.mobileViewImage);
        }

        binding.rupay.setText(Html.fromHtml("<s>" + "₹40" + "</s>"));
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("LLL dd");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        binding.date.setText("Delivery By " + dateTime);
        binding.Changeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductView.this, EditPRofile.class);
                startActivity(intent);

            }
        });
        binding.BuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(ProductView.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(ProductView.this, R.anim.bonceexit);
                binding.BuyNow.startAnimation(animation);
                binding.BuyNow.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.BuyNow.startAnimation(animation1);
                        if (mobileModel != null) {
                            Intent intent = new Intent(ProductView.this, PaymentActivity.class);
                            intent.putExtra("mobileProduct", mobileModel);
                            startActivity(intent);
                        }
                        if (fashion != null) {
                            Intent intent = new Intent(ProductView.this, PaymentActivity.class);
                            intent.putExtra("fashionProducts", fashion);
                            startActivity(intent);
                        }
                        if (Electronics != null) {
                            Intent intent = new Intent(ProductView.this, PaymentActivity.class);
                            intent.putExtra("electronicsProducts", Electronics);
                            startActivity(intent);
                        }
                        if (HomeAppliance != null) {
                            Intent intent = new Intent(ProductView.this, PaymentActivity.class);
                            intent.putExtra("HomeAppliancePRoducts", HomeAppliance);
                            startActivity(intent);
                        }
                        if (Appliances != null) {
                            Intent intent = new Intent(ProductView.this, PaymentActivity.class);
                            intent.putExtra("AppliancesPRoducts", Appliances);
                            startActivity(intent);

                        }
                    }
                }, 0);
            }
        });
        //click event on add to cart
        binding.addProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileModel != null) {
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ProductsModel cartModel = new ProductsModel();
                            cartModel.setProductId(mobileModel.getProductId());
                            cartModel.setProductType(mobileModel.getProductType());
                            cartModel.setModel(mobileModel.getModel());
                            cartModel.setMobileRam(mobileModel.getMobileRam());
                            cartModel.setSellingPrice(mobileModel.getSellingPrice());
                            cartModel.setActualPrice(mobileModel.getActualPrice());
                            cartModel.setImage(mobileModel.getImage());
                            cartModel.setImage2(mobileModel.getImage2());
                            cartModel.setImage3(mobileModel.getImage3());
                            cartModel.setImage4(mobileModel.getImage4());
                            cartModel.setImage5(mobileModel.getImage5());
                            cartModel.setDicount(mobileModel.getDicount());
                            cartModel.setColor(mobileModel.getColor());
                            cartModel.setMobileStorage(mobileModel.getMobileStorage());
                            cartModel.setCompany(mobileModel.getCompany());
                            cartModel.setAddedBy(mobileModel.getAddedBy());
                            cartModel.setMobileDisplay(mobileModel.getMobileDisplay());
                            cartModel.setMobileRearCamera(mobileModel.getMobileRearCamera());
                            cartModel.setMobileProcessor(mobileModel.getMobileProcessor());
                            cartModel.setMobileBAttery(mobileModel.getMobileBAttery());
                            cartModel.setMobilentworkType(mobileModel.getMobilentworkType());
                            cartModel.setAddedAt(mobileModel.getAddedAt());
                            cartModel.setProductId(mobileModel.getProductId());
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").child(mobileModel.getProductId()).setValue(cartModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProductView.this, mobileModel.getCompany() + " " + mobileModel.getModel() + " added to cart", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if (fashion != null) {
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ProductsModel model = new ProductsModel();
                            model.setFashionFor(fashion.getFashionFor());
                            model.setProductType(fashion.getProductType());
                            model.setModel(fashion.getModel());
                            model.setCompany(fashion.getCompany());
                            model.setActualPrice(fashion.getActualPrice());
                            model.setSellingPrice(fashion.getSellingPrice());
                            model.setDicount(fashion.getDicount());
                            model.setColor(fashion.getColor());
                            model.setImage(fashion.getImage());
                            model.setImage2(fashion.getImage2());
                            model.setImage3(fashion.getImage3());
                            model.setImage4(fashion.getImage4());
                            model.setImage5(fashion.getImage5());
                            model.setFashionSpecification(fashion.getFashionSpecification());
                            model.setAddedBy(fashion.getAddedBy());
                            model.setAddedAt(fashion.getAddedAt());
                            model.setProductId(fashion.getProductId());
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").child(fashion.getProductId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProductView.this, fashion.getCompany() + " " + fashion.getModel() + " added to cart", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if (Electronics != null) {
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ProductsModel model = new ProductsModel();
                            model.setImage(Electronics.getImage());
                            model.setImage2(Electronics.getImage2());
                            model.setProductId(Electronics.getProductId());
                            model.setImage3(Electronics.getImage3());
                            model.setImage4(Electronics.getImage4());
                            model.setImage5(Electronics.getImage5());
                            model.setProductType(Electronics.getProductType());
                            model.setElectronicsCategory(Electronics.getElectronicsCategory());
                            model.setCompany(Electronics.getCompany());
                            model.setElectronicsSpecs(Electronics.getElectronicsSpecs());
                            model.setActualPrice(Electronics.getActualPrice());
                            model.setSellingPrice(Electronics.getSellingPrice());
                            model.setDicount(Electronics.getDicount());
                            model.setColor(Electronics.getColor());
                            model.setElectronicsRam(Electronics.getElectronicsRam());
                            model.setElectronicsStorage(Electronics.getElectronicsStorage());
                            model.setModel(Electronics.getModel());
                            model.setAddedAt(Electronics.getAddedAt());
                            model.setAddedBy(Electronics.getAddedBy());
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").child(Electronics.getProductId()).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ProductView.this, Electronics.getCompany() + " " + Electronics.getModel() + " added to cart", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if (HomeAppliance != null) {
                    ProductsModel productsModel = new ProductsModel();
                    productsModel.setProductType(HomeAppliance.getProductType());
                    productsModel.setCompany(HomeAppliance.getCompany());
                    productsModel.setModel(HomeAppliance.getModel());
                    productsModel.setHomeApplianceSpecs(HomeAppliance.getHomeApplianceSpecs());
                    productsModel.setActualPrice(HomeAppliance.getActualPrice());
                    productsModel.setSellingPrice(HomeAppliance.getSellingPrice());
                    productsModel.setDicount(HomeAppliance.getDicount());
                    productsModel.setColor(HomeAppliance.getColor());
                    productsModel.setAddedAt(HomeAppliance.getAddedAt());
                    productsModel.setAddedBy(HomeAppliance.getAddedBy());
                    productsModel.setImage(HomeAppliance.getImage());
                    productsModel.setImage2(HomeAppliance.getImage2());
                    productsModel.setImage3(HomeAppliance.getImage3());
                    productsModel.setProductId(HomeAppliance.getProductId());
                    productsModel.setImage4(HomeAppliance.getImage4());
                    productsModel.setImage5(HomeAppliance.getImage5());
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").child(HomeAppliance.getProductId()).setValue(productsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProductView.this, HomeAppliance.getCompany() + " " + HomeAppliance.getModel() + " added to cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if (Appliances != null) {
                    ProductsModel productsModel = new ProductsModel();
                    productsModel.setImage(Appliances.getImage());
                    productsModel.setImage2(Appliances.getImage2());
                    productsModel.setImage3(Appliances.getImage3());
                    productsModel.setImage4(Appliances.getImage4());
                    productsModel.setImage5(Appliances.getImage5());
                    productsModel.setProductType(Appliances.getProductType());
                    productsModel.setCompany(Appliances.getCompany());
                    productsModel.setModel(Appliances.getModel());
                    productsModel.setAppliaceSpecification(Appliances.getAppliaceSpecification());
                    productsModel.setActualPrice(Appliances.getActualPrice());
                    productsModel.setSellingPrice(Appliances.getSellingPrice());
                    productsModel.setDicount(Appliances.getDicount());
                    productsModel.setColor(Appliances.getColor());
                    productsModel.setAddedAt(Appliances.getAddedAt());
                    productsModel.setProductId(Appliances.getProductId());
                    productsModel.setAddedBy(Appliances.getAddedBy());
                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").child(Appliances.getProductId()).setValue(productsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProductView.this, Appliances.getCompany() + " " + Appliances.getModel() + " added to cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void loadBannerAds() {
        //Ads Code
        MobileAds.initialize(ProductView.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);
        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                loadBannerAds();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                binding.adView.setVisibility(View.VISIBLE);
            }
        });
    }
}