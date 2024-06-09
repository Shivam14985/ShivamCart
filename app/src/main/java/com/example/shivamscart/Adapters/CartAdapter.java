package com.example.shivamscart.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivamscart.Models.ProductsModel;
import com.example.shivamscart.ProductView;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.HomeAppliancesRecycleviewDesignBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder> {
    ArrayList<ProductsModel> list;
    Context context;

    public CartAdapter(ArrayList<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(com.example.shivamscart.R.layout.home_appliances_recycleview_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel model = list.get(position);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.gallery).into(holder.binding.imageappliance);
        holder.binding.companyapp.setText(Html.fromHtml("<b>" + model.getCompany() + "</b>" + " " + model.getModel()));
        holder.binding.specs.setText(model.getHomeApplianceSpecs());
        holder.binding.colorapp.setText(model.getColor());
        holder.binding.mobileActualPrice.setText(Html.fromHtml("<s>" + model.getActualPrice() + "</s>"));
        holder.binding.mobilePrice.setText(model.getSellingPrice());
        holder.binding.mobileDiscount.setText(model.getDicount());

        if (model.getProductType().equals("Mobiles")) {
            holder.binding.DiscountLAyot.setVisibility(View.GONE);
            holder.binding.specs.setVisibility(View.GONE);
            holder.binding.mobileDiscountcart.setVisibility(View.VISIBLE);
            holder.binding.mobileRam.setVisibility(View.VISIBLE);
            holder.binding.mobileDiscountcart.setText(model.getDicount() + "% off");
            holder.binding.mobileRam.setText(model.getMobileRam() + "GB RAM");
            holder.binding.colorapp.setText("(" + model.getColor() + ", " + model.getMobileStorage() + "GB)");
           }
        holder.binding.clicks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Press long to remove this product from cart", Toast.LENGTH_LONG).show();
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
        holder.binding.clicks.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Do you want to remove product");
                alert.setTitle("Warning!!!!");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, model.getProductId(), Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Cart").child(model.getProductId()).setValue(null).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Product Removed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alert.setNeutralButton("Help", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Press yes to remove product", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
                return false;
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
