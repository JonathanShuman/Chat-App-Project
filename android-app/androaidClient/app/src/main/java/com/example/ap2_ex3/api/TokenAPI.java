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

public class TokenAPI {
    Retrofit retrofit;
    UserServiceAPI userServiceAPI;

    public TokenAPI() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
        String serverHost = sharedPrefs.getString("pref_server_url", MyApplication.context.getString(R.string.BaseURLHost));
        String serverPort = sharedPrefs.getString("pref_server_port", MyApplication.context.getString(R.string.BaseURLPort));

        String completeBaseUrl = MyApplication.context.getString(R.string.BaseURLProtocol) +
                serverHost +
                ":" +
                serverPort +
                MyApplication.context.getString(R.string.BaseURLPath);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(completeBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        userServiceAPI = retrofit.create(UserServiceAPI.class);
    }

    public Call<String> getToken(String username, String password) {
        return userServiceAPI.getToken(new generateToken(username, password));
    }

}


