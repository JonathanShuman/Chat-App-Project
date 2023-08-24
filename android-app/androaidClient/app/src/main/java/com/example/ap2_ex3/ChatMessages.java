package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ap2_ex3.api.ContactAPI;
import com.example.ap2_ex3.api.Msg;
import com.example.ap2_ex3.api.MyApplication;
import com.example.ap2_ex3.api.OneMessage;
import com.example.ap2_ex3.api.User;
import com.example.ap2_ex3.api.addNewContact;
import com.example.ap2_ex3.api.apiChatsGet;
import com.example.ap2_ex3.api.apiChatsPost;
import com.example.ap2_ex3.api.chatPostMess;
import com.example.ap2_ex3.api.myMessage;
import com.example.ap2_ex3.entities.Contact;
import com.example.ap2_ex3.entities.MyMessage;
import com.example.ap2_ex3.entities.MyMessageDB;
import com.example.ap2_ex3.entities.MyMessageDao;
import com.example.ap2_ex3.entities.MyUser;
import com.example.ap2_ex3.entities.MyUserDB;
import com.example.ap2_ex3.entities.MyUserDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatMessages extends AppCompatActivity {
    private MyUserDB myUserDB;
    private MyUserDao myUserDao;

    private MyMessageDB myMessageDB;
    private MyMessageDao myMessageDao;

    ListView myListXML;
    List<OneMessage> myMessageList = new ArrayList<>();

    final ChatAdapter myChatAdapter = new ChatAdapter(myMessageList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myMessageDB = Room.databaseBuilder(getApplicationContext(), MyMessageDB.class, "myMessageDB")
                .allowMainThreadQueries()
                .build();
        myMessageDao = myMessageDB.myMessageDao();
        myUserDB = Room.databaseBuilder(getApplicationContext(), MyUserDB.class, "myUserDB")
                .allowMainThreadQueries()
                .build();
        myUserDao = myUserDB.myUserDao();
        setTheme(MyApplication.getCurrentTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_messages);
        MyApplication.addActivity(this);

        //handling message list.
        myListXML = findViewById(R.id.messageListView);
        myListXML.setAdapter(myChatAdapter);

        //display name!
        String name2 = getIntent().getStringExtra("name");
        TextView usernameTextViewWeClicked = findViewById(R.id.namePersonITalk);
        usernameTextViewWeClicked.setText(name2);

        //profilePic
        MyUser myUser1 = myUserDao.getPic(name2);
        String image = myUser1.getProfilePicture();
        String cleanImage = image.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
        byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ImageView picturePerson = findViewById(R.id.userTalkPictureTop);
        picturePerson.setImageBitmap(decodedByte);


        //GET MESSAGES!
        ContactAPI contactAPI1 = new ContactAPI();
        String token1 = getIntent().getStringExtra("token");
        String bearerToken1 = "Bearer " + token1;
        String chatId1 = getIntent().getStringExtra("chatId");
        Call<List<myMessage>> call1 = contactAPI1.getAllMessages(chatId1, bearerToken1);
        call1.enqueue(new Callback<List<myMessage>>() {
            @Override
            public void onResponse(Call<List<myMessage>> call, Response<List<myMessage>> response) {
                List<myMessage> myList = response.body();
                myMessageDao.deleteAll();
                for (int i = myList.size() - 1; i >= 0; i--) {
                    String date = myList.get(i).getCreated();
                    String reversedString;
                    if (!date.equals("")) {
                        String[] parts = date.split(", ");
                        String datePart = parts[0]; // "22.6.2023"
                        String timePart = parts[1]; // "15:20:34"
                        reversedString = timePart + ", " + datePart;
                    } else {
                        reversedString = "";
                    }
                    String content = myList.get(i).getContent();
                    String sender = myList.get(i).getSender().getUsername();
                    String myAcoount = getIntent().getStringExtra("username");
                    String messaId = myList.get(i).getId();
                    MyMessage myMessage = new MyMessage(content, reversedString, sender, messaId);
                    myMessageDao.insert(myMessage);
                    myMessageList.add(new OneMessage(myList.get(i).getContent(), myAcoount.equals(sender)));
                }
                myChatAdapter.notifyDataSetChanged();
                myListXML.smoothScrollToPosition(myChatAdapter.getCount() - 1);

            }

            @Override
            public void onFailure(Call<List<myMessage>> call, Throwable t) {
            }
        });

        //go back
        Button back = findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatMessages.this, ContactsWindow.class);
                String user = getIntent().getStringExtra("username");
                String token1 = getIntent().getStringExtra("token");
                intent.putExtra("username", user);
                intent.putExtra("token", token1);
                startActivity(intent);
            }
        });

        //POST MESSAGE!
        ImageButton sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myAcoount = getIntent().getStringExtra("username");
                //get token.
                String token = getIntent().getStringExtra("token");
                String bearerToken = "Bearer " + token;
                //get chatId
                String chatId = getIntent().getStringExtra("chatId");
                //get the message we want to send.
                EditText editTextMsg = findViewById(R.id.messageInput);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextMsg.getWindowToken(), 0);
                String msg = editTextMsg.getText().toString();
                Msg myMsg = new Msg(msg);
                ContactAPI contactAPI = new ContactAPI();
                Call<chatPostMess> call = contactAPI.addMessage(bearerToken, chatId, myMsg);
                call.enqueue(new Callback<chatPostMess>() {
                    @Override
                    public void onResponse(Call<chatPostMess> call, Response<chatPostMess> response) {
                        String msg = response.body().getContent();
                        String sender = response.body().getSender().getUsername();
                        myMessageList.add(new OneMessage(editTextMsg.getText().toString(), myAcoount.equals(sender)));
                        myChatAdapter.notifyDataSetChanged();
                        EditText editTextMsg = findViewById(R.id.messageInput);
                        editTextMsg.setText("");
                        myListXML.smoothScrollToPosition(myChatAdapter.getCount() - 1);
                    }

                    @Override
                    public void onFailure(Call<chatPostMess> call, Throwable t) {
                    }
                });
            }
        });
    }

    protected void onDestroy() {
        // Remove this activity from the activity list
        MyApplication.removeActivity(this);
        super.onDestroy();
    }

}