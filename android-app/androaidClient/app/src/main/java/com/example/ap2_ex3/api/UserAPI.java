package com.example.ap2_ex3.api;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.preference.PreferenceManager;

import com.example.ap2_ex3.R;
import com.example.ap2_ex3.LoginActivity;
import com.example.ap2_ex3.RegisterActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;  // Import HashMap instead of Map

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    UserServiceAPI userServiceAPI;


    public UserAPI() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
        String serverHost = sharedPrefs.getString("pref_server_url", MyApplication.context.getString(R.string.BaseURLHost));
        String serverPort = sharedPrefs.getString("pref_server_port", MyApplication.context.getString(R.string.BaseURLPort));

        String completeBaseUrl = MyApplication.context.getString(R.string.BaseURLProtocol) +
                serverHost +
                ":" +
                serverPort +
                MyApplication.context.getString(R.string.BaseURLPath);
        retrofit = new Retrofit.Builder()
                .baseUrl(completeBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userServiceAPI = retrofit.create(UserServiceAPI.class);
    }

    public Call<Void> postNewUser(String username, String password, String displayName, String profilePicture) {
        // return userServiceAPI.postNewUser(Map.of("username", username, "password", password, "displayName", displayName, "profilePicture", profilePicture));
        return userServiceAPI.postNewUser(new fullUser(username, password, displayName, profilePicture));
    }

    public Call<User> getLoggedInUser(String username, String token) {
        return userServiceAPI.getLoggedInUser(username, token);
    }

    public Call<Void> postNewFirebaseToken(String username, String token) {
        return userServiceAPI.postNewFirebaseToken(new fbToken(username, token));
    }

}
