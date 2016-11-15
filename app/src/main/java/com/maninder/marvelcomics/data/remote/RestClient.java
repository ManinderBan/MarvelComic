package com.maninder.marvelcomics.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Maninder on 14/11/16.
 */

public class RestClient {

    /**
     * This is our main backend/server URL.
     */
    public static final String REST_API_URL = "http://gateway.marvel.com/";

    private static Retrofit comicsRetrofit;

    /**
     * Set 10 second time outs, after that retrofit get an error.
     */
    static {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        comicsRetrofit = new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static <T> T getService(Class<T> serviceClass) {
        return comicsRetrofit.create(serviceClass);
    }
}
