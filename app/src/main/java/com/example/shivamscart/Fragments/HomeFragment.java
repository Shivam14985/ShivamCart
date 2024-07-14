package com.example.shivamscart.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.shivamscart.Adapters.ElectronicsAdapter;
import com.example.shivamscart.Adapters.FashionAdapter;
import com.example.shivamscart.AppliancesActivity;
import com.example.shivamscart.ElectronicsActivity;
import com.example.shivamscart.FashionActivity;
import com.example.shivamscart.Home_Appliances_Activity;
import com.example.shivamscart.MobilesActivity;
import com.example.shivamscart.Models.HomeSliderModel;
import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.R;
import com.example.shivamscart.SearchProductsActivity;
import com.example.shivamscart.databinding.FragmentHomeBinding;
import com.facebook.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    private AdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        final ArrayList<SlideModel> Poster = new ArrayList<>();
        database.getReference().child("Upcoming Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    HomeSliderModel model = data.getValue(HomeSliderModel.class);
                    Poster.add(new SlideModel(model.getSliderPoster(), ScaleTypes.FIT));
                    binding.imageSlider.setImageList(Poster);
                    binding.imageSlider.setVisibility(View.VISIBLE);
                    binding.shimmerFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //FAshiom
        ArrayList<ProductsModel> Menlist = new ArrayList<>();
        FashionAdapter Menadapter = new FashionAdapter(Menlist, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setStackFromEnd(true);
        binding.bottomwearmenrecycler.setLayoutManager(linearLayoutManager);
        binding.bottomwearmenrecycler.setAdapter(Menadapter);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Menlist.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Fashion")) {
                            ProductsModel fashionModel = snapshot1.getValue(ProductsModel.class);
                            Menlist.add(fashionModel);
                            binding.fashionrecycleShimmer.setVisibility(View.GONE);
                            String productID=snapshot1.getKey();
                            String searchabele=fashionModel.getActualPrice()+" "+fashionModel.getColor()+" "+fashionModel.getCompany()+" "+fashionModel.getDicount()+ " % discount"+
                                    fashionModel.getFashionFor()+" "+fashionModel.getFashionSpecification()+" "+fashionModel.getModel()+" "+fashionModel.getProductType()+" "+fashionModel.getSellingPrice();
                            database.getReference().child("Products").child(productID).child("searchabele").setValue(searchabele);
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
//HomeAppliance
        ArrayList<ProductsModel> Homelist = new ArrayList<>();
        ElectronicsAdapter adapterHome = new ElectronicsAdapter(Homelist, getContext());
        LinearLayoutManager linearLayoutManagerHome = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManagerHome.setStackFromEnd(true);
        binding.appliacerecycle.setLayoutManager(linearLayoutManagerHome);
        binding.appliacerecycle.setAdapter(adapterHome);

        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Homelist.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Home")) {
                            ProductsModel model = snapshot1.getValue(ProductsModel.class);
                            Homelist.add(model);
                            binding.HomerecycleShimmer.setVisibility(View.GONE);
                            String productID=snapshot1.getKey();
                            String searchable=model.getActualPrice()+" "+model.getColor()+" "+model.getCompany()+" "+model.getDicount()+ " % discount "+model.getHomeApplianceSpecs()+" "+model.getModel()+" "+model.getProductType()+" "+model.getSellingPrice();
                            database.getReference().child("Products").child(productID).child("searchabele").setValue(searchable);
                        } else {
                        }
                    }
                } else {
                }
                adapterHome.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Appliance
        ArrayList<ProductsModel> listAppliance = new ArrayList<>();
        ElectronicsAdapter adapterApp = new ElectronicsAdapter(listAppliance, getContext());
        LinearLayoutManager linearLayoutManagerApp = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManagerApp.setStackFromEnd(true);
        binding.recycler.setAdapter(adapterApp);
        binding.recycler.setLayoutManager(linearLayoutManagerApp);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listAppliance.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Appliances")) {
                            ProductsModel model = snapshot1.getValue(ProductsModel.class);
                            listAppliance.add(model);
                            binding.appliacerecycleShimmer.setVisibility(View.GONE);
                            String productID=snapshot1.getKey();
                            String searchable=model.getActualPrice()+" "+model.getColor()+" "+model.getCompany()+" "+model.getDicount()+ " % discount "+model.getAppliaceSpecification()+" "+model.getModel()+" "+model.getProductType()+" "+model.getSellingPrice();
                            database.getReference().child("Products").child(productID).child("searchabele").setValue(searchable);
                        } else {
                        }
                    }
                } else {
                }
                adapterApp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//Electronics
        ArrayList<ProductsModel> Electlist = new ArrayList<>();
        ElectronicsAdapter Elecadapter = new ElectronicsAdapter(Electlist, getContext());
        LinearLayoutManager linearLayoutManagerElec = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setStackFromEnd(true);
        binding.RecycleView.setAdapter(Elecadapter);
        binding.RecycleView.setLayoutManager(linearLayoutManagerElec);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Electlist.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String type = snapshot1.child("productType").getValue().toString();
                        if (type.equals("Electronics")) {
                            ProductsModel model = snapshot1.getValue(ProductsModel.class);
                            Electlist.add(model);
                            binding.ElectrecycleShimmer.setVisibility(View.GONE);
                            String productID=snapshot1.getKey();
                            String searchable=model.getActualPrice()+" "+model.getColor()+" "+model.getCompany()+" "+model.getDicount()+ " % discount "+model.getElectronicsCategory()+" "+model.getElectronicsRam()+" Gb Ram "+model.getElectronicsStorage()+" GB Rom Storage"+model.getElectronicsSpecs()+" "+model.getModel()+" "+model.getProductType()+" "+model.getSellingPrice();
                            database.getReference().child("Products").child(productID).child("searchabele").setValue(searchable);
                        } else {
                        }
                    }
                } else {
                }
                Elecadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Mobile
        ArrayList<ProductsModel> list = new ArrayList<>();
        ElectronicsAdapter adapter = new ElectronicsAdapter(list, getContext());
        LinearLayoutManager linearLayoutManagerMobile = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        linearLayoutManagerMobile.setStackFromEnd(true);
        binding.mobileRecylerView.setLayoutManager(linearLayoutManagerMobile);
        binding.mobileRecylerView.setAdapter(adapter);
        database.getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String Type = dataSnapshot.child("productType").getValue().toString();
                    if (Type.equals("Mobiles")) {
                        ProductsModel mobileModel = dataSnapshot.getValue(ProductsModel.class);
                        list.add(mobileModel);
                        binding.mobilerecyclershimmer.setVisibility(View.GONE);
                        String productID=dataSnapshot.getKey();
                        String searchable=mobileModel.getActualPrice()+" "+mobileModel.getColor()+" "+mobileModel.getCompany()+" "+mobileModel.getDicount()+ " % discount "+mobileModel.getMobileProcessor()+" "+mobileModel.getMobileRearCamera()+" Mp MEga Pixel Camera "+mobileModel.getMobileRam()+" Gb Ram "+mobileModel.getMobileStorage()+" Gb Rom Storage "+mobileModel.getModel()+" "+mobileModel.getProductType()+" "+mobileModel.getSellingPrice();
                        database.getReference().child("Products").child(productID).child("searchabele").setValue(searchable);
                    } else {
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.mobiles.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.mobiles.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.mobiles.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), MobilesActivity.class);
                startActivity(intent);

            }
        });
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                final Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.bonceexit);

                binding.search.startAnimation(animation);
                binding.search.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.search.startAnimation(animation1);
                    }
                }, 0);
                Intent intent = new Intent(getContext(), SearchProductsActivity.class);
                startActivity(intent);
            }
        });
        binding.Electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.Electronics.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.Electronics.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Electronics.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), ElectronicsActivity.class);
                startActivity(intent);
            }
        });

        binding.clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.clothes.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.clothes.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.clothes.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), FashionActivity.class);
                startActivity(intent);
            }
        });
        binding.HomeApplianceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.HomeApplianceLayout.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.HomeApplianceLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.HomeApplianceLayout.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), Home_Appliances_Activity.class);
                startActivity(intent);
            }
        });
        binding.Appliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.Appliance.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.Appliance.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.Appliance.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(getContext(), AppliancesActivity.class);
                startActivity(intent);
            }
        });
        binding.GroceryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                binding.GroceryLayout.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                binding.GroceryLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.GroceryLayout.setBackgroundColor(originalColor);
                    }
                }, 100);
                Toast.makeText(getContext(), "This Section is not added now.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Toast.makeText(getContext(), "Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void doubleClick(int i) {

            }
        });
        binding.imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578062421?alt=media&token=6493af4f-7eff-4fdb-bd89-0be5a106332a").placeholder(R.drawable.gallery).into(binding.oneplusimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578124089?alt=media&token=b465d6a5-c563-4293-8282-d06fe74a9f80").placeholder(R.drawable.gallery).into(binding.fashionimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578217602?alt=media&token=7b18a09b-a506-4be2-8a7e-38b60b24c0db").placeholder(R.drawable.gallery).into(binding.applianceimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578297063?alt=media&token=dd332003-fc59-4f06-adbe-3193016dbec7").placeholder(R.drawable.gallery).into(binding.Homeimg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578356583?alt=media&token=0809a987-01c5-47d8-997c-6f541f9d08cf").placeholder(R.drawable.gallery).into(binding.ElectImg);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shivam-s-cart-c0e04.appspot.com/o/Images%2FWmhcXrTZLpODIr0MmHsf4RNBcqR2%2F1708578560791?alt=media&token=b1468480-c0ba-4681-8249-3a8d19513b02").placeholder(R.drawable.gallery).into(binding.groceryimg);
        return view;
    }
}