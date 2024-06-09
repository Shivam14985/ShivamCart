package com.example.shivamscart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.ProductView;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.FashionRecyclerDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FashionAdapter extends RecyclerView.Adapter<FashionAdapter.viewHolder> {
    ArrayList<ProductsModel> list;
    Context context;

    public FashionAdapter(ArrayList<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fashion_recycler_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel fashionModel = list.get(position);
        Picasso.get().load(fashionModel.getImage()).placeholder(R.drawable.gallery).into(holder.binding.ProductImage);
        holder.binding.FashinCompany.setText(fashionModel.getCompany());
        holder.binding.FashionDescription.setText(fashionModel.getFashionSpecification());
        holder.binding.Color.setText(fashionModel.getColor());
        holder.binding.ActualPrice.setText(Html.fromHtml("<s>" + fashionModel.getActualPrice() + "</s>"));
        holder.binding.SellingPrice.setText( fashionModel.getSellingPrice());
        holder.binding.mobileDiscount.setText(fashionModel.getDicount());
//        holder.binding.ProductImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Animation animation= AnimationUtils.loadAnimation(context,R.anim.bounce);
//                final Animation animation1=AnimationUtils.loadAnimation(context,R.anim.bonceexit);
//
//                holder.binding.ProductImage.startAnimation(animation);
//                holder.binding.ProductImage.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        holder.binding.ProductImage.startAnimation(animation1);
//                    }
//                },0);
//                Intent intent=new Intent(context, imageview.class);
//                intent.putExtra("fashionProductImage",list.get(position));
//                context.startActivity(intent);
//            }
//        });
        holder.binding.fashioncard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                holder.binding.layout.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                holder.binding.layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.layout.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(context, ProductView.class);
                intent.putExtra("fashion", list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        FashionRecyclerDesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = FashionRecyclerDesignBinding.bind(itemView);
        }
    }
}
