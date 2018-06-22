package com.example.victor.carketplace.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.example.victor.carketplace.R;
import com.example.victor.carketplace.view.adapter.Adapter;
import com.example.victor.carketplace.viewmodel.ProductViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static ProductViewModel sProductViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sProductViewModel = new ProductViewModel(getApplication());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final Adapter mAdapter = new Adapter(getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(id -> {
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            Bundle b = new Bundle();
            b.putLong("key", id);
            intent.putExtras(b);

            startActivity(intent);
        });

        sProductViewModel.getAllProducts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((products -> mAdapter.setData(products)));
    }

    @OnClick(R.id.image_cart)
    void onCartClick(){
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        startActivity(intent);
    }
}
