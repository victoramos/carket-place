package com.example.victor.carketplace.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import com.example.victor.carketplace.R;
import com.example.victor.carketplace.view.adapter.Adapter;
import com.example.victor.carketplace.view.utils.EndlessScrollListener;
import com.example.victor.carketplace.viewmodel.ProductViewModel;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static ProductViewModel sProductViewModel;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Adapter mAdapter;
    private ProgressBar mLoading;

    private boolean mIsFetching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sProductViewModel = new ProductViewModel(getApplication());
        mLoading = findViewById(R.id.progress_loading);
        mAdapter = new Adapter(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        EndlessScrollListener endlessScrollListener = new EndlessScrollListener(mLayoutManager);
        mRecyclerView.addOnScrollListener(endlessScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this::redirectToProductDetail);

        mCompositeDisposable.add(endlessScrollListener.asObservable().subscribe( (nextPage) -> {
            if(!mIsFetching){
                mLoading.setVisibility(View.VISIBLE);
                getProducts(nextPage);
            }
        }));

        getProducts(0);
    }

    @OnClick(R.id.image_cart)
    void onCartClick(){
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    void getProducts(Integer page){
        Disposable disposable = sProductViewModel.getAllProducts(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mIsFetching = false;
                    mLoading.setVisibility(View.GONE);
                })
                .subscribe( (products) -> mAdapter.setData(products));

        mCompositeDisposable.add(disposable) ;
    }

    void redirectToProductDetail(Long productId){
        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
        Bundle b = new Bundle();
        b.putLong("key", productId);
        intent.putExtras(b);

        startActivity(intent);
    }
}
