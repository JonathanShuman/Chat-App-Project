package com.example.ap2_ex3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ap2_ex3.api.MyApplication;
import com.example.ap2_ex3.api.TokenAPI;
import com.example.ap2_ex3.api.User;
import com.example.ap2_ex3.api.UserAPI;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApplication.getCurrentTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyApplication.addActivity(this);
        //get user name
        EditText editTextUserName = findViewById(R.id.editTextUserNameLogIn);
        //get password
        EditText editTextPassword = findViewById(R.id.editTextPasswordLogIn);
        //move to sign Up page.
        TextView textViewSignUp = findViewById(R.id.textViewSignUp);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //go to settings activity.
        ImageView changeSettings = findViewById(R.id.imageButtonSettings);
        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, settingsActivity.class);
                intent.putExtra("sourceActivity", "LoginActivity");
                startActivity(intent);
            }
        });

        //click on login button- create token + get firebaseToken.
        Button buttonLogin = findViewById(R.id.buttonLogIn);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = getIntent().getStringExtra("displayName");
                //String profilePicture = getIntent().getStringExtra("profilePicture");
                boolean isValid = true;
                //check username is not empty.
                String username = editTextUserName.getText().toString();
                if (username.isEmpty()) {
                    editTextUserName.setError("field can't be empty");
                    isValid = false;
                } else {
                    editTextUserName.setError(null); // Clear previous error if any
                }
                //check password is not empty.
                String password = editTextPassword.getText().toString();
                if (password.isEmpty()) {
                    editTextPassword.setError("field can't be empty");
                    isValid = false;
                } else {
                    editTextPassword.setError(null); // Clear previous error if any
                }
                TokenAPI tokenAPI = new TokenAPI();
                if (isValid) {
                    //FIREBASE!!!!!!!!!!!
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
                        String firebaseToken = instanceIdResult.getToken();
                        UserAPI userAPI2 = new UserAPI();
                        Call<Void> fbToken = userAPI2.postNewFirebaseToken(username, firebaseToken);
                        fbToken.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                response.isSuccessful();
                            }
                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            }
                        });
                    });

                    //get token.
                    Call<String> call = tokenAPI.getToken(username, password);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (!response.isSuccessful()) {
                                editTextUserName.setError("Username/password is not correct");
                            } else {
                                String token = response.body();
                                Intent intent = new Intent(LoginActivity.this, ContactsWindow.class);
                                intent.putExtra("username", username);
                                intent.putExtra("token", token);
                                intent.putExtra("displayName", displayName);
                               // intent.putExtra("profilePicture", profilePicture);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        }
                    });

                }
            }
        });
    }

    protected void onDestroy() {
        MyApplication.removeActivity(this);

        super.onDestroy();
    }


}