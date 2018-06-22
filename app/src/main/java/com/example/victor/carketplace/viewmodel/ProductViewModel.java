package com.example.victor.carketplace.viewmodel;

import android.app.Application;
import com.example.victor.carketplace.database.model.Product;
import com.example.victor.carketplace.database.model.ProductDetail;
import com.example.victor.carketplace.repository.ProductRepository;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class ProductViewModel {
    private ProductRepository mRepositoryManager;

    public ProductViewModel(Application application){
        mRepositoryManager = new ProductRepository(application);
    }

    public Observable<List<Product>> getAllProducts() {
        return mRepositoryManager.getAllProducts();
    }

    public Flowable<ProductDetail> getProductDetail(Long productId) {
        return mRepositoryManager.getProductDetail(productId);
    }
}
