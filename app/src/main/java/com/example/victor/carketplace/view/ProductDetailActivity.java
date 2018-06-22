package com.example.victor.carketplace.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.victor.carketplace.R;
import com.example.victor.carketplace.database.model.CartItem;
import com.example.victor.carketplace.database.model.ProductDetail;
import com.example.victor.carketplace.view.dialog.ConfirmationDialog;
import com.example.victor.carketplace.view.dialog.FullCartErrorDialog;
import com.example.victor.carketplace.view.dialog.UnknownErrorDialog;
import com.example.victor.carketplace.viewmodel.CartViewModel;
import com.example.victor.carketplace.viewmodel.ProductViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ProductDetailActivity extends AppCompatActivity {
    private ProductViewModel mProductViewModel;
    private CartViewModel mCartViewModel;
    private ProductDetail mProductDetail;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

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
        long value = -1;
        if(b != null)
            value = b.getLong("key");

        mCompositeDisposable.add(mProductViewModel.getProductDetail(value)
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
                    price.setText(getResources().getString(R.string.app_coin, productDetail.getPrice()));
                },(error) -> onUnknownError()));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.button_buy)
    void OnBuyClick(){
        mCartViewModel.getCartTotal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((total) -> {
                    if(mCartViewModel.checkCartValue(total)){
                        total = mCartViewModel.getMaxCartTotal() - total;

                        ConfirmationDialog confirmationDialog = ConfirmationDialog
                                .newInstance(mProductDetail.getName(), mProductDetail.getAmount(),
                                        total, mProductDetail.getPrice());

                        mCompositeDisposable.add(confirmationDialog.getObservable().subscribe((count) -> {
                            CartItem cartItem = mCartViewModel.createCartItem(mProductDetail, count);
                            mCartViewModel.storeCartItemInDb(cartItem);

                            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                            startActivity(intent);
                        }));
                        confirmationDialog.show(getSupportFragmentManager(), "dialog");
                    }else{
                        FullCartErrorDialog dialog = new FullCartErrorDialog();
                        dialog.getObservable().doOnComplete( () -> {
                            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                            startActivity(intent);
                        }).subscribe();
                        dialog.getDialog(ProductDetailActivity.this).show();
                    }
                });
    }

    @OnClick(R.id.image_cart)
    void onCartClick(){
        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCompositeDisposable.dispose();
    }

    private void onUnknownError() {
        UnknownErrorDialog dialog = new UnknownErrorDialog();
        dialog.getObservable().doOnComplete(ProductDetailActivity.this::finish).subscribe();
        dialog.getDialog(ProductDetailActivity.this).show();
    }
}
