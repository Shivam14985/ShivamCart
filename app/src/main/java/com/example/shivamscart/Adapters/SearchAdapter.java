package com.example.shivamscart.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class SearchAdapter extends FirebaseRecyclerAdapter<ProductsModel,SearchAdapter.viewHolder>{

    public SearchAdapter(@NonNull FirebaseRecyclerOptions<ProductsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull ProductsModel model) {
        Picasso.get().load(model.getImage()).placeholder(R.drawable.gallery).into(holder.binding.image);
        holder.binding.company.setText(Html.fromHtml("<b>" + model.getCompany() + "</b>"+" "+model.getModel()));
        holder.binding.color.setText(model.getColor());
        holder.binding.mobileActualPrice.setText(Html.fromHtml("<s>" + model.getActualPrice() + "</s>"));
        holder.binding.mobilePrice.setText(model.getSellingPrice());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recyclerview_design,parent,false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SearchRecyclerviewDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SearchRecyclerviewDesignBinding.bind(itemView);
        }
    }
}