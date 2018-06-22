package com.example.victor.carketplace.viewmodel;

import android.app.Application;
import android.util.Log;
import com.example.victor.carketplace.database.model.CartItem;
import com.example.victor.carketplace.database.model.ProductDetail;
import com.example.victor.carketplace.repository.CartRepository;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CartViewModel {
    private int mMaxCartValue = 100000;
    private CartRepository mRepositoryManager;

    public CartViewModel(Application application){
        mRepositoryManager = new CartRepository(application);
    }

    public Flowable<List<CartItem>> getAllItems(){
        return mRepositoryManager.getAllItems();
    }

    public Single<Integer> getCartTotal(){
        return mRepositoryManager.getCartTotal().onErrorReturn( (error) -> 0);
    }

    public void storeCartItemInDb(CartItem item) {
        mRepositoryManager.storeCartItemInDb(item)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe((itemID) -> Log.d("Cart", "New Item added to cart"));
    }

    public void deleteCartItem(CartItem item) {
        mRepositoryManager.deleteCartItem(item)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe((itemID) -> Log.d("Cart", "Item removed to cart"));
    }

    public CartItem createCartItem(ProductDetail product, Integer amount){
        CartItem item = new CartItem();
        item.setAmount(amount);
        item.setId(product.getId());
        item.setPrice(product.getPrice());
        item.setImage(product.getImage());
        item.setName(product.getName());
        item.setModel(product.getModel());

        return item;
    }

    public boolean checkCartValue(Integer currentValue){
        return currentValue < mMaxCartValue;
    }

    public Integer getMaxCartTotal() {
        return mMaxCartValue;
    }
}
