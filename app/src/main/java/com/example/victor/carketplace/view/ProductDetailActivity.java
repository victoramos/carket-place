package com.example.victor.carketplace.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.victor.carketplace.R;
import com.example.victor.carketplace.database.model.CartItem;
import com.example.victor.carketplace.database.model.ProductDetail;
import com.example.victor.carketplace.view.dialog.ConfirmationDialog;
import com.example.victor.carketplace.viewmodel.CartViewModel;
import com.example.victor.carketplace.viewmodel.ProductViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailActivity extends AppCompatActivity {
    private ProductViewModel mProductViewModel;
    private CartViewModel mCartViewModel;
    private ProductDetail mProductDetail;

    @BindView(R.id.image_product_thumb)
    ImageView productThumb;

    @BindView(R.id.text_name)
    TextView name;

    @BindView(R.id.text_brand)
    TextView brand;

    @BindView(R.id.text_description)
    TextView description;

    @BindView(R.id.text_price)
    TextView price;

    private long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mProductViewModel = new ProductViewModel(getApplication());
        mCartViewModel = new CartViewModel(getApplication());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle b = getIntent().getExtras();
        value = -1; // or other values
        if(b != null)
            value = b.getLong("key");

        mProductViewModel.getProductDetail(value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((productDetail) -> {
                    mProductDetail = productDetail;

                    Glide.with(ProductDetailActivity.this)
                            .load(productDetail.getImage())
                            .into(productThumb);

                    name.setText(productDetail.getName());
                    brand.setText(productDetail.getModel());
                    description.setText(productDetail.getDescription());
                    price.setText(productDetail.getPrice().toString());
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.button_buy)
    void OnBuyClick(){
        showEditDialog(mProductDetail.getName(), mProductDetail.getAmount());
    }

    @OnClick(R.id.image_cart)
    void onCartClick(){
        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
        startActivity(intent);
    }

    private void showEditDialog(String dialogTitle, Integer total) {
        ConfirmationDialog newFragment = ConfirmationDialog.newInstance(dialogTitle, total);
        newFragment.resultSubject.subscribe((count) -> {
            CartItem cartItem = mCartViewModel.createCartItem(mProductDetail, count);
            mCartViewModel.storeCartItemInDb(cartItem);

            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);
        });
        newFragment.show(getSupportFragmentManager(), "dialog");
    }
}
