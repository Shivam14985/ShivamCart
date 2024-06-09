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
import com.example.shivamscart.databinding.HomeAppliancesRecycleviewDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppliaceAdapter extends RecyclerView.Adapter<AppliaceAdapter.viewHolder> {
    ArrayList<ProductsModel> list;
    Context context;

    public AppliaceAdapter(ArrayList<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_appliances_recycleview_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel model = list.get(position);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.gallery).into(holder.binding.imageappliance);
        holder.binding.companyapp.setText(Html.fromHtml("<b>" + model.getCompany() + "</b>"));
        holder.binding.specs.setText(model.getAppliaceSpecification());
        holder.binding.colorapp.setText(model.getColor());
        holder.binding.mobileActualPrice.setText(Html.fromHtml("<s>" + model.getActualPrice() + "</s>"));
        holder.binding.mobilePrice.setText(model.getSellingPrice());
        holder.binding.mobileDiscount.setText(model.getDicount());

        holder.binding.clicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                holder.binding.clicks.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                holder.binding.clicks.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.clicks.setBackgroundColor(originalColor);
                    }
                }, 100);
                Intent intent = new Intent(context, ProductView.class);
                intent.putExtra("Appliances", list.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        HomeAppliancesRecycleviewDesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = HomeAppliancesRecycleviewDesignBinding.bind(itemView);
        }
    }
}
