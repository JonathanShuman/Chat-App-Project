package com.example.ap2_ex3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ap2_ex3.api.ContactAPI;
import com.example.ap2_ex3.api.MyApplication;
import com.example.ap2_ex3.api.User;
import com.example.ap2_ex3.api.UserAPI;
import com.example.ap2_ex3.api.addNewContact;
import com.example.ap2_ex3.api.apiChatsGet;
import com.example.ap2_ex3.api.apiChatsPost;
import com.example.ap2_ex3.entities.Contact;
import com.example.ap2_ex3.entities.MyUser;
import com.example.ap2_ex3.entities.MyUserDB;
import com.example.ap2_ex3.entities.MyUserDao;

//import com.example.ap2_ex3.entities.ChatDao;
//import com.example.ap2_ex3.entities.DBContactHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsWindow extends AppCompatActivity {
    private MyUserDB myUserDB;
    private MyUserDao myUserDao;
    ListView myListXML;
    List<Contact> myContactList = new ArrayList<>();
    final ContactAdapter myContactAdapter = new ContactAdapter(myContactList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myUserDB = Room.databaseBuilder(getApplicationContext(), MyUserDB.class, "myUserDB")
                .allowMainThreadQueries()
                .build();
        myUserDao = myUserDB.myUserDao();
        setTheme(MyApplication.getCurrentTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_window);
        MyApplication.addActivity(this);
        String username = getIntent().getStringExtra("username");
        String token = getIntent().getStringExtra("token");
        String displayName = getIntent().getStringExtra("displayName");
        String profilePicture = getIntent().getStringExtra("profilePicture");

        //handling list contact.
        myListXML = findViewById(R.id.contactListView);
        myListXML.setAdapter(myContactAdapter);

        //GET USER.
        UserAPI userAPI = new UserAPI();
        String bearerToken = "Bearer " + token;
        String y = username;
        Call<User> call = userAPI.getLoggedInUser(username, bearerToken);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                //set displayName.
                TextView usernameTextView = findViewById(R.id.usernameTextView);
                usernameTextView.setText(response.body().getDisplayName());
                //set picture.
                String x = response.body().getProfilePic().toString();
                String cleanImage = x.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
                byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ImageView userPictureTop = findViewById(R.id.userPictureTop);
                // Bitmap profileBitmap = decodeBase64(x);
                userPictureTop.setImageBitmap(decodedByte);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

        //GET CHATS.
        ContactAPI contactAPI2 = new ContactAPI();
        String bearerToken2 = "Bearer " + token;
        Call<List<apiChatsGet>> call2 = contactAPI2.getContacts(bearerToken2);
        call2.enqueue(new Callback<List<apiChatsGet>>() {
            @Override
            public void onResponse(Call<List<apiChatsGet>> call, Response<List<apiChatsGet>> response) {
                response.isSuccessful();
                List<apiChatsGet> myList = response.body();
                //pass all the data to my myUserDB and my List.
                myUserDao.deleteAll();
                for (int i = 0; i < myList.size(); i++) {
                    String reversedString;
                    String date = myList.get(i).getLastMessage().getCreated();
                    if (!date.equals("")) {
                        String[] parts = date.split(", ");
                        String datePart = parts[0]; // "22.6.2023"
                        String timePart = parts[1]; // "15:20:34"
                        reversedString = timePart + ", " + datePart;
                    } else {
                        reversedString = "";
                    }

                    MyUser myUser = new MyUser(myList.get(i).getId(), myList.get(i).getUser().getUsername(), myList.get(i).getUser().getDisplayName(), myList.get(i).getLastMessage().getContent(),
                            reversedString, myList.get(i).getUser().getProfilePic());
                    myUserDao.insert(myUser);

                    Contact contact = new Contact(myList.get(i).getUser().getDisplayName(), myList.get(i).getLastMessage().getContent(),
                            reversedString, myList.get(i).getUser().getProfilePic());
                    myContactList.add(contact);
                }
                myContactAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<apiChatsGet>> call, Throwable t) {
            }
        });


        Button addContactButton = findViewById(R.id.addContactButton);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(bearerToken);
            }
        });

        Button logedOut = findViewById(R.id.logoutButton);
        logedOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsWindow.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        myListXML.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(ContactsWindow.this, ChatMessages.class);
            TextView usernameClicked = view.findViewById(R.id.displaynameTextView);
            String name = usernameClicked.getText().toString();
            //Retrieve the id from the selected person
            MyUser myUser = myUserDao.getName(name);
            String chatId = myUser.getId();
            intent.putExtra("chatId", chatId);
            //pass token.
            intent.putExtra("token", token);
            intent.putExtra("username", username);

            intent.putExtra("name", name);





            startActivity(intent);
        });
    }

    private Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    private void showPopupWindow(String bearerToken) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_contact_popup, null);
        final EditText stringEditText = popupView.findViewById(R.id.stringEditText);
        Button addButton = popupView.findViewById(R.id.addButton);
        Button exitButton = popupView.findViewById(R.id.exitButton);
        final AlertDialog popupWindow = new AlertDialog.Builder(this)
                .setView(popupView)
                .create();

        //cancel add contact.
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //POST Chats.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(bearerToken);
                String contactUserName = stringEditText.getText().toString();
                ContactAPI contactAPI = new ContactAPI();
                Call<apiChatsPost> call = contactAPI.addContact(new addNewContact(contactUserName), bearerToken);
                call.enqueue(new Callback<apiChatsPost>() {
                    @Override
                    public void onResponse(Call<apiChatsPost> call, Response<apiChatsPost> response) {
                        if (!response.isSuccessful()) {
                            stringEditText.setError("Username not exist" + response.code());

                        } else {
                            String myUsername = getIntent().getStringExtra("username");
                            // assert response.body() != null;
                            String chatId = response.body().getId();
                            String contactUsername = response.body().getUser().getUsername();
                            String addUserDisplayName = response.body().getUser().getDisplayName();
                            String addUserPorofilePic = response.body().getUser().getProfilePic();
                            MyUser newMyUser = new MyUser(chatId, contactUsername, addUserDisplayName, "n", "n", addUserPorofilePic);
                            myUserDao.insert(newMyUser);
                            myContactList.add(new Contact(addUserDisplayName, "", "", addUserPorofilePic));
                            myContactAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<apiChatsPost> call, Throwable t) {
                    }
                });

            }
        });

        popupWindow.show();
    }


    protected void onDestroy() {
        MyApplication.removeActivity(this);
        super.onDestroy();
    }


}