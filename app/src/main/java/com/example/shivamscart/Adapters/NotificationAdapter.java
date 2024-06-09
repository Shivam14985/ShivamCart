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
import com.example.shivamscart.databinding.NotificationLayoutBinding;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {
    ArrayList<ProductsModel> list;
    Context context;

    public NotificationAdapter(ArrayList<ProductsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel productsModel = list.get(position);
        Picasso.get().load(productsModel.getImage()).placeholder(R.drawable.gallery).into(holder.binding.icon);
        holder.binding.title.setText(Html.fromHtml("<b>" + productsModel.getCompany() + " " + productsModel.getModel() + "</b>" + " at " + "<b>" + productsModel.getDicount() + "% discount" + "</b>"));
        String timeAgo = TimeAgo.using(productsModel.getAddedAt());
        holder.binding.addedat.setText(timeAgo);
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").child(productsModel.getProductId()).child("notificationOpen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().equals("true")) {
                    holder.binding.Clicke.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.binding.icon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.binding.Clicke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productsModel.getProductType().equals("Mobiles")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("mobileImage", list.get(position));
                    context.startActivity(intent);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").child(productsModel.getProductId()).child("notificationOpen").setValue("true");
                }
                if (productsModel.getProductType().equals("Fashion")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("fashion", list.get(position));
                    context.startActivity(intent);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").child(productsModel.getProductId()).child("notificationOpen").setValue("true");

                }
                if (productsModel.getProductType().equals("Electronics")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("Electronics", list.get(position));
                    context.startActivity(intent);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").child(productsModel.getProductId()).child("notificationOpen").setValue("true");

                }
                if (productsModel.getProductType().equals("Home")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("HomeAppliance", list.get(position));
                    context.startActivity(intent);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").child(productsModel.getProductId()).child("notificationOpen").setValue("true");

                }
                if (productsModel.getProductType().equals("Appliances")) {
                    Intent intent = new Intent(context, ProductView.class);
                    intent.putExtra("Appliances", list.get(position));
                    context.startActivity(intent);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("Notification").child(productsModel.getProductId()).child("notificationOpen").setValue("true");

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        NotificationLayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NotificationLayoutBinding.bind(itemView);
        }
    }
}
