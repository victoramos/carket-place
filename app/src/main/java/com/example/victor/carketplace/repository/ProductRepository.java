package com.example.victor.carketplace.repository;

import android.app.Application;

import com.example.victor.carketplace.database.model.Product;
import com.example.victor.carketplace.database.model.ProductDetail;
import com.example.victor.carketplace.server.ServerAPI;
import com.example.victor.carketplace.server.requests.ProductRequests;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ProductRepository {
    private ProductRequests mProductRequests;

    public ProductRepository(Application application){
        mProductRequests = ServerAPI.getInstance().product();
    }

    public Observable<List<Product>> getAllProducts(){
        return mProductRequests.getAllProducts();
    }

    public Flowable<ProductDetail> getProductDetail(Long productId){
        return mProductRequests.getProductDetail(productId);
    }
}
