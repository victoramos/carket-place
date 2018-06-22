package com.example.victor.carketplace.server;





import com.example.victor.carketplace.server.requests.ProductRequests;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServerAPI {
    private static ServerAPI sInstance;
    private Retrofit mRetrofit;

    public ProductRequests product() {
        return mRetrofit.create(ProductRequests.class);
    }

    private ServerAPI(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ServerConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static ServerAPI getInstance(){
        if (sInstance == null){
            sInstance = new ServerAPI();
        }
        return sInstance;
    }
}
