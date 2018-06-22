package com.example.victor.carketplace.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.victor.carketplace.R;
import com.example.victor.carketplace.database.model.CartItem;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context mContext;
    private List<CartItem> mCartItems = new ArrayList<>();

    public static final Subject<CartItem> removeSubject = PublishSubject.create();

    public CartAdapter(Context context) {
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView productThumb;
        public final TextView name;
        public final TextView brand;
        public final TextView price;
        public final TextView amount;
        public final ImageView removeBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            productThumb = itemView.findViewById(R.id.image_thumb);
            name = itemView.findViewById(R.id.text_name);
            brand = itemView.findViewById(R.id.text_brand);
            price = itemView.findViewById(R.id.text_price);
            amount = itemView.findViewById(R.id.text_amount);
            removeBtn = itemView.findViewById(R.id.image_remove);
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);

        return new CartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartItem cartItem = mCartItems.get(position);

        Glide.with(mContext).load(cartItem.getImage()).into(holder.productThumb);

        holder.brand.setText(cartItem.getModel());
        holder.name.setText(cartItem.getName());
        holder.price.setText(cartItem.getPrice().toString());
        holder.amount.setText(cartItem.getAmount().toString());

        holder.removeBtn.setOnClickListener(v -> removeSubject.onNext(cartItem));
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    public void setData(List<CartItem> items){
        mCartItems = items;
        notifyDataSetChanged();
    }
}