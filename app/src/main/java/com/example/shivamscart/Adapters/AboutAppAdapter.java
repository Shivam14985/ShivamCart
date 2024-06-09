package com.example.shivamscart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shivamscart.Models.AboutAppModel;
import com.example.shivamscart.R;
import com.example.shivamscart.databinding.AboutAppRecyclerDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AboutAppAdapter extends RecyclerView.Adapter<AboutAppAdapter.ViewHolder>{

    ArrayList<AboutAppModel>list;
    Context context;

    public AboutAppAdapter(ArrayList<AboutAppModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.about_app_recycler_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AboutAppModel model=list.get(position);
        holder.binding.AboutAppDescription.setText(model.getAboutDiscription());
        Picasso.get().load(model.getAboutImage()).placeholder(R.drawable.gallery).into(holder.binding.AboutAppImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AboutAppRecyclerDesignBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=AboutAppRecyclerDesignBinding.bind(itemView);
        }
    }
}
