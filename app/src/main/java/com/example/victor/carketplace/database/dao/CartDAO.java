package com.example.victor.carketplace.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.victor.carketplace.database.model.CartItem;
import com.example.victor.carketplace.database.model.Product;

import java.util.List;
import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM cart_item")
    Flowable<List<CartItem>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addToCart(CartItem c);

    @Delete
    int delete(CartItem cartItem);
}
