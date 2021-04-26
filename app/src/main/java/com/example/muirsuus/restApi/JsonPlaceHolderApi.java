package com.example.muirsuus.restApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    @GET("api/token-auth/")
    Call<List<Post>> getPosts();

    @POST("api/token-auth/")
    Call<Post> createPost(@Body Post post);


    @GET("api/me/")
    Call<User> clientInfo(@Header("Authorization") String token);

    @GET("static/otd/img/1E805CB7-90AF-4135-8FDD-90396134F7F2.fa7c2263.png")
    Call<ResponseBody> downloadImage();


}
