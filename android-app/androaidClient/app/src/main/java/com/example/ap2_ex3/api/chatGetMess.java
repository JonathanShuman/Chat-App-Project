package com.example.ap2_ex3.api;

import java.util.List;

public class chatGetMess {
    private String id;
    private List<User> users;
    private List<allMess> messages;

    public chatGetMess(String id, List<User> users, List<allMess> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<allMess> getMessages() {
        return messages;
    }

    public void setMessages(List<allMess> messages) {
        this.messages = messages;
    }
}
