package com.example.muirsuus.restApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Класс, который возвращает объект класса JsonPlaceHolderApi
 * создан с целью уменьшения кода, при работе с сервером
 */

public class ClientServerWorker {

    private final String baseUrl;
    private JsonPlaceHolderApi instance;

    public ClientServerWorker(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * @return instance JsonPlaceHolderApi
     */
    public JsonPlaceHolderApi getInstance() {
        if (instance == null) {
            synchronized (new Object()) {

                Gson gson = new GsonBuilder()
                        .serializeNulls()
                        .create();

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();
                instance = retrofit.create(JsonPlaceHolderApi.class);
            }
        }

        return instance;
    }
}
