package com.example.shivamscart.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.shivamscart.FashionActivity;
import com.example.shivamscart.MobilesActivity;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
   FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater, container, false);
        View view=binding.getRoot();

        ArrayList<SlideModel> imageList=new ArrayList<>();
        imageList.add( new SlideModel("https://m.media-amazon.com/images/W/MEDIAX_792452-T1/images/G/31/img23/Wireless/Samsung/SamsungM/Cathero/M34/D85723568_INWLD-WLD-Samsung-M34-NewLaunch_catpage_1400x800_2._CB571344217_.jpg", ScaleTypes.FIT));
        imageList.add(new SlideModel("https://m.media-amazon.com/images/W/MEDIAX_792452-T1/images/G/31/img23/Wireless/OnePlus/OnePlus12/20Dec/D108792004_WLD_OnePlus_Waffle_NewLaunch_1400x800._CB586365395_.jpg",  ScaleTypes.FIT));
        imageList.add(new SlideModel("https://m.media-amazon.com/images/W/MEDIAX_792452-T1/images/G/31/img23/Wireless/Xiaomi/RedmiNote13/GW/D108421481_INWLD_RedmiNote13_1400x800._CB586366611_.jpg",  ScaleTypes.FIT));
        imageList.add(new SlideModel("https://m.media-amazon.com/images/W/MEDIAX_792452-T1/images/G/31/Iqoo12/iQOO12/Iqoo12new/IQOO12lasdday/D103643090_WLD_BAU_iQOO_12_5G_New_Launch_catpage_1400x800._CB586191460_.jpg",  ScaleTypes.FIT));
        imageList.add(new SlideModel("https://m.media-amazon.com/images/W/MEDIAX_792452-T1/images/G/31/img23/Wireless/OnePlus/Nord/NordCE3Lite/GW/21Dec/D75734134_1400x800._CB586209700_.jpg",  ScaleTypes.FIT));
        imageList.add(new SlideModel("https://m.media-amazon.com/images/W/MEDIAX_792452-T1/images/G/31/img21/Wireless/Shreyansh/BAU/Motodays/Dec/D93751383_WLD_BAU_Motorola_BrandDays_1400x800._CB571336362_.jpg",  ScaleTypes.FIT));
        binding.imageSlider.setImageList(imageList);


        //Mobiles
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
                Intent intent =new Intent(getContext(), MobilesActivity.class);
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
                Intent intent=new Intent(getContext(), FashionActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}