package com.example.victor.carketplace.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.victor.carketplace.database.dao.CartDAO;
import com.example.victor.carketplace.database.model.CartItem;
import com.example.victor.carketplace.database.model.Product;

@Database(entities = {Product.class, CartItem.class}, version = 5)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase sInstance;

    public abstract CartDAO cartDAO();

    public static LocalDatabase getInstance(Context context){
        if (sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDatabase.class, "database-name").build();
        }
        return sInstance;
    }
}
