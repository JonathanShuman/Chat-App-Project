package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.ap2_ex3.R;


public class Contact {

    private String displayName;
    private String lastMess;
    private String date;
    private String profilePicture;


    public Contact(String displayName, String lastMess, String date, String profilePicture) {

        this.displayName = displayName;
        this.lastMess = lastMess;
        this.date = date;
        this.profilePicture = profilePicture;

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
                ", displayName='" + displayName + '\'' +
                ", lastMess='" + lastMess + '\'' +
                ", Data='" + date + '\'' +
                '}';
    }
}