package com.example.ap2_ex3.api;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.ap2_ex3.entities.Contact;
import com.example.ap2_ex3.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class ContactAPI {
    Retrofit retrofit;
    ContactServiceAPI contactServiceAPI;

    public ContactAPI() {
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
        contactServiceAPI = retrofit.create(ContactServiceAPI.class);
    }

    public Call<apiChatsPost> addContact(addNewContact newContact, String token) {
        return contactServiceAPI.addContact(newContact, token);
    }

    public Call<List<apiChatsGet>> getContacts(String token) {
        return contactServiceAPI.getContacts(token);
    }

    public Call<chatPostMess> addMessage(String token, String chatId, Msg msg) {
        return contactServiceAPI.addMessage(token, chatId, msg);
    }

    public Call<List<myMessage>> getAllMessages(String chatId, String token) {
        return contactServiceAPI.getAllMessages(chatId, token);
    }

}