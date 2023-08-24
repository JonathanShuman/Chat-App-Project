package com.example.ap2_ex3.api;

import com.example.ap2_ex3.entities.MyMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ContactServiceAPI {

    @GET("Chats")
    Call<List<apiChatsGet>> getContacts(@Header("Authorization") String token);

    @POST("Chats")
    Call<apiChatsPost> addContact(@Body addNewContact newContact, @Header("Authorization") String token);

    @GET("Chats/{chatid}/Messages")
    Call<List<myMessage>> getAllMessages(@Path("chatid") String chatid, @Header("Authorization") String token);

    @GET("Chats/{chatid}")
    Call<chatGetMess> getMessages(@Path("chatid") String chatid, @Header("Authorization") String token);

    @POST("Chats/{chatid}/Messages")
    Call<chatPostMess> addMessage(@Header("Authorization") String token, @Path("chatid") String chatid, @Body Msg msg);
}

