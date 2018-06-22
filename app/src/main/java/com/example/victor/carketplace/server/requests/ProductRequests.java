package com.example.victor.carketplace.server.requests;

import com.example.victor.carketplace.database.model.Product;
import com.example.victor.carketplace.database.model.ProductDetail;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductRequests {
    @GET("carro")
    Observable<List<Product>> getAllProducts();

    @GET("carro/{id}")
    Flowable<ProductDetail> getProductDetail(@Path("id") long id);
}
