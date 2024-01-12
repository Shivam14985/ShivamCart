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
import com.example.shivamscart.databinding.CartRecyclerDesignBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewHolder>{

    ArrayList<MobileModel>list;
    Context context;

    public CartAdapter(ArrayList<MobileModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_recycler_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CartRecyclerDesignBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CartRecyclerDesignBinding.bind(itemView);
        }
    }
}
