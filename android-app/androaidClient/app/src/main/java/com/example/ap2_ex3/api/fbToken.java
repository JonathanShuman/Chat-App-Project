package com.example.ap2_ex3.api;

public class fbToken {

    private String username;
    private String firebaseToken;

    public fbToken(String username, String firebaseToken) {
        this.username = username;
        this.firebaseToken = firebaseToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
