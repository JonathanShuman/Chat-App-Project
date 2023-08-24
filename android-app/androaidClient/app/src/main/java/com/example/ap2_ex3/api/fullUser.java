package com.example.ap2_ex3.api;

public class fullUser {
    private String username;
    private String password;
    private String displayName;

    private String profilePic;

    public fullUser(String username, String password, String displayName, String profilePic) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

