package com.example.shivamscart.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivamscart.Models.MobileModel;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.ProductSuggestionRecyclerDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductSuugestionAdapter extends RecyclerView.Adapter<ProductSuugestionAdapter.viewHolder>{

    ArrayList<MobileModel>list;
    Context context;

    public ProductSuugestionAdapter(ArrayList<MobileModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_suggestion_recycler_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MobileModel mobileModel=list.get(position);
        Picasso.get().load(mobileModel.getMobileImage())
                .placeholder(R.drawable.gallery)
                .into(holder.binding.MobImage);
        holder.binding.mobileCompany.setText(mobileModel.getMobileCompany());
        holder.binding.mobileModel.setText(mobileModel.getMobileDescription());
        holder.binding.mobileActualPrice.setText(Html.fromHtml("<s>" + mobileModel.getMobileActualPrice() + "</s>"));
        holder.binding.mobilePrice.setText(mobileModel.getMobilePrice());
        holder.binding.mobileDiscount.setText(mobileModel.getMobileDicount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ProductSuggestionRecyclerDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ProductSuggestionRecyclerDesignBinding.bind(itemView);
        }
    }
}
