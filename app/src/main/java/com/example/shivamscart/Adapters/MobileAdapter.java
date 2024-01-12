package com.example.shivamscart.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivamscart.Models.MobileModel;
import com.example.shivamscart.ProductView;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.MobileRecyclerDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.viewHolder>{

    ArrayList<MobileModel>list;
    Context context;

    public MobileAdapter(ArrayList<MobileModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mobile_recycler_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MobileModel mobileModel=list.get(position);
        Picasso.get().load(mobileModel.getMobileImage())
                .placeholder(R.drawable.gallery)
                .into(holder.binding.mobileImage);
        holder.binding.mobileCompany.setText(Html.fromHtml("<b>"+mobileModel.getMobileCompany()+"</b>"+" "+mobileModel.getMobileDescription()));
        holder.binding.productcolor.setText(mobileModel.getMobileColor());
        holder.binding.mobileStorage.setText(mobileModel.getMobileCompany());
        holder.binding.mobileActualPrice.setText(Html.fromHtml("<s>" + mobileModel.getMobileActualPrice() + "</s>"));
        holder.binding.mobileStorage.setText(mobileModel.getMobileRam());
        holder.binding.mobilePrice.setText(mobileModel.getMobilePrice());
        holder.binding.mobileDiscount.setText(mobileModel.getMobileDicount());

        //click event On product
        holder.binding.mobileDesignRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change the background color temporarily
                int originalColor = Color.parseColor("#FFFFFFFF");
                int clickedColor = Color.parseColor("#E8E8E8"); // Replace with your desired color

                // Change background color when clicked
                holder.binding.shivam.setBackgroundColor(clickedColor);
                holder.binding.mobileImage.setBackgroundColor(clickedColor);

                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                holder.binding.shivam.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.shivam.setBackgroundColor(originalColor);
                    }
                }, 100);
                // Set a delayed runnable to revert the color after a short duration (e.g., 500 milliseconds)
                holder.binding.mobileImage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.mobileImage.setBackgroundColor(originalColor);
                    }
                }, 100);

                Intent intent=new Intent(context, ProductView.class);
                intent.putExtra("mobileImage",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        MobileRecyclerDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=MobileRecyclerDesignBinding.bind(itemView);
        }
    }
}
