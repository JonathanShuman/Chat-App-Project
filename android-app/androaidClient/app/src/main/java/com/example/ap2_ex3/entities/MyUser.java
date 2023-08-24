package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MyUser {
    @PrimaryKey
    @NonNull
    private String id;
    private String username;
    private String displayName;
    private String lastMess;
    private String date;
    private String profilePicture;

    //every person we talk to.

    public MyUser(@NonNull String id, String username, String displayName, String lastMess, String date, String profilePicture) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.lastMess = lastMess;
        this.date = date;
        this.profilePicture = profilePicture;

    }


    public @NonNull
    String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLastMess() {
        return lastMess;
    }

    public String getDate() {
        return date;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLastMess(String lastMess) {
        this.lastMess = lastMess;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // ToString method for debugging
    @NonNull
    @Override
    public String toString() {
        return "Contact{" +
                "chatid='" + id + '\'' +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", lastMess='" + lastMess + '\'' +
                ", Data='" + date + '\'' +
                '}';
    }
}
