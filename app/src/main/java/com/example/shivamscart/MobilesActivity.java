package com.example.shivamscart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shivamscart.Adapters.MobileAdapter;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.Services.NetworkBroadcast;
import com.example.shivamscart.databinding.ActivityMobilesBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MobilesActivity extends AppCompatActivity {
    ActivityMobilesBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Dialog dialog;
    //For Show All Mobiles
    ArrayList<ProductsModel> list = new ArrayList<>();
    MobileAdapter adapter = new MobileAdapter(list, this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
    private BroadcastReceiver broadcastReceiver;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMobilesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fapple.png?alt=media&token=1cd96a4e-137e-462f-8142-a049c7231def").placeholder(R.drawable.gallery).into(binding.Apple);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Finfinix.jpg?alt=media&token=0c5b2455-eb79-49ae-ab23-910c9a45c4ac").placeholder(R.drawable.gallery).into(binding.Infinix);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fgoogle.webp?alt=media&token=34f84e9c-bf9e-4499-afaf-05e52416dabb").placeholder(R.drawable.gallery).into(binding.Google);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fiq.jpg?alt=media&token=287f42a3-3626-48b9-b698-edfe22041805").placeholder(R.drawable.gallery).into(binding.Iq00);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fmi.png?alt=media&token=133a6546-8d47-4905-91ef-e7f94d58f88c").placeholder(R.drawable.gallery).into(binding.mi);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Frealme.png?alt=media&token=6dd71654-aecf-40b6-83f2-ee0b9e651b52").placeholder(R.drawable.gallery).into(binding.Realme);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Ftechno.jpg?alt=media&token=36f70213-5404-4b77-9c7f-fd5463278436").placeholder(R.drawable.gallery).into(binding.Techno);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fvivo.jpg?alt=media&token=9fe12226-3b31-4062-a7c0-f1f0802b4811").placeholder(R.drawable.gallery).into(binding.vivo);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Fsamsung.png?alt=media&token=4004407d-4430-49e9-924f-47a923e9efaf").placeholder(R.drawable.gallery).into(binding.Samsung);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Foneplus.png?alt=media&token=2aca538d-08c1-49f9-bcca-481486e086ba").placeholder(R.drawable.gallery).into(binding.OnePlus);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2Foppo.webp?alt=media&token=6809ba25-9dc6-4c9a-aa2f-c920469233a8").placeholder(R.drawable.gallery).into(binding.oppo);

        //Ads Code
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        linearLayoutManager.setStackFromEnd(true);
        binding.mobileRecylerView.setLayoutManager(linearLayoutManager);
        binding.mobileRecylerView.setAdapter(adapter);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String Type = dataSnapshot.child("productType").getValue().toString();
                    if (Type.equals("Mobiles")) {
                        ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                        String id = dataSnapshot.getKey().toString();
                        mobileModel.setProductId(id);
                        database.getReference().child("Products").child(id).child("productId").setValue(id);
                        list.add(mobileModel);
                        dialog.dismiss();
                    } else {
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.GONE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                list.add(mobileModel);
                                dialog.dismiss();
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Oppo")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);
                binding.oppo.startAnimation(animation);
                binding.oppo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.oppo.startAnimation(animation1);
                    }
                }, 0);

//                binding.IqRv.setVisibility(View.GONE);
//                binding.InfinixRv.setVisibility(View.GONE);
//                binding.MiRv.setVisibility(View.GONE);
//                binding.GoogleRv.setVisibility(View.GONE);
//                binding.TechnoRv.setVisibility(View.GONE);
//                binding.VivoMobilesRecycler.setVisibility(View.GONE);
//                binding.OnePlusRecycler.setVisibility(View.GONE);
//                binding.RealmeRecyclerView.setVisibility(View.GONE);
//                binding.mobileRecylerView.setVisibility(View.GONE);
//                binding.AppleRecyclerView.setVisibility(View.GONE);
//                binding.SamsungRecyclerView.setVisibility(View.GONE);
//                binding.OppoMobilesRecycler.setVisibility(View.VISIBLE);
            }
        });
        binding.vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Vivo")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);
                binding.vivo.startAnimation(animation);
                binding.vivo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.vivo.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.Samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Samsung")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.Samsung.startAnimation(animation);
                binding.Samsung.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Samsung.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.Apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Apple")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.Apple.startAnimation(animation);
                binding.Apple.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Apple.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.Realme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Realme")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.Realme.startAnimation(animation);
                binding.Realme.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Realme.startAnimation(animation1);
                    }
                }, 0);

            }
        });
        binding.OnePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("OnePlus")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.OnePlus.startAnimation(animation);
                binding.OnePlus.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.OnePlus.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.Iq00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("iQOO")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.Iq00.startAnimation(animation);
                binding.Iq00.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Iq00.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.Infinix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Infinix")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.Infinix.startAnimation(animation);
                binding.Infinix.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Infinix.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Mi")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.mi.startAnimation(animation);
                binding.mi.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.mi.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Google")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.Google.startAnimation(animation);
                binding.Google.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Google.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.Techno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewAll.setVisibility(View.VISIBLE);
                database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String Type = dataSnapshot.child("productType").getValue().toString();
                            if (Type.equals("Mobiles")) {
                                String OppoCompany = dataSnapshot.child("company").getValue().toString();
                                if (OppoCompany.equals("Techno")) {
                                    ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                                    list.add(mobileModel);
                                }
                            } else {
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MobilesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                final Animation animation = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(MobilesActivity.this, R.anim.bonceexit);

                binding.Techno.startAnimation(animation);
                binding.Techno.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Techno.startAnimation(animation1);
                    }
                }, 0);
            }
        });
        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });
    }

}