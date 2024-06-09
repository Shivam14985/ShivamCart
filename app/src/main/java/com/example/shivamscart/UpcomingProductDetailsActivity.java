package com.example.shivamscart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shivamscart.Models.HomeSliderModel;
import com.example.shivamscart.databinding.ActivityUpcomingProductDetailsBinding;
import com.squareup.picasso.Picasso;

public class UpcomingProductDetailsActivity extends AppCompatActivity {
ActivityUpcomingProductDetailsBinding binding;
HomeSliderModel model=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpcomingProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        final Object object=getIntent().getSerializableExtra("model");
        if (object instanceof HomeSliderModel){
            model=(HomeSliderModel) object;
        }
        if (model!=null){
            Picasso.get().load(model.getImage1()).placeholder(R.drawable.gallery).into(binding.imageView3);
        }
    }
}