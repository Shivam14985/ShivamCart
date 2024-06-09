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
import com.example.shivamscart.databinding.ProductSuggestionRecyclerDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductSuugestionAdapter extends RecyclerView.Adapter<ProductSuugestionAdapter.viewHolder> {

    ArrayList<ProductsModel> list;
    Context context;

    public ProductSuugestionAdapter(ArrayList<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_suggestion_recycler_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel mobileModel = list.get(position);
        Picasso.get().load(mobileModel.getImage())
                .placeholder(R.drawable.gallery)
                .into(holder.binding.MobImage);
        holder.binding.mobileCompany.setText(mobileModel.getCompany());
        holder.binding.mobileModel.setText(mobileModel.getModel());
        holder.binding.mobileActualPrice.setText(Html.fromHtml("<s>" + mobileModel.getActualPrice() + "</s>"));
        holder.binding.mobilePrice.setText(mobileModel.getSellingPrice());
        holder.binding.mobileDiscount.setText(mobileModel.getDicount());
        holder.binding.Suggetion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                holder.binding.Suggetion.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                holder.binding.Suggetion.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.Suggetion.setBackgroundColor(originalColor);
                    }
                }, 100);
                if (mobileModel.getProductType().equals("Mobiles")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("mobileImage", list.get(position));
                    context.startActivity(intent);
                }
                if (mobileModel.getProductType().equals("Fashion")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("fashion", list.get(position));
                    context.startActivity(intent);
                }
                if (mobileModel.getProductType().equals("Electronics")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("Electronics", list.get(position));
                    context.startActivity(intent);
                }
                if (mobileModel.getProductType().equals("Home")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("HomeAppliance", list.get(position));
                    context.startActivity(intent);
                }
                if (mobileModel.getProductType().equals("Appliances")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("Appliances", list.get(position));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ProductSuggestionRecyclerDesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProductSuggestionRecyclerDesignBinding.bind(itemView);
        }
    }
}
