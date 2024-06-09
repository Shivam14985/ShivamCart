package com.example.shivamscart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.ProductView;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.FashionRecyclerDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ElectronicsAdapter extends RecyclerView.Adapter<ElectronicsAdapter.viewHolder> {

    ArrayList<ProductsModel> list;
    Context context;

    public ElectronicsAdapter(ArrayList<ProductsModel> list, Context context) {
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
        ProductsModel model = list.get(position);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.gallery).into(holder.binding.ProductImage);
        holder.binding.FashinCompany.setText(Html.fromHtml("<b>" + model.getCompany() + "</b>" + " " + model.getModel()));
        holder.binding.FashionDescription.setText(model.getElectronicsSpecs());
        holder.binding.ElectronicsRam.setText(model.getElectronicsRam());
        holder.binding.ElectronicsStorage.setText(model.getElectronicsStorage());
        holder.binding.ActualPrice.setText(Html.fromHtml("<s>" + model.getActualPrice() + "</s>"));
        holder.binding.SellingPrice.setText(model.getSellingPrice());
        holder.binding.mobileDiscount.setText(model.getDicount());
        holder.binding.Color.setText(model.getColor());
        holder.binding.mobilecompany.setText(Html.fromHtml("<b>" + model.getCompany() + "</b>" + " " + model.getModel()));
        holder.binding.mobileDiscounttext.setText(model.getDicount() + "% off");
        holder.binding.mobilespecs.setText("(" + model.getColor() + ", " + model.getMobileStorage() + "GB)");
        holder.binding.companyapp.setText(Html.fromHtml("<b>" + model.getCompany() + "</b>" + " " + model.getModel()));
        holder.binding.colorapp.setText(model.getColor());

        holder.binding.layout.setOnClickListener(new View.OnClickListener() {
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

        if (model.getProductType().equals("Mobiles")) {
            holder.binding.FashinCompany.setVisibility(View.GONE);
            holder.binding.FashionDescription.setVisibility(View.GONE);
            holder.binding.Color.setVisibility(View.GONE);
            holder.binding.layou.setVisibility(View.GONE);
            holder.binding.ProductImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.binding.mobilecompany.setVisibility(View.VISIBLE);
            holder.binding.mobileDiscounttext.setVisibility(View.VISIBLE);
            holder.binding.mobilespecs.setVisibility(View.VISIBLE);
        }
        if (model.getProductType().equals("Home")) {
            holder.binding.FashinCompany.setVisibility(View.GONE);
            holder.binding.FashionDescription.setVisibility(View.GONE);
            holder.binding.Color.setVisibility(View.GONE);
            holder.binding.specs.setText(model.getHomeApplianceSpecs());
            holder.binding.companyapp.setVisibility(View.VISIBLE);
            holder.binding.specs.setVisibility(View.VISIBLE);
            holder.binding.colorapp.setVisibility(View.VISIBLE);
        }
        if (model.getProductType().equals("Appliances")) {
            holder.binding.FashinCompany.setVisibility(View.GONE);
            holder.binding.FashionDescription.setVisibility(View.GONE);
            holder.binding.Color.setVisibility(View.GONE);
            holder.binding.specs.setText(model.getAppliaceSpecification());
            holder.binding.companyapp.setVisibility(View.VISIBLE);
            holder.binding.specs.setVisibility(View.VISIBLE);
            holder.binding.colorapp.setVisibility(View.VISIBLE);
        }
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
