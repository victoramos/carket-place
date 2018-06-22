package com.example.victor.carketplace.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.victor.carketplace.R;
import com.example.victor.carketplace.database.model.Product;
import java.util.ArrayList;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Product> mData = new ArrayList<>();
    private Context mContext;
    private OnItemClick mListener;

    public Adapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Product product = mData.get(position);

        Glide.with(mContext).load(product.getImage()).into(holder.productThumb);

        holder.model.setText(product.getModel());
        holder.name.setText(product.getName());
        holder.value.setText(mContext.getResources().getString(R.string.app_coin, product.getPrice()));

        holder.itemView.setOnClickListener(v -> mListener.onItemClick(mData.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Product> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setClickListener(OnItemClick listener) {
        this.mListener = listener;
    }

    public interface OnItemClick{
        void onItemClick(long id);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView productThumb;
        final TextView name;
        final TextView model;
        final TextView value;
        final Button deleteBtn;

        ViewHolder(View itemView) {
            super(itemView);

            productThumb = itemView.findViewById(R.id.image_product);
            name = itemView.findViewById(R.id.text_name);
            model = itemView.findViewById(R.id.text_model);
            value = itemView.findViewById(R.id.text_value);
            deleteBtn = itemView.findViewById(R.id.remove_btn);
        }
    }
}