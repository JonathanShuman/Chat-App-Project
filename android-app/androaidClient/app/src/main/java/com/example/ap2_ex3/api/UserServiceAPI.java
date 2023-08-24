package com.example.ap2_ex3.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserServiceAPI {
    @POST("Tokens")
    Call<String> getToken(@Body generateToken mytoken);
    @POST("Users")
    Call<Void> postNewUser(@Body fullUser myUser);
    @GET("Users/{username}")
    Call<User> getLoggedInUser(@Path("username") String username, @Header("Authorization") String token);
    @POST("getFirebaseToken")
    Call<Void> postNewFirebaseToken(@Body fbToken userfb);
}