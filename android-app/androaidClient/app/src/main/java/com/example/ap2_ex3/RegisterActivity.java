package com.example.ap2_ex3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ap2_ex3.api.MyApplication;
import com.example.ap2_ex3.api.UserAPI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final int REQUEST_PICK_IMAGE = 1;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MyApplication.getCurrentTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        MyApplication.addActivity(this);
        //get user name
        EditText editTextUserName = findViewById(R.id.editTextUserName);
        //get password
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        //get confirm passwrod
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        //get user name
        EditText editTextDisplayName = findViewById(R.id.editTextDisplayName);
        Button buttonAddPic = findViewById(R.id.buttonUploadPicture);
        //upload picture
        ImageView UploadPicture = findViewById(R.id.imageViewUploadPicture);
        UploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PICK_IMAGE);
            }
        });

        //go to settings activity.
        ImageView changeSettings = findViewById(R.id.imageButtonSettings);
        changeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, settingsActivity.class);
                intent.putExtra("sourceActivity", "RegisterActivity");
                startActivity(intent);
            }
        });

        //move to sign in page.
        TextView textViewSignIn = findViewById(R.id.textViewSignIn);
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //click on register button.
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString();
                boolean isValid = true;
                if (validateUsername(username)) {
                    editTextUserName.setError(null);  // Clear the error
                } else {
                    editTextUserName.setError("Enter valid email [email_name]@gmail.com");
                    isValid = false;
                }
                String password = editTextPassword.getText().toString();
                if (validatePassword(password)) {
                    editTextPassword.setError(null);  // Clear the error
                } else {
                    editTextPassword.setError("Enter valid password [Min 7 (1 cap, 1 low, 1 num)]");
                    isValid = false;
                }
                String confirmPassword = editTextConfirmPassword.getText().toString();
                if (confirmPassword.equals(password)) {
                    editTextConfirmPassword.setError(null);  // Clear the error
                } else {
                    editTextConfirmPassword.setError("Password does not match");
                    isValid = false;
                }
                String displayName = editTextDisplayName.getText().toString();
                if (displayName.isEmpty()) {
                    editTextDisplayName.setError("Display name cannot be empty");
                    isValid = false;
                } else {
                    editTextDisplayName.setError(null); // Clear previous error if any
                }
                //profilePic- NEED TO CHANGE
                String profilePicture = "";
                if (selectedImageBitmap != null) {
                    profilePicture = convertBitmapToBase64(selectedImageBitmap);
                } else {
                    buttonAddPic.setError("Please Upload a picture");
                    isValid = false;
                }

                final String finalProfilePicture = profilePicture;
                UserAPI userAPI = new UserAPI();
                if (isValid) {
                    //post new user.
                    Call<Void> call = userAPI.postNewUser(username, password, displayName, profilePicture);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("displayName", displayName);
                                //intent.putExtra("profilePicture", finalProfilePicture);
                                startActivity(intent);
                            } else {
                                editTextUserName.setError("Username is already exist");
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
                }
            }
        });
    }


    private boolean validateUsername(String username) {
        // Define the regular expression pattern for validation
        String regex = "^\\w+@gmail\\.com$";
        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        // Create a Matcher object
        Matcher matcher = pattern.matcher(username);
        // Perform the matching and return the result
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        // Define the regular expression pattern for password validation
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{7,}$";
        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);
        // Create a Matcher object
        Matcher matcher = pattern.matcher(password);
        // Perform the matching and return the result
        return matcher.matches();
    }

    //Handle the result of the gallery intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // The user has successfully picked an image from the gallery
            Uri imageUri = data.getData();
            try {
                // Convert the selected image to a Bitmap
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                // Set the selected image in the ImageView
                ImageView imageViewUploadPicture = findViewById(R.id.imageViewUploadPicture);
                imageViewUploadPicture.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    protected void onDestroy() {
        MyApplication.removeActivity(this);
        super.onDestroy();
    }

}
