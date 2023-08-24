package com.example.ap2_ex3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ap2_ex3.api.MyApplication;

public class settingsActivity extends AppCompatActivity implements ServerIPDialogFragment.OnServerIPChangedListener {
    private TextView hostTextView;
    private TextView portTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hostTextView = findViewById(R.id.hostTextView);
        portTextView = findViewById(R.id.portTextView);
        updateCurrentHostAndPort();

        ImageButton buttonReturn = findViewById(R.id.returnButton);
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sourceActivity = getIntent().getStringExtra("sourceActivity");
                if ("RegisterActivity".equals(sourceActivity)) {
                    // Navigate back to RegisterActivity
                    Intent intent = new Intent(settingsActivity.this, RegisterActivity.class);
                    startActivity(intent);
                } else if ("LoginActivity".equals(sourceActivity)) {
                    // Navigate back to LoginActivity
                    Intent intent = new Intent(settingsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void updateCurrentHostAndPort() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String currentHost = sharedPrefs.getString("pref_server_url", getString(R.string.BaseURLHost));
        String currentPort = sharedPrefs.getString("pref_server_port", getString(R.string.BaseURLPort));

        hostTextView.setText("Current Host: " + currentHost);
        portTextView.setText("Current Port: " + currentPort);
    }

    public void changeTheme(View view) {
        // Toggle between two themes
        int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int newTheme = currentTheme == Configuration.UI_MODE_NIGHT_NO
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO;

        AppCompatDelegate.setDefaultNightMode(newTheme);

        // Update the theme in the MyApplication class
        MyApplication.setCurrentTheme(newTheme);

        // Recreate all activities to apply the new theme
        MyApplication.recreateAllActivities();


    }

    public void changeServerIP(View view) {
        // Display the dialog fragment to get the new server IP and port
        ServerIPDialogFragment dialogFragment = new ServerIPDialogFragment();
        dialogFragment.setOnServerIPChangedListener(this);
        dialogFragment.show(getSupportFragmentManager(), "server_ip_dialog");
    }


    @Override
    public void onServerIPChanged(String serverIP, String serverPort) {
        // Save the updated server IP and port in shared preferences or any other storage mechanism
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("pref_server_url", serverIP);
        editor.putString("pref_server_port", serverPort);
        editor.apply();
    }
}

