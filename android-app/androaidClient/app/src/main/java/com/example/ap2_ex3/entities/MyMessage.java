package com.example.ap2_ex3.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//every message we have.
@Entity
public class MyMessage {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String created;
    private String sender;
    private String messageId;


    public MyMessage(String content, String created, String sender, String messageId) {
        this.content = content;
        this.created = created;
        this.sender = sender;
        this.messageId = messageId;
    }

    public int getId() {
        return this.id;
    }

    public String getContent() {
        return content;
    }

    public String getCreated() {
        return created;
    }

    public String getSender() {
        return sender;
    }

    public String getMessageId() {
        return messageId;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}