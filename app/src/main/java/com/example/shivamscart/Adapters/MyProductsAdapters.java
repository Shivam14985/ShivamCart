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
import com.example.shivamscart.databinding.MyProductsRecyclerDesignBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyProductsAdapters extends RecyclerView.Adapter<MyProductsAdapters.viewHolder> {

    ArrayList<ProductsModel> list = new ArrayList<>();
    Context context;

    public MyProductsAdapters(ArrayList<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_products_recycler_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel productsModel = list.get(position);
        holder.binding.mobilecompany.setText(Html.fromHtml("<b>" + productsModel.getCompany() + "</b>" + " " + productsModel.getModel()));
        holder.binding.mobiledescription.setText("Color:" + productsModel.getColor());
        holder.binding.ActualPrice.setText(Html.fromHtml("<s>" + productsModel.getActualPrice() + "</s>"));
        holder.binding.Price.setText(productsModel.getSellingPrice());
        holder.binding.Discount.setText(productsModel.getDicount());
        holder.binding.time.setText("Added this on" + productsModel.getAddedAt());
        Picasso.get().load(productsModel.getImage()).placeholder(R.drawable.gallery).into(holder.binding.image);
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
                if (productsModel.getProductType().equals("Mobiles")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("mobileImage", list.get(position));
                    context.startActivity(intent);
                }
                if (productsModel.getProductType().equals("Fashion")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("fashion", list.get(position));
                    context.startActivity(intent);
                }
                if (productsModel.getProductType().equals("Electronics")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("Electronics", list.get(position));
                    context.startActivity(intent);
                }
                if (productsModel.getProductType().equals("Home")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("HomeAppliance", list.get(position));
                    context.startActivity(intent);
                }
                if (productsModel.getProductType().equals("Appliances")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("Appliances", list.get(position));
                    context.startActivity(intent);
                }
            }
        });
        holder.binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        FirebaseDatabase.getInstance().getReference().child("Products").child(productsModel.getProductId()).setValue(null);
                    }
                });
                alert.setNeutralButton("Help", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Press yes to remove product", Toast.LENGTH_SHORT).show();}
                });
                alert.show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        MyProductsRecyclerDesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = MyProductsRecyclerDesignBinding.bind(itemView);

        }
    }
}
