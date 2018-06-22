package com.example.victor.carketplace.repository;

import android.app.Application;
import com.example.victor.carketplace.database.LocalDatabase;
import com.example.victor.carketplace.database.dao.CartDAO;
import com.example.victor.carketplace.database.model.CartItem;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class CartRepository {
    private CartDAO mCartDAO;

    public CartRepository(Application application){
        mCartDAO = LocalDatabase.getInstance(application).cartDAO();
    }

    public Flowable<List<CartItem>> getAllItems(){
        return mCartDAO.getAll();
    }

    public Observable<Long> storeCartItemInDb(CartItem item) {
        return Observable.fromCallable(() -> mCartDAO.addToCart(item));
    }

    public Observable<Integer> deleteCartItem(CartItem item){
        return Observable.fromCallable(() -> mCartDAO.delete(item));
    }
}
