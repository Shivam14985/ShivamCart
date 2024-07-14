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
import com.example.shivamscart.databinding.SearchRecyclerviewDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewHolder> {

    ArrayList<ProductsModel> list;
    Context context;

    public SearchAdapter(ArrayList<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_recyclerview_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel model = list.get(position);
        holder.binding.company.setText(model.getCompany() + " " + model.getModel());
        holder.binding.color.setText(model.getColor());
        Picasso.get().load(model.getImage()).placeholder(R.drawable.gallery).into(holder.binding.image);
        holder.binding.mobileActualPrice.setText(Html.fromHtml("<s>" + model.getActualPrice() + "</s>"));
        holder.binding.mobilePrice.setText(model.getSellingPrice());

        holder.binding.clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                holder.binding.clickable.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                holder.binding.clickable.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.clickable.setBackgroundColor(originalColor);
                    }
                }, 100);
                if (model.getProductType().equals("Mobiles")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("mobileImage", list.get(position));
                    context.startActivity(intent);
                }
                if (model.getProductType().equals("Fashion")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("fashion", list.get(position));
                    context.startActivity(intent);
                }
                if (model.getProductType().equals("Electronics")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("Electronics", list.get(position));
                    context.startActivity(intent);
                }
                if (model.getProductType().equals("Home")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("HomeAppliance", list.get(position));
                    context.startActivity(intent);
                }
                if (model.getProductType().equals("Appliances")) {
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
        SearchRecyclerviewDesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SearchRecyclerviewDesignBinding.bind(itemView);
        }
    }
}