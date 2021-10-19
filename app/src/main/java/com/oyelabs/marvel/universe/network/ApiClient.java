package com.oyelabs.marvel.universe.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    private static final String BASE_URL = "https://gateway.marvel.com";
    private static Retrofit retrofit;

    private static  Retrofit getInstance()
    {
        if (retrofit == null){
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                     .client(getRetrofitClent())
                    .build();
        }
        return retrofit;
    }

     private static OkHttpClient getRetrofitClent() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(30, TimeUnit.SECONDS);
        client.connectTimeout(30, TimeUnit.SECONDS);
        return client.build();
    }

    public static ApiInterface getApi()
    {
        return getInstance().create(ApiInterface.class);
    }
}
